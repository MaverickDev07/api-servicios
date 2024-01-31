package org.allivia.api.alliviaapi.utils;

import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;

import java.time.LocalDate;

public class UtilsPayment {
    public static String cardType(String numeroTarjeta) throws PayException {
        if (numeroTarjeta==null || numeroTarjeta.isEmpty()){
            throw  new PayException("Nro. de tarjeta es requerido.");
        }
        if (numeroTarjeta.startsWith("4")) {
            return "001";
        }
        if (numeroTarjeta.startsWith("5")) {
            return "002";
        }
        throw  new PayException("Nro. de tarjeta no válido.");
    }

    public static String expireDateCard(String fechaVencimiento) throws PayException {
        String result;
        if (fechaVencimiento==null || fechaVencimiento.isEmpty()){
            throw  new PayException("Fecha vencimiento es requerido.");
        }
        try{
            LocalDate localDate = LocalDate.now();
            String year = String.valueOf(localDate.getYear());
            String[] yearArray = fechaVencimiento.split("/");
            result = yearArray[0] + "-" + year.substring(0, 2) + yearArray[1];
        }catch (Exception e){
            throw  new PayException("Fecha de expiración no válida.");
        }
       return result;
    }
    public static String formatDate(String fechaVencimiento) throws PayException {
        try {
            String[] yearArray = fechaVencimiento.split("-");
            return yearArray[0] + "/" + yearArray[1].substring(yearArray[1].length() - 2);
        }catch (Exception e){
            throw  new PayException("Formato de fecha no válido.");
        }
    }
}
