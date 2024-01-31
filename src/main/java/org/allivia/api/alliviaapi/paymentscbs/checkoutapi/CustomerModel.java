package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

public class CustomerModel {
    private String billToForename;
    private String billToSurname;
    private String billToEmail;
    private String billToPhone;
    private String billTo_addressLine1;
    private String billToAddressCity;
    private String billToAddressState;
    private String billToAddressCountry;
    private String billToAddressPostalCode;

    public CustomerModel(String billToForename, String billToSurname, String billToEmail, String billToPhone, String billTo_addressLine1, String billToAddressCity, String billToAddressState, String billToAddressCountry, String billToAddressPostalCode) {
        this.billToForename = billToForename;
        this.billToSurname = billToSurname;
        this.billToEmail = billToEmail;
        this.billToPhone = billToPhone;
        this.billTo_addressLine1 = billTo_addressLine1;
        this.billToAddressCity = billToAddressCity;
        this.billToAddressState = billToAddressState;
        this.billToAddressCountry = billToAddressCountry;
        this.billToAddressPostalCode = billToAddressPostalCode;
    }

    public String getBillToForename() {
        return billToForename;
    }

    public String getBillToSurname() {
        return billToSurname;
    }

    public String getBillToEmail() {
        return billToEmail;
    }

    public String getBillToPhone() {
        return billToPhone;
    }

    public String getBillTo_addressLine1() {
        return billTo_addressLine1;
    }

    public String getBillToAddressCity() {
        return billToAddressCity;
    }

    public String getBillToAddressState() {
        return billToAddressState;
    }

    public String getBillToAddressCountry() {
        return billToAddressCountry;
    }

    public String getBillToAddressPostalCode() {
        return billToAddressPostalCode;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "billToForename='" + billToForename + '\'' +
                ", billToSurname='" + billToSurname + '\'' +
                ", billToEmail='" + billToEmail + '\'' +
                ", billToPhone='" + billToPhone + '\'' +
                ", billTo_addressLine1='" + billTo_addressLine1 + '\'' +
                ", billToAddressCity='" + billToAddressCity + '\'' +
                ", billToAddressState='" + billToAddressState + '\'' +
                ", billToAddressCountry='" + billToAddressCountry + '\'' +
                ", billToAddressPostalCode='" + billToAddressPostalCode + '\'' +
                '}';
    }
}
