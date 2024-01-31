package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.IPaymentService;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.repositories.IMetodosPagoRepository;
import org.allivia.api.alliviaapi.repositories.IPacienteRepository;
import org.allivia.api.alliviaapi.repositories.IUserRepository;
import org.allivia.api.alliviaapi.security.Encryption;
import org.allivia.api.alliviaapi.utils.UtilsPayment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@Component
public class MetodosPagoServiceImpl implements IMetodosPagoService {
    public static final Logger logger = LogManager.getLogger(MetodosPagoServiceImpl.class);
    @Autowired
    private IMetodosPagoRepository iMetodosPagoRepository;
    @Autowired
    private IPaymentService iPaymentService;
    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private IUserRepository iUserRepository;

    public Object findAll(long idPaciente) throws Exception {
        List<AppMetodospagoEntity> appMetodospagoEntityList = iMetodosPagoRepository.findAllByIdPaciente(idPaciente);

        for (AppMetodospagoEntity appMetodospagoEntity : appMetodospagoEntityList) {
            appMetodospagoEntity.setNumeroTarjeta(getCardNumber(appMetodospagoEntity));
            appMetodospagoEntity.setTipoTarjeta(getTipo(appMetodospagoEntity.getTipoTarjeta()));
            appMetodospagoEntity.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity.getFechaVencimiento()));
        }
        logger.log(Level.INFO, "Listado de metodos de pago " + appMetodospagoEntityList.toString());
        return appMetodospagoEntityList;
    }

    private String getTipo(String tipoTarjeta) {
        if (tipoTarjeta.equals("001")) {
            return "TipoTarjeta.Visa";
        }
        if (tipoTarjeta.equals("002")) {
            return "TipoTarjeta.Mastercard";
        }
        return "";
    }

    public Object save(AppMetodospagoEntity appMetodospagoEntity) throws PayException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String cardEncripted = new Encryption().encrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
        String cardType = UtilsPayment.cardType(appMetodospagoEntity.getNumeroTarjeta());
        String payDate = UtilsPayment.expireDateCard(appMetodospagoEntity.getFechaVencimiento());
        AppMetodospagoEntity existe = iMetodosPagoRepository.findByNumeroTarjeta(cardEncripted);

        if (appMetodospagoEntity.isPredeterminado()) {
            if (existe != null) {

                AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findPredeterminado(true, appMetodospagoEntity.getIdPaciente());
                if (appMetodospagoEntity1 != null) {
                    appMetodospagoEntity1.setPredeterminado(false);
                    iMetodosPagoRepository.save(appMetodospagoEntity1);
                }

                existe.setFechaVencimiento(payDate);
                existe.setNumeroTarjeta(cardEncripted);
                existe.setPredeterminado(appMetodospagoEntity.isPredeterminado());
                existe.setTipoTarjeta(cardType);
                existe.setNombre(appMetodospagoEntity.getNombre());
                existe.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(existe);
                appMetodospagoEntity2.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity2.getFechaVencimiento()));
                logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity2.toString());
                return appMetodospagoEntity2;
            } else {
                AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findPredeterminado(true, appMetodospagoEntity.getIdPaciente());
                if (appMetodospagoEntity1 != null) {
                    appMetodospagoEntity1.setPredeterminado(false);
                    iMetodosPagoRepository.save(appMetodospagoEntity1);
                }
                appMetodospagoEntity.setTipoTarjeta(cardType);
                appMetodospagoEntity.setFechaVencimiento(payDate);
                return saveNewMethodPayment(appMetodospagoEntity);
            }

        } else {
            if (existe != null) {
                existe.setFechaVencimiento(payDate);
                existe.setNumeroTarjeta(cardEncripted);
                existe.setPredeterminado(appMetodospagoEntity.isPredeterminado());
                existe.setTipoTarjeta(cardType);
                existe.setNombre(appMetodospagoEntity.getNombre());
                existe.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(existe);
                appMetodospagoEntity2.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity2.getFechaVencimiento()));
                logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity2.toString());
                return appMetodospagoEntity2;
            } else {
                appMetodospagoEntity.setTipoTarjeta(cardType);
                appMetodospagoEntity.setFechaVencimiento(payDate);
                return saveNewMethodPayment(appMetodospagoEntity);
            }

        }
    }

    private AppMetodospagoEntity saveNewMethodPayment(AppMetodospagoEntity appMetodospagoEntity) throws PayException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        AppMetodospagoEntity result;
        String cardEncripted = new Encryption().encrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
        AppPacienteEntity appPacienteEntity = new AppPacienteEntity();
        AppUsuarioEntity appUsuarioEntity=new AppUsuarioEntity();
        try{
             appPacienteEntity = iPacienteRepository.findById(appMetodospagoEntity.getIdPaciente()).get();
        }catch (Exception e){
            throw new PayException("El paciente no existe");
        }  try{
             appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
        }catch (Exception e){
            throw new PayException("El usuario no existe");
        }



        String token = iPaymentService.tokenizacion(appMetodospagoEntity, appUsuarioEntity, appMetodospagoEntity.getCvc()).getPaymentToken();
        appMetodospagoEntity.setToken(token);
        appMetodospagoEntity.setNumeroTarjeta(cardEncripted);
        result = iMetodosPagoRepository.save(appMetodospagoEntity);
        result.setFechaVencimiento(UtilsPayment.formatDate(result.getFechaVencimiento()));
        logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity.toString());
        return result;
    }

    public Object update(AppMetodospagoEntity appMetodospagoEntity) throws Exception {

        //AppMetodospagoEntity igual = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
        AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
        String cardDecrypt = new Encryption().decrypt(appMetodospagoEntity1.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity1.getIdPaciente()));

        String cardType = UtilsPayment.cardType(cardDecrypt);
        String payDate = UtilsPayment.expireDateCard(appMetodospagoEntity.getFechaVencimiento());
        appMetodospagoEntity.setNumeroTarjeta(cardDecrypt);
        appMetodospagoEntity.setFechaVencimiento(payDate);
        appMetodospagoEntity.setTipoTarjeta(cardType);
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appMetodospagoEntity.getIdPaciente()).get();
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();

        iPaymentService.updateTokenizacion(appMetodospagoEntity, appUsuarioEntity, appMetodospagoEntity.getCvc());
        appMetodospagoEntity.setNumeroTarjeta(appMetodospagoEntity1.getNumeroTarjeta());
        // if (cardEncrypted.equals(igual.getNumeroTarjeta())) {
        if (appMetodospagoEntity.isPredeterminado()) {
            AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.findPredeterminado(true, appMetodospagoEntity.getIdPaciente());
            if (appMetodospagoEntity2 != null) {
                appMetodospagoEntity2.setPredeterminado(false);
                iMetodosPagoRepository.save(appMetodospagoEntity2);
            }
            // AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
            appMetodospagoEntity1.setFechaVencimiento(payDate);
            //  appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
            appMetodospagoEntity1.setPredeterminado(appMetodospagoEntity.isPredeterminado());
            appMetodospagoEntity1.setTipoTarjeta(cardType);
            appMetodospagoEntity1.setNombre(appMetodospagoEntity.getNombre());
            appMetodospagoEntity1.setIdPaciente(appMetodospagoEntity.getIdPaciente());
            AppMetodospagoEntity appMetodospagoEntity3 = iMetodosPagoRepository.save(appMetodospagoEntity1);
            appMetodospagoEntity3.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity3.getFechaVencimiento()));
            logger.log(Level.INFO, "actualizar metodo de pago " + appMetodospagoEntity3.toString());
            return appMetodospagoEntity3;
        } else {
            // AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
            appMetodospagoEntity1.setFechaVencimiento(payDate);
            // appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
            appMetodospagoEntity1.setPredeterminado(appMetodospagoEntity.isPredeterminado());
            appMetodospagoEntity1.setTipoTarjeta(cardType);
            appMetodospagoEntity1.setNombre(appMetodospagoEntity.getNombre());
            appMetodospagoEntity1.setIdPaciente(appMetodospagoEntity.getIdPaciente());
            AppMetodospagoEntity appMetodospagoEntity3 = iMetodosPagoRepository.save(appMetodospagoEntity1);
            appMetodospagoEntity3.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity3.getFechaVencimiento()));
            logger.log(Level.INFO, "actualizar metodo de pago " + appMetodospagoEntity3.toString());
            return appMetodospagoEntity3;
        }
