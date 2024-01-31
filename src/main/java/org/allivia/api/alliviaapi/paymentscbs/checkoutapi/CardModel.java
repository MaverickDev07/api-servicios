package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;

public class CardModel {
    private String cardType;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardCvn;

   
    public CardModel(String cardType, String cardNumber, String cardExpiryDate, String cardCvn) {
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCvn = cardCvn;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCvn() {
        return cardCvn;
    }

    public void setCardCvn(String cardCvn) {
        this.cardCvn = cardCvn;
    }

   
    
}
