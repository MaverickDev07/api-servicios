package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

public class PaymentModel {
    private String transactionType;
    private double amount;
    private String currency;
    private String locale;
    private String paymentToken;
    private CardModel cardModel;
    private CustomerModel customerModel;
    private CompanyKey companyKey;
    private Merchants merchants;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public CardModel getCardModel() {
        return cardModel;
    }

    public void setCardModel(CardModel cardModel) {
        this.cardModel = cardModel;
    }

    public CustomerModel getCustomerModel() {
        return customerModel;
    }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(CompanyKey companyKey) {
        this.companyKey = companyKey;
    }

    public Merchants getMerchants() {
        return merchants;
    }

    public void setMerchants(Merchants merchants) {
        this.merchants = merchants;
    }
}
