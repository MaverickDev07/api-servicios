package com.api.test;

import Model.PtsV2PaymentsReversalsPost201Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.allivia.api.alliviaapi.paymentscbs.apirest.reversal.ProcessAuthorizationReversal;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.*;
import org.allivia.api.alliviaapi.utils.UtilsPayment;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class TestPaymen {
    PaymentModel paymentModel;
    private String urlPay = "https://testsecureacceptance.cybersource.com/silent/pay";
    private String urlMetrix = "https://h.online-metrix.net/fp/tags.js?org_id=1snn5n9w&session_id=linkser_0543571";

    public TestPaymen() {

        CustomerModel customerModel = new CustomerModel("xxxxx", "Mamani", "dondeelsolnollega@gmail.com", "76982167",
                "Avenida los palotes", "Santa Cruz de la Sierra", "CA", "BO", "123");
        CardModel cardModel = new CardModel("001", "4218287004315365", "10-2024", "462");
        paymentModel = new PaymentModel();
        paymentModel.setCardModel(cardModel);
        paymentModel.setCustomerModel(customerModel);
        paymentModel.setAmount(3.00);
        paymentModel.setCurrency("BOB");
        paymentModel.setLocale("en");
        paymentModel.setPaymentToken("");
        paymentModel.setCompanyKey(
                new CompanyKey("54e31b3ccfeb34da8234d6dfae751452", "0438DC4B-D82E-4C49-AEDD-173396C10107"));
        paymentModel.setMerchants(new Merchants("Social", "Servicios", "linkser_0543571", "Allivia", "Ventas"));
    }

    @Test
    public void testTokenizacion() throws Exception {
        paymentModel.setTransactionType("authorization,create_payment_token");
        AppPayment appPayment = new AppPayment(paymentModel, urlPay, urlMetrix,"");
        ReceiptModel model = appPayment.execute();

        Assert.assertEquals("ACCEPT", model.getDecision());

        System.out.println(model.getTransactionType());
        System.out.println(model.getDecision());
        System.out.println(model.getMessage());
        System.out.println(model.getTransactionId());
        System.out.println(model.getReferenceNumber());
        System.out.println(model.getPaymentToken());

        ProcessAuthorizationReversal authorizationReversal = new ProcessAuthorizationReversal(model.getReferenceNumber(), model.getTransactionId(), String.valueOf(paymentModel.getAmount()), paymentModel.getCurrency(),getMerchantDetails());
        authorizationReversal.reversal();


    }

    @Test
    public void reversal(){
        try{
            ProcessAuthorizationReversal authorizationReversal = new ProcessAuthorizationReversal("1625075470134", "6250754853106556704005", "3.00", "BOB",getMerchantDetails());
            authorizationReversal.start();
        }catch (Exception e){
            System.out.println(e);
        }

    }
    public Properties getMerchantDetails() {
        Properties props = new Properties();

        // HTTP_Signature = http_signature and JWT = jwt
        props.setProperty("authenticationType", "http_signature");
        props.setProperty("merchantID", "linkser_0543571");
        props.setProperty("runEnvironment", "CyberSource.Environment.SANDBOX");
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
        props.setProperty("merchantKeyId", "6a64f13b-1538-4499-929f-25851f48c54a");
        props.setProperty("merchantsecretKey", "+DXjmBpQVecXGKeTFJHVpntkd9e90T3MEWbQIx4EZw0=");
        // Logging to be enabled or not.
        //props.setProperty("enableLog", "true");
        // Log directory Path
        //props.setProperty("logDirectory", "log");
        ///props.setProperty("logFilename", "serviciosRestAllivia");

        // Log file size in KB
       // props.setProperty("logMaximumSize", "5M");

        // OAuth related properties.
        //////////props.setProperty("enableClientCert", "false");
        ///////////props.setProperty("clientCertDirectory", "src/main/resources");
        ///////////props.setProperty("clientCertFile", "");
        /////////////props.setProperty("clientCertPassword", "");
        /////////////props.setProperty("clientId", "");
        /////////////// props.setProperty("clientSecret", "");

        return props;

    }
    @Test
    public void testPaymentSaleWihtToken() throws Exception {
        paymentModel.setTransactionType("sale");
        paymentModel.setPaymentToken("C565E191EAB3880BE053AF598E0ABF5A");
        AppPayment appPayment = new AppPayment(paymentModel, urlPay, urlMetrix,"");
        ReceiptModel model = appPayment.execute();

        Assert.assertEquals("ACCEPT", model.getDecision());

        System.out.println(model.getTransactionType());
        System.out.println(model.getDecision());
        System.out.println(model.getMessage());
        System.out.println(model.getTransactionId());
        System.out.println(model.getPaymentToken());

    }

    @Test
    public void testPaymentSale() throws Exception {
        paymentModel.setTransactionType("sale");
        AppPayment appPayment = new AppPayment(paymentModel, urlPay, urlMetrix,"");
        ReceiptModel model = appPayment.execute();

        Assert.assertEquals("ACCEPT", model.getDecision());

        System.out.println(model.getTransactionType());
        System.out.println(model.getDecision());
        System.out.println(model.getMessage());
        System.out.println(model.getTransactionId());
        // System.out.println(model.getPaymentToken());

    }

    @Test
    public void testTokenUpdate() throws Exception {
        paymentModel.setTransactionType("authorization,update_payment_token");
        paymentModel.setPaymentToken("C565E191EAB3880BE053AF598E0ABF5A");
        AppPayment appPayment = new AppPayment(paymentModel, urlPay, urlMetrix,"");
        ReceiptModel model = appPayment.execute();

        Assert.assertEquals("ACCEPT", model.getDecision());

        System.out.println(model.getTransactionType());
        System.out.println(model.getDecision());
        System.out.println(model.getMessage());
        System.out.println(model.getTransactionId());
        System.out.println(model.getPaymentToken());
    }

    @Test
    public void testExecuteMetrix() {
        AppPayment appPayment = new AppPayment(null, null, urlMetrix,"");
        appPayment.generateFingerPrint();

    }

    @Test
    public void testuuid() {
        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    public void testSedMessage() {
        AppPayment appPayment = new AppPayment();
        String[] value = {"card_number", "card_type"};
        System.out.println(appPayment.setMessage(value));
    }

    @Test
    public void testDateCard() {
        Payment payment = new Payment();
        LocalDate localDate = LocalDate.now();
        String year = String.valueOf(localDate.getYear());

        System.out.println(year.substring(0, 2));
        System.out.println();
    }

    @Test
    public void testCardType() throws PayException {
        Payment payment = new Payment();
        System.out.println(UtilsPayment.cardType("5218287004315365"));
    }

    @Test
    public void testFormatDate() throws PayException {
        Assert.assertEquals("10/24", UtilsPayment.formatDate("10-2024"));
    }
}