/*        } else {

            AppMetodospagoEntity existe = iMetodosPagoRepository.findByNumeroTarjeta(cardEncrypted);
            if (existe != null) {
                throw new PayException("Ya se encuentra registrado el numero de tarjeta " + appMetodospagoEntity.getNumeroTarjeta());

            } else {
                if (appMetodospagoEntity.isPredeterminado()) {
                    AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.findPredeterminado(true, appMetodospagoEntity.getIdPaciente());
                    appMetodospagoEntity2.setPredeterminado(false);
                    iMetodosPagoRepository.save(appMetodospagoEntity2);
                    AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
                    appMetodospagoEntity1.setFechaVencimiento(payDate);
                    appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
                    appMetodospagoEntity1.setPredeterminado(appMetodospagoEntity.isPredeterminado());
                    appMetodospagoEntity1.setTipoTarjeta(cardType);
                    appMetodospagoEntity1.setNombre(appMetodospagoEntity.getNombre());
                    appMetodospagoEntity1.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                    AppMetodospagoEntity appMetodospagoEntity3 = iMetodosPagoRepository.save(appMetodospagoEntity1);
                    appMetodospagoEntity3.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity3.getFechaVencimiento()));
                    logger.log(Level.INFO, "actualizar metodo de pago " + appMetodospagoEntity3.toString());
                    return appMetodospagoEntity3;
                } else {
                    AppMetodospagoEntity appMetodospagoEntity1 = iMetodosPagoRepository.findById(appMetodospagoEntity.getId()).get();
                    appMetodospagoEntity1.setFechaVencimiento(payDate);
                    appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
                    appMetodospagoEntity1.setPredeterminado(appMetodospagoEntity.isPredeterminado());
                    appMetodospagoEntity1.setTipoTarjeta(cardType);
                    appMetodospagoEntity1.setNombre(appMetodospagoEntity.getNombre());
                    appMetodospagoEntity1.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                    AppMetodospagoEntity appMetodospagoEntity3 = iMetodosPagoRepository.save(appMetodospagoEntity1);
                    appMetodospagoEntity3.setFechaVencimiento(UtilsPayment.formatDate(appMetodospagoEntity3.getFechaVencimiento()));
                    logger.log(Level.INFO, "actualizar metodo de pago " + appMetodospagoEntity3.toString());
                    return appMetodospagoEntity3;
                }
            }
        }*/
    }

    @Transactional
    public void delete(long idMetodosPago) {
        AppMetodospagoEntity appMetodospagoEntity = iMetodosPagoRepository.findById(idMetodosPago).get();
        iMetodosPagoRepository.deleteById(idMetodosPago);
        logger.log(Level.INFO, "Eliminar metodo de pago " + appMetodospagoEntity.toString());
    }

    @Override
    public Object findById(long idMetodoPago) throws Exception {
        AppMetodospagoEntity appMetodospagoEntity = iMetodosPagoRepository.findById(idMetodoPago).get();
        return new Encryption().decrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
    }

    private String getCardNumber(AppMetodospagoEntity entity) throws Exception {
        StringBuilder stringBuilder = new StringBuilder("**** **** **** ");
        String key = String.valueOf(entity.getIdPaciente());
        String cardNumber = new Encryption().decrypt(entity.getNumeroTarjeta(), key);
        int tamCard = cardNumber.length();
        String lastCardNumber = cardNumber.substring(tamCard - 4);
        return stringBuilder.append(lastCardNumber).toString();
    }
}
