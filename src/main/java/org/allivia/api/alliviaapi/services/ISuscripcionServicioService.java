package org.allivia.api.alliviaapi.services;

import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import org.allivia.api.alliviaapi.entities.AppPagoSuscripcionPaciente;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionpacienteEntity;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface ISuscripcionServicioService {
    Object findSuscripcionServices();
    AppSuscripcionEntity suscripcionPaciente(long idSuscripcion, long idPaciente);
    AppSuscripcionpacienteEntity getSuscripcionPaciente(long idPaciente);

    AppSuscripcionpacienteEntity updateCancelacion(long idPaciente, String motivo);

    Object suscripcionPaciente(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;


}
