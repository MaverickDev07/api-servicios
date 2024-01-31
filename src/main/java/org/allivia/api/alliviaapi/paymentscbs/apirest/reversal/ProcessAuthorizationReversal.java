package org.allivia.api.alliviaapi.paymentscbs.apirest.reversal;

import Api.ReversalApi;
import Invokers.ApiClient;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cybersource.authsdk.log.Log4j;

import java.util.Properties;


public class ProcessAuthorizationReversal extends Thread  {
    public static final Logger logger = LogManager.getLogger(ProcessAuthorizationReversal.class);
    private String referenceNumber;
    private String transactionId;
    private String amount;
    private String currency;
    private Properties properties;

    public ProcessAuthorizationReversal(String referenceNumber, String transactionId, String amount, String currency,Properties properties) {
        this.referenceNumber = referenceNumber;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.properties= properties;
    }

    @Override
    public void run() {
        try {
            logger.log(Level.INFO, "Inicio de reversion");
            reversal();
            logger.log(Level.INFO, "Fin de reversion");
        } catch (Exception e) {
            logger.error("Fallo proceso de reversion", e);
        }
    }

    public void reversal() {
        //logger.log(Level.INFO, "Ejecutando reversión: {}", referenceNumber);
        PtsV2PaymentsReversalsPost201Response result = null;
        try {
            AuthReversalRequest requestObj = new AuthReversalRequest();
            Ptsv2paymentsidreversalsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsidreversalsClientReferenceInformation();
            clientReferenceInformation.code(referenceNumber);
            requestObj.clientReferenceInformation(clientReferenceInformation);

            Ptsv2paymentsidreversalsReversalInformation reversalInformation = new Ptsv2paymentsidreversalsReversalInformation();
            Ptsv2paymentsidreversalsReversalInformationAmountDetails reversalInformationAmountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
            reversalInformationAmountDetails.totalAmount(amount);
            reversalInformationAmountDetails.setCurrency(currency);
            reversalInformation.amountDetails(reversalInformationAmountDetails);

            reversalInformation.reason("testing");
            requestObj.reversalInformation(reversalInformation);

            //Properties merchantProp = Configuration.getMerchantDetails();

            ApiClient apiClient = new ApiClient();
            apiClient.merchantConfig = new MerchantConfig(properties);
            ReversalApi apiInstance = new ReversalApi(apiClient);
            result = apiInstance.authReversal(transactionId, requestObj);
            String responseCode = apiClient.responseCode;
            String status = apiClient.status;
            logger.log(Level.INFO, "ResponseCode :" + responseCode + ", ResponseMessage :" + status );
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error en procesar la reversión: " + referenceNumber, e);
        }

    }
}
