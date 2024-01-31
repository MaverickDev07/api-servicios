package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppPayment {
    public static final Logger logger = LogManager.getLogger(AppPayment.class);
    private static final String BILL_TO_FORENAME = "bill_to_forename";
    private static final String BILL_TO_SURNAME = "bill_to_surname";
    private static final String BILL_TO_EMAIL = "bill_to_email";
    private static final String BILL_TO_ADDRESS_LINE_1 = "bill_to_address_line1";
    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final String CARD_NUMBER = "card_number";
    private static final String CARD_TYPE = "card_type";
    private static final String CARD_EXPIRY_DATE = "card_expiry_date";
    private static final String CARD_CVN = "card_cvn";
    private static final String PAYMENT_TOKEN = "payment_token";
    private static final String GENERATE_TOKEN = "authorization,update_payment_token";


    private String fieldNames;
    private String unsignedFieldNames;
    private String transDate;
    private String numberRef;
    private String uuidTrans;
    private PaymentModel paymentModel;
    private String urlPayment;
    private String urlFingerPrint;
    private String secretKey;

    public AppPayment() {
    }

    /**
     * Constructor que inicializa los valore de pago
     *
     * @param paymentModel,   objeto con el detalle de pago
     * @param urlPayment,     link del servicio ejecutado
     * @param urlFingerPrint, link que genera huella digital
     */
    public AppPayment(PaymentModel paymentModel, String urlPayment, String urlFingerPrint,String secretKey) {
        logger.log(Level.INFO, "Transaction CYBS: {}, ", paymentModel.getTransactionType());
        this.transDate = getUTCDateTime();
        this.numberRef = getRefNumber();
        this.uuidTrans = UUID.randomUUID().toString();
        unsignedFieldNames = "card_type,card_number,card_expiry_date,card_cvn";
        this.paymentModel = paymentModel;
        this.urlPayment = urlPayment;
        this.urlFingerPrint = urlFingerPrint;
        this.secretKey=secretKey;
    }

    /**
     * Ejecuta métodos de pago, (Tokenizacion, solo pago, pago con token, actualizacion de token)
     *
     * @return ojbeto ReceiptModel con los datos de respuesta si realizo el pago correctamente
     * @throws PayException si hubo un fallo en el proceso de pago
     */
    public ReceiptModel execute() throws PayException {
        ReceiptModel receiptModel = new ReceiptModel();
        try {
           // generateFingerPrint();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();

            MultiValueMap<String, String> map = setRequest(paymentModel);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,
                    headers);

            ResponseEntity<String> response = restTemplate.postForEntity(urlPayment, request, String.class);

            logger.log(Level.INFO, "AppPayment: " + response.toString());
            if (response.getStatusCodeValue() != 200) {
                throw new PayException("Servicio no disponible");
            }

            Document doc = Jsoup.parse(Objects.requireNonNull(response.getBody()));
            Elements form = doc.select("form");

            if (form.get(0).getElementById(PAYMENT_TOKEN) != null) {
                receiptModel.setPaymentToken(form.get(0).getElementById(PAYMENT_TOKEN).val());
                receiptModel.setReferenceNumber(form.get(0).getElementById("req_reference_number").val());
            }

            if (form.get(0).getElementById("req_payment_token") != null) {
                receiptModel.setPaymentToken(form.get(0).getElementById("req_payment_token").val());
            }

            receiptModel.setDecision(form.get(0).getElementById("decision").val());
            receiptModel.setMessage(form.get(0).getElementById("message").val());
            logger.log(Level.INFO, "Transaction CYBS: {}, {}", paymentModel.getTransactionType(), receiptModel.getDecision());
            if (!receiptModel.getDecision().equals("ACCEPT")) {
                throw new PayException(getMessage(form));
            } else {
                receiptModel.setTransactionId(form.get(0).getElementById("transaction_id").val());
                receiptModel.setTransactionType(form.get(0).getElementById("req_transaction_type").val());
            }

        } catch (HttpClientErrorException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.log(Level.ERROR, "Error en procesar la operación:" + paymentModel.getTransactionType(), e);
            throw new PayException("Error en procesar la operación: " + paymentModel.getTransactionType() + " - " + e.getMessage());
        } catch (PayException e) {
            throw e;
        }
        return receiptModel;
    }

    /**
     * Construye mensaje personalizado de acuerdo a respueta
     *
     * @param form, respuesta html
     * @return mensaje si hubo fallo en el proceso de pago
     */
    public String getMessage(Elements form) {
        String requiredFields;
        String fieldsInvalid;
        try {
            if (form.get(0).getElementById("invalid_fields")!=null){
                fieldsInvalid = form.get(0).getElementById("invalid_fields").val() == null ? "" : form.get(0).getElementById("invalid_fields").val();
                String[] invalidsArray = fieldsInvalid.split(",");
                if (invalidsArray.length > 0) {
                    return "Dato no válido: " + setMessage(invalidsArray);
                }
            }

        } catch (Exception ignored) {
            logger.log(Level.ERROR, "ignorado");
        }
        try {
            if (form.get(0).getElementById("required_fields")!=null){
                requiredFields = form.get(0).getElementById("required_fields").val() != null ? form.get(0).getElementById("required_fields").val() : "";
                String[] requireArray = requiredFields.split(",");
                if (requireArray.length > 0) {
                    return "Dato requerido: " + setMessage(requireArray);
                }
            }

        } catch (Exception ignored) {
            logger.log(Level.ERROR, "ignorado");
        }

        if (form.get(0).getElementById("message")!=null){
            return form.get(0).getElementById("message").val();
        }
        return "";
    }

    /**
     * Conctena nombres de los campos de formulario
     *
     * @param valueArray, conjunto de nombre de campos de formulario de respusta
     * @return msg
     */
    public String setMessage(String[] valueArray) {
        StringBuilder msg = new StringBuilder();
        int count = 1;
        for (String value : valueArray) {
            msg.append(getNames(value));
            if (count < valueArray.length) {
                msg.append(",");
            }
            count++;
        }
        return msg.toString();
    }

    /**
     * Valida los campos de respuesta, para devolver una respusta personalizada
     *
     * @param value, nombre que se tiene que validar
     * @return nombre de campos
     */
    public String getNames(String value) {
        if (value.equals(BILL_TO_FORENAME)) {
            return "Nombres";
        }
        if (value.equals(BILL_TO_SURNAME)) {
            return "Apellidos";
        }
        if (value.equals(BILL_TO_EMAIL)) {
            return "Correo electrónico";
        }
        if (value.equals(BILL_TO_ADDRESS_LINE_1)) {
            return "Dirección";
        }
        if (value.equals(TRANSACTION_TYPE)) {
            return "Tipo de transacción";
        }
        if (value.equals(CARD_NUMBER)) {
            return "Nro. de tarjeta";
        }
        if (value.equals(CARD_TYPE)) {
            return "Tipo de tarjeta";
        }
        if (value.equals(CARD_EXPIRY_DATE)) {
            return "Fecha de expiración";
        }
        return "";
    }

    /**
     * Cnstruye el request que sera enviado al metodo de pago
     *
     * @param paymentModel, objeto con el detalle de pago
     * @return map
     * @throws UnsupportedEncodingException, codificacion no soportada
     * @throws NoSuchAlgorithmException,     si el algoritmo de cifrano es inválido
     * @throws InvalidKeyException,          si la llave de encriptacion es inválido
     */
    private MultiValueMap<String, String> setRequest(PaymentModel paymentModel) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        if (paymentModel.getPaymentToken() != null && !paymentModel.getPaymentToken().isEmpty()) {
            fieldNames = "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,payment_token,device_fingerprint_id,merchant_defined_data9,merchant_defined_data14,merchant_defined_data87,merchant_defined_data88,merchant_defined_data90";
        } else {
            fieldNames = "access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency,payment_method,bill_to_forename,bill_to_surname,bill_to_email,bill_to_phone,bill_to_address_line1,bill_to_address_city,bill_to_address_state,bill_to_address_country,bill_to_address_postal_code,device_fingerprint_id,merchant_defined_data9,merchant_defined_data14,merchant_defined_data87,merchant_defined_data88,merchant_defined_data90";
            if (Objects.requireNonNull(paymentModel.getPaymentToken()).equals(GENERATE_TOKEN)) {
                fieldNames.concat(",payment_token");
            }
        }
        try {
            map.add("access_key", paymentModel.getCompanyKey().getAccessKey());
            map.add("profile_id", paymentModel.getCompanyKey().getProfileId());
            map.add("signed_field_names", this.fieldNames);
            map.add("unsigned_field_names", this.unsignedFieldNames);
            map.add("locale", paymentModel.getLocale());
            map.add(TRANSACTION_TYPE, paymentModel.getTransactionType());
            map.add("amount", String.valueOf(paymentModel.getAmount()));
            map.add("currency", paymentModel.getCurrency());
            map.add("payment_method", "card");

            if (paymentModel.getPaymentToken() != null && !paymentModel.getPaymentToken().isEmpty()) {
                map.add(PAYMENT_TOKEN, paymentModel.getPaymentToken());
                map.add(CARD_TYPE, "");
                map.add(CARD_NUMBER, "");
                map.add(CARD_EXPIRY_DATE, "");
                map.add(CARD_CVN, "");
            } else {

                map.add(BILL_TO_FORENAME, paymentModel.getCustomerModel().getBillToForename());
                map.add(BILL_TO_SURNAME, paymentModel.getCustomerModel().getBillToSurname());
                map.add(BILL_TO_EMAIL, paymentModel.getCustomerModel().getBillToEmail());
                map.add("bill_to_phone", paymentModel.getCustomerModel().getBillToPhone());
                map.add(BILL_TO_ADDRESS_LINE_1, paymentModel.getCustomerModel().getBillTo_addressLine1());
                map.add("bill_to_address_city", paymentModel.getCustomerModel().getBillToAddressCity());
                map.add("bill_to_address_state", paymentModel.getCustomerModel().getBillToAddressState());
                map.add("bill_to_address_country", paymentModel.getCustomerModel().getBillToAddressCountry());
                map.add("bill_to_address_postal_code", paymentModel.getCustomerModel().getBillToAddressPostalCode());
                map.add(CARD_TYPE, paymentModel.getCardModel().getCardType());
                map.add(CARD_NUMBER, paymentModel.getCardModel().getCardNumber());
                map.add(CARD_EXPIRY_DATE, paymentModel.getCardModel().getCardExpiryDate());
                map.add(CARD_CVN, paymentModel.getCardModel().getCardCvn());

                if (paymentModel.getPaymentToken().equals(GENERATE_TOKEN)) {
                    map.add(PAYMENT_TOKEN, paymentModel.getPaymentToken());
                }
            }

            map.add("signed_date_time", transDate);
            map.add("reference_number", numberRef);
            map.add("signature", getSignature(paymentModel));
            map.add("transaction_uuid", uuidTrans);
            map.add("device_fingerprint_id", uuidTrans);

            map.add("merchant_defined_data9", paymentModel.getMerchants().getMerchantDefinedData9());
            map.add("merchant_defined_data14", paymentModel.getMerchants().getMerchantDefinedData14());
            map.add("merchant_defined_data87", paymentModel.getMerchants().getMerchantDefinedData87());
            map.add("merchant_defined_data88", paymentModel.getMerchants().getMerchantDefinedData88());
            map.add("merchant_defined_data90", paymentModel.getMerchants().getMerchantDefinedData90());

        } catch (Exception e) {
            logger.log(Level.ERROR, "Error al construir request cbs: {}", paymentModel.getTransactionType(), e);
            throw e;
        }
        return map;
    }

    /**
     * Cifrado de datos enviados en request de pago
     *
     * @param paymentModel, objeto con el detalle de pago
     * @return signature
     * @throws InvalidKeyException,          si la llave de encriptacion es inválido
     * @throws NoSuchAlgorithmException,     si el algoritmo de cifrano es inválido
     * @throws UnsupportedEncodingException, codificacion no soportada
     */
    private String getSignature(PaymentModel paymentModel) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {

        String signature;
        try {
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.put("signed_field_names", this.fieldNames);
            hashMap.put("access_key", paymentModel.getCompanyKey().getAccessKey());
            hashMap.put("profile_id", paymentModel.getCompanyKey().getProfileId());
            hashMap.put("locale", paymentModel.getLocale());
            hashMap.put(TRANSACTION_TYPE, paymentModel.getTransactionType());
            hashMap.put("amount", paymentModel.getAmount());
            hashMap.put("currency", paymentModel.getCurrency());
            hashMap.put("unsigned_field_names", this.unsignedFieldNames);
            hashMap.put("payment_method", "card");

            if (paymentModel.getPaymentToken() != null && !paymentModel.getPaymentToken().isEmpty()) {
                hashMap.put(PAYMENT_TOKEN, paymentModel.getPaymentToken());
            } else {
                hashMap.put(BILL_TO_FORENAME, paymentModel.getCustomerModel().getBillToForename());
                hashMap.put(BILL_TO_SURNAME, paymentModel.getCustomerModel().getBillToSurname());
                hashMap.put(BILL_TO_EMAIL, paymentModel.getCustomerModel().getBillToEmail());
                hashMap.put("bill_to_phone", paymentModel.getCustomerModel().getBillToPhone());
                hashMap.put(BILL_TO_ADDRESS_LINE_1, paymentModel.getCustomerModel().getBillTo_addressLine1());
                hashMap.put("bill_to_address_city", paymentModel.getCustomerModel().getBillToAddressCity());
                hashMap.put("bill_to_address_state", paymentModel.getCustomerModel().getBillToAddressState());
                hashMap.put("bill_to_address_country", paymentModel.getCustomerModel().getBillToAddressCountry());
                hashMap.put("bill_to_address_postal_code", paymentModel.getCustomerModel().getBillToAddressPostalCode());
                hashMap.put(CARD_TYPE, paymentModel.getCardModel().getCardType());
                hashMap.put(CARD_NUMBER, paymentModel.getCardModel().getCardNumber());
                hashMap.put(CARD_EXPIRY_DATE, paymentModel.getCardModel().getCardExpiryDate());
                hashMap.put(CARD_CVN, paymentModel.getCardModel().getCardCvn());

                if (paymentModel.getPaymentToken().equals(GENERATE_TOKEN)) {
                    hashMap.put(PAYMENT_TOKEN, paymentModel.getPaymentToken());
                }
            }
            hashMap.put("signed_date_time", this.transDate);
            hashMap.put("reference_number", this.numberRef);
            hashMap.put("transaction_uuid", this.uuidTrans);
            hashMap.put("device_fingerprint_id", this.uuidTrans);

            hashMap.put("merchant_defined_data9", paymentModel.getMerchants().getMerchantDefinedData9());
            hashMap.put("merchant_defined_data14", paymentModel.getMerchants().getMerchantDefinedData14());
            hashMap.put("merchant_defined_data87", paymentModel.getMerchants().getMerchantDefinedData87());
            hashMap.put("merchant_defined_data88", paymentModel.getMerchants().getMerchantDefinedData88());
            hashMap.put("merchant_defined_data90", paymentModel.getMerchants().getMerchantDefinedData90());

            Security security = new Security(secretKey);
            signature = security.sign(hashMap);
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.log(Level.ERROR, "Error al cifrar los datos: ", e);
            throw e;
        }
        return signature;
    }

    /**
     * Ejecuta el envio de huella digital
     */
    public void generateFingerPrint() {
        String url = this.urlFingerPrint.concat(this.uuidTrans);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
    }

    /**
     * Obtiene la fecha en formato yyyy-MM-dd'T'HH:mm:ss'Z'
     *
     * @return '2021-06-29T13:28:57Z'
     */
    private String getUTCDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    /**
     * Obtiene el tiempo para generar nro de referencia de la transaccion
     *
     * @return '1624973337'
     */
    private String getRefNumber() {
        return String.valueOf(new Date().getTime());
    }

}