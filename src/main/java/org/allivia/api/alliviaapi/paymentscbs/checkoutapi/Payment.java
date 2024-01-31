package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;


import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.allivia.api.alliviaapi.entities.AppPagoSuscripcionPaciente;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.models.AppPacienteTarjeta;
import org.allivia.api.alliviaapi.paymentscbs.apirest.reversal.ProcessAuthorizationReversal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by yeri_ on 24/06/2021.
 */
@Component
public class Payment implements IPaymentService {
    public static final Logger logger = LogManager.getLogger(Payment.class);


    @Value("${payment.url}")
    private String url;
    @Value("${payment.urlfingerprint}")
    private String urlFingerPrint;
    @Value("${payment.currency}")
    private String currencyUSD;
    @Value("${payment.amount}")
    private double amount;
    @Value("${payment.createtoken}")
    private String createToken;
    @Value("${payment.updatetoken}")
    private String updateToken;
    @Value("${payment.sale}")
    private String sale;
    @Value("${payment.locale}")
    private String localidad;
    @Value("${payment.accesskey}")
    private String accesskey;
    @Value("${payment.profileid}")
    private String profileid;
    @Value("${authenticationType}")
    private String authenticationType;
    @Value("${merchantID}")
    private String merchantId;
    @Value("${runEnvironment}")
    private String runEnvironment;
    @Value("${merchantKeyId}")
    private String merchantKeyId;
    @Value("${merchantsecretKey}")
    private String merchanSecretKey;

    @Value("${merchant.defined.data9}")
    private String merchaDefineDate9;
    @Value("${merchant.defined.data14}")
    private String merchaDefineDate14;
    @Value("${merchant.defined.data87}")
    private String merchaDefineDate87;
    @Value("${merchant.defined.data88}")
    private String merchaDefineDate88;
    @Value("${merchant.defined.data90}")
    private String merchaDefineDate90;
    @Value("${payment.secretkey}")
    private String secretKey;

    @Value("${payment.address1}")
    private String address1;
    @Value("${payment.city}")
    private String city;
    @Value("${payment.state}")
    private String state;
    @Value("${payment.zip}")
    private String zip;
    @Value("${payment.contry}")
    private String contry;


    public ReceiptModel paymentSaleToken(Double monto, String token, String moneda) throws PayException {

        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        paymentModel.setCardModel(new CardModel("", "", "", ""));
        paymentModel.setCustomerModel(new CustomerModel("", "", "", "", "", "", "", "", ""));
        paymentModel.setAmount(monto);
        paymentModel.setCurrency(moneda);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(sale);
        paymentModel.setPaymentToken(token);
        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);

        logger.info("Realizando pago por tarjeta, urlPago=" + url + ", moneda= " + moneda + ", localidad=" + localidad);
        return appPayment.execute();
    }

    public ReceiptModel paymentSale(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente, AppUsuarioEntity appUsuarioEntity) throws PayException {
        logger.info("Servicio paymentSale=" + appPagoSuscripcionPaciente.toString());
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        CustomerModel customerModel = new CustomerModel(appPagoSuscripcionPaciente.getNombre(), "Mamami", appUsuarioEntity.getEmail(), "76982167",
                "Avenida los palotes", "Santa Cruz de la Sierra", "CA", "BO", "123");
        CardModel cardModel = new CardModel(appPagoSuscripcionPaciente.getTipoTarjeta(), appPagoSuscripcionPaciente.getNumeroTarjeta(), appPagoSuscripcionPaciente.getFechaVencimiento(), appPagoSuscripcionPaciente.getCvn());
        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(appPagoSuscripcionPaciente.getMonto());
        paymentModel.setCurrency(currencyUSD);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(sale);

        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);
        return appPayment.execute();
    }

    public ReceiptModel paymentSaleNew(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente, AppPacienteTarjeta appPacienteTarjeta) throws PayException {
        logger.info("Servicio paymentSale=" + appPagoSuscripcionPaciente.toString());
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        CustomerModel customerModel = new CustomerModel(appPagoSuscripcionPaciente.getNombre(), appPacienteTarjeta.getApellido(), appPacienteTarjeta.getEmail(), appPacienteTarjeta.getCelular(),
                address1, city, state, contry, zip);
        CardModel cardModel = new CardModel(appPagoSuscripcionPaciente.getTipoTarjeta(), appPagoSuscripcionPaciente.getNumeroTarjeta(), appPagoSuscripcionPaciente.getFechaVencimiento(), appPagoSuscripcionPaciente.getCvn());
        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(appPagoSuscripcionPaciente.getMonto());
        paymentModel.setCurrency(currencyUSD);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(sale);

        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);
        return appPayment.execute();
    }

    public ReceiptModel tokenizacion(AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        CustomerModel customerModel = new CustomerModel(appMetodospagoEntity.getNombre(), "Allivia"+" "+appUsuarioEntity.getEmail(), appUsuarioEntity.getEmail(), "76982167",
                address1, city, state, contry, zip);

        CardModel cardModel = new CardModel(
                appMetodospagoEntity.getTipoTarjeta(),
                appMetodospagoEntity.getNumeroTarjeta(),
                appMetodospagoEntity.getFechaVencimiento(), cardCvn);

        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(amount);
        paymentModel.setCurrency(currencyUSD);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(createToken);
        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);
        ReceiptModel model = appPayment.execute();

        if (model.getDecision().equals("ACCEPT")) {

            ProcessAuthorizationReversal authorizationReversal = new ProcessAuthorizationReversal(model.getReferenceNumber(), model.getTransactionId(), String.valueOf(paymentModel.getAmount()), paymentModel.getCurrency(), getMerchantDetails());
            authorizationReversal.start();

        }

        return model;
    }

    public ReceiptModel tokenizacionNew(AppPacienteTarjeta appPacienteTarjeta, AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        CustomerModel customerModel = new CustomerModel(appMetodospagoEntity.getNombre(), appPacienteTarjeta.getApellido(), appPacienteTarjeta.getEmail(), appPacienteTarjeta.getCelular(),
                address1, city, state, contry, zip);
        logger.info("tokenizacionNew -> " + customerModel.toString());

        CardModel cardModel = new CardModel(
                appMetodospagoEntity.getTipoTarjeta(),
                appMetodospagoEntity.getNumeroTarjeta(),
                appMetodospagoEntity.getFechaVencimiento(), cardCvn);

        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(amount);
        paymentModel.setCurrency(currencyUSD);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(createToken);
        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);
        ReceiptModel model = appPayment.execute();

        if (model.getDecision().equals("ACCEPT")) {

            ProcessAuthorizationReversal authorizationReversal = new ProcessAuthorizationReversal(model.getReferenceNumber(), model.getTransactionId(), String.valueOf(paymentModel.getAmount()), paymentModel.getCurrency(), getMerchantDetails());
            authorizationReversal.start();

        }

        return model;
    }

    public ReceiptModel updateTokenizacion(AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setMerchants(new Merchants(merchaDefineDate9, merchaDefineDate14, merchaDefineDate87, merchaDefineDate88, merchaDefineDate90));
        CustomerModel customerModel = new CustomerModel(appMetodospagoEntity.getNombre(), "Allivia"+" "+appUsuarioEntity.getEmail(), appUsuarioEntity.getEmail(), "76982167",
                address1, city, state, contry, zip);
        CardModel cardModel = new CardModel(appMetodospagoEntity.getTipoTarjeta(), appMetodospagoEntity.getNumeroTarjeta(), appMetodospagoEntity.getFechaVencimiento(), cardCvn);
        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(amount);
        paymentModel.setCurrency(currencyUSD);
        paymentModel.setLocale(localidad);
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(new CompanyKey(accesskey, profileid));
        paymentModel.setTransactionType(updateToken);
        paymentModel.setPaymentToken(appMetodospagoEntity.getToken());
        AppPayment appPayment = new AppPayment(paymentModel, url, urlFingerPrint,secretKey);
        ReceiptModel model = appPayment.execute();

        if (model.getDecision().equals("ACCEPT")) {
            ProcessAuthorizationReversal authorizationReversal = new ProcessAuthorizationReversal(model.getReferenceNumber(), model.getTransactionId(), String.valueOf(paymentModel.getAmount()), paymentModel.getCurrency(), getMerchantDetails());
            authorizationReversal.start();
        }
        return model;
    }

    public Properties getMerchantDetails() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", authenticationType);
        props.setProperty("merchantID", merchantId);
        props.setProperty("runEnvironment", runEnvironment);
        //////props.setProperty("requestJsonPath", "src/main/resources/request.json");

        // MetaKey Parameters
        //////////props.setProperty("portfolioID", "");
        ////props.setProperty("useMetaKey", "false");

        // JWT Parameters
        ///////props.setProperty("keyAlias", "testrest");
        ///////props.setProperty("keyPass", "testrest");
        ///////props.setProperty("keyFileName", "testrest");

        // P12 key path. Enter the folder path where the .p12 file is located.

        //////props.setProperty("keysDirectory", "src/main/resources");
        // HTTP Parameters
        props.setProperty("merchantKeyId", merchantKeyId);
        props.setProperty("merchantsecretKey", merchanSecretKey);
        // Logging to be enabled or not.
        props.setProperty("enableLog", "true");
        // Log directory Path
        props.setProperty("logDirectory", "log");
        props.setProperty("logFilename", "cybs");

        // Log file size in KB
        props.setProperty("logMaximumSize", "5M");

        // OAuth related properties.
        //////////props.setProperty("enableClientCert", "false");
        ///////////props.setProperty("clientCertDirectory", "src/main/resources");
        ///////////props.setProperty("clientCertFile", "");
        /////////////props.setProperty("clientCertPassword", "");
        /////////////props.setProperty("clientId", "");
        /////////////// props.setProperty("clientSecret", "");

        return props;

    }

}
