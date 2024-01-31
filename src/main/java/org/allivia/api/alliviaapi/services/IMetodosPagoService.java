package org.allivia.api.alliviaapi.services;


import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface IMetodosPagoService {
    Object findAll(long idPaciente) throws Exception;

    Object save(AppMetodospagoEntity appMetodospagoEntity) throws PayException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    Object update(AppMetodospagoEntity appMetodospagoEntity) throws Exception;

    void delete(long idMetodosPago);

    Object findById(long idMetodoPago) throws Exception;
}
