package org.allivia.api.alliviaapi.paymentscbs.checkoutapi;


import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.allivia.api.alliviaapi.entities.AppPagoSuscripcionPaciente;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.models.AppPacienteTarjeta;
import org.allivia.api.alliviaapi.entities.models.AppPagoTarjetaPaciente;

public interface IPaymentService {
    ReceiptModel paymentSaleToken(Double monto, String token, String moneda) throws PayException;
    ReceiptModel paymentSale(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente, AppUsuarioEntity appUsuarioEntity) throws PayException ;
    ReceiptModel paymentSaleNew(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente, AppPacienteTarjeta appPacienteTarjeta) throws PayException;
    ReceiptModel tokenizacion(AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException;
    ReceiptModel tokenizacionNew(AppPacienteTarjeta appPacienteTarjeta, AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException;
    ReceiptModel updateTokenizacion(AppMetodospagoEntity appMetodospagoEntity, AppUsuarioEntity appUsuarioEntity, String cardCvn) throws PayException;
}
