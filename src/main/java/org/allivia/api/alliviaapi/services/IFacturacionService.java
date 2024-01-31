package org.allivia.api.alliviaapi.services;

import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import org.allivia.api.alliviaapi.entities.AppFacturaEntity;
import org.allivia.api.alliviaapi.entities.AppPagoSuscripcionPaciente;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.entities.models.AppPacienteTarjeta;
import org.allivia.api.alliviaapi.entities.models.AppPagoTarjetaPaciente;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.ReceiptModel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface IFacturacionService extends IBaseService<AppFacturaEntity, Long> {
    Object saveFactura(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException;
    ReceiptModel savePayment(AppPacienteTarjeta appPacienteTarjeta, AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    AppPacienteTarjeta getDataPaciente(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws Exception;
    AppSuscripcionEntity saveFacturaSuscripcion(AppPacienteTarjeta appPacienteTarjeta, AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws Exception;
    Object saveFacturaTest(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException;
    void sendEmail() throws Exception;
}
