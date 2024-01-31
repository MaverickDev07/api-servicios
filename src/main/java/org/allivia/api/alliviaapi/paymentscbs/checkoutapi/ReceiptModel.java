package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

public class ReceiptModel {
    private String decisionReasonCode;
    private String decision;
    private String paymentToken;
    private String message ;
    private String transactionId ;
    private String referenceNumber ;
    private String transactionType;
    
    
    public ReceiptModel() {
       
    }

    public String getDecisionReasonCode() {
        return decisionReasonCode;
    }

    public void setDecisionReasonCode(String decisionReasonCode) {
        this.decisionReasonCode = decisionReasonCode;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
