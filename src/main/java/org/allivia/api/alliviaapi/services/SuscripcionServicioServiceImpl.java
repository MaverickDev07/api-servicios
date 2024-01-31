package org.allivia.api.alliviaapi.services;


import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.allivia.api.alliviaapi.security.Encryption;
import org.allivia.api.alliviaapi.utils.UtilsPayment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Component
public class SuscripcionServicioServiceImpl implements ISuscripcionServicioService {
    public static final Logger logger = LogManager.getLogger(SuscripcionServicioServiceImpl.class);
    @Autowired
    private ISuscripcionServicioRepository isuscripcionServicioRepository;

    @Autowired
    private ISuscripcionRepository isuscripcionRepository;

    @Autowired
    private IServicioRepository iservicioRepository;

    @Autowired
    private ISuscripcionPacienteRepository iSuscripcionPacienteRepository;

    @Autowired
    private IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository;

    @Autowired
    private IPagoRepository iPagoRepository;

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IPaymentService iPaymentService;

    @Autowired
    private IMetodosPagoRepository iMetodosPagoRepository;

    @Value("${payment.currency}")
    private String currencyUSD;

    public Object findSuscripcionServices() {

        List<AppSuscripcionEntity> resultSuscripcion = isuscripcionRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "REVISAR: " + resultSuscripcion.toString());

        for (AppSuscripcionEntity row : resultSuscripcion) {

            List<AppSuscripcionservicioEntity> appSuscripcionservicioEntities = isuscripcionServicioRepository.findByIdSuscripcion(row.getId());
            List<AppServicioEntity> listServices = new ArrayList<>();
            for (AppSuscripcionservicioEntity rowSServices : appSuscripcionservicioEntities) {
                AppServicioEntity appServicioEntity = iservicioRepository.findById(rowSServices.getIdServicio()).get();
                AppServicioEntity update = new AppServicioEntity();
                update.setCodigo(appServicioEntity.getCodigo());
                update.setId(appServicioEntity.getId());
                if(appServicioEntity.getCodigo().equals("CGG")){
                    //String consultasGratuistas = rowSServices.getCantidad() + " " + appServicioEntity.getDescripcion();
                    String consultasGratuistas = appServicioEntity.getDescripcion();
                    update.setDescripcion(consultasGratuistas);
                }else{
                    update.setDescripcion(appServicioEntity.getDescripcion());
                }
                listServices.add(update);
            }
            row.setListServicios(listServices);
        }

        logger.log(Level.INFO, "Listado de suscripciones con sus servicios " + resultSuscripcion.toString());
        return resultSuscripcion;
    }

    public AppSuscripcionEntity suscripcionPaciente(long idSuscripcion, long idPaciente) {
        logger.log(Level.INFO, "Suscripcion Paciente valores de busqueda  idSuscripcion: " + idSuscripcion + ", idPaciente: " + idPaciente);

        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(idSuscripcion).get();

        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(idPaciente);
        appSuscripcionpacienteEntity.setEstado("ACTUALIZACION");
        appSuscripcionpacienteEntity.setMotivo(appSuscripcionEntity.getDescripcion());
        iSuscripcionPacienteRepository.save(appSuscripcionpacienteEntity);

        AppSuscripcionpacienteEntity suscripcionpacienteEntity = new AppSuscripcionpacienteEntity();
        suscripcionpacienteEntity.setIdPaciente(idPaciente);
        suscripcionpacienteEntity.setIdSuscripcion(idSuscripcion);
        suscripcionpacienteEntity.setEstado("VIGENTE");
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.MONTH, 1);
        suscripcionpacienteEntity.setVigencia(calendar.getTime());
        iSuscripcionPacienteRepository.save(suscripcionpacienteEntity);

        logger.log(Level.INFO, "Guardado " + suscripcionpacienteEntity.toString());

        List<AppSuscripcionservicioEntity> appSuscripcionservicioEntities = isuscripcionServicioRepository.findByIdSuscripcion(appSuscripcionEntity.getId());
        List<AppControlsuscripcionservicioEntity> getServiciosPaciente = iControlSuscripcionServicioRepository.findByIdPaciente(idPaciente);

        for (AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity : getServiciosPaciente) {
            AppControlsuscripcionservicioEntity update = iControlSuscripcionServicioRepository.findById(appControlsuscripcionservicioEntity.getId()).get();
            update.setEstado(false);
            iControlSuscripcionServicioRepository.save(update);
        }
        List<AppServicioEntity> listServices = new ArrayList<>();
        for (AppSuscripcionservicioEntity row : appSuscripcionservicioEntities) {
            AppControlsuscripcionservicioEntity controlsuscripcionservicioEntity = new AppControlsuscripcionservicioEntity();
            controlsuscripcionservicioEntity.setCantidad(row.getCantidad());
            controlsuscripcionservicioEntity.setIdPaciente(idPaciente);
            controlsuscripcionservicioEntity.setIdServicio(row.getIdServicio());
            controlsuscripcionservicioEntity.setEstado(true);
            iControlSuscripcionServicioRepository.save(controlsuscripcionservicioEntity);
            AppServicioEntity appServicioEntity = iservicioRepository.findById(row.getIdServicio()).get();
            listServices.add(appServicioEntity);
        }
        appSuscripcionEntity.setListServicios(listServices);

        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(idPaciente).get();
//        appPacienteEntity.setIdSuscripcion(idSuscripcion);
        appPacienteEntity.setDescripcion(appSuscripcionEntity.getDescripcion());
        iPacienteRepository.save(appPacienteEntity);

        logger.log(Level.INFO, "Guardado " + appPacienteEntity.toString());

        logger.log(Level.INFO, "Resultado " + appSuscripcionEntity.toString());
        return appSuscripcionEntity;
    }


    public Object suscripcionPaciente(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        logger.log(Level.INFO, "Suscripcion Paciente valores de busqueda  idSuscripcion: " + appPagoSuscripcionPaciente.getIdSuscripcion() + ", idPaciente: " + appPagoSuscripcionPaciente.getIdPaciente());

        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(appPagoSuscripcionPaciente.getIdSuscripcion()).get();
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appPagoSuscripcionPaciente.getIdPaciente()).get();
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();

        if (appPagoSuscripcionPaciente.getToken() != null && !appPagoSuscripcionPaciente.getToken().isEmpty()) {
            ReceiptModel sale = iPaymentService.paymentSaleToken(appSuscripcionEntity.getPrecio(), appPagoSuscripcionPaciente.getToken(), currencyUSD);
            if (sale.getDecision().equals("ACCEPT")) {
                return suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("decision", sale.getDecision());
                data.put("message", sale.getMessage());
                return data;
            }
        }
        String cardType = UtilsPayment.cardType(appPagoSuscripcionPaciente.getNumeroTarjeta());
        String payDate = UtilsPayment.expireDateCard(appPagoSuscripcionPaciente.getFechaVencimiento());

        appPagoSuscripcionPaciente.setTipoTarjeta(cardType);
        appPagoSuscripcionPaciente.setFechaVencimiento(payDate);

        AppMetodospagoEntity appMetodospagoEntity = new AppMetodospagoEntity();
        appMetodospagoEntity.setToken(appPagoSuscripcionPaciente.getToken());
        appMetodospagoEntity.setIdPaciente(appPagoSuscripcionPaciente.getIdPaciente());
        appMetodospagoEntity.setPredeterminado(false);
        appMetodospagoEntity.setNombre(appPagoSuscripcionPaciente.getNombre());
        appMetodospagoEntity.setNumeroTarjeta(appPagoSuscripcionPaciente.getNumeroTarjeta());
        appMetodospagoEntity.setTipoTarjeta(cardType);
        appMetodospagoEntity.setFechaVencimiento(payDate);

        if (appPagoSuscripcionPaciente.isGuardarTarjeta()) {
            ReceiptModel tokenizacion = iPaymentService.tokenizacion(appMetodospagoEntity, appUsuarioEntity, appPagoSuscripcionPaciente.getCvn());
            if (tokenizacion.getDecision().equals("ACCEPT")) {
                ReceiptModel sale = iPaymentService.paymentSaleToken(appSuscripcionEntity.getPrecio(), tokenizacion.getPaymentToken(), currencyUSD);
                if (tokenizacion.getDecision().equals("ACCEPT")) {
                    String cardEncrypted = new Encryption().encrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
                    AppMetodospagoEntity existe = iMetodosPagoRepository.findByNumeroTarjeta(cardEncrypted);
                    if (existe != null) {
                        existe.setFechaVencimiento(appPagoSuscripcionPaciente.getFechaVencimiento());
                        existe.setNumeroTarjeta(cardEncrypted);
                        existe.setPredeterminado(false);
                        existe.setTipoTarjeta(appPagoSuscripcionPaciente.getTipoTarjeta());
                        existe.setNombre(appPagoSuscripcionPaciente.getNombre());
                        existe.setIdPaciente(appPagoSuscripcionPaciente.getIdPaciente());
                        existe.setToken(tokenizacion.getPaymentToken());
                        AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(existe);
                        logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity2.toString());
                    } else {
                        AppMetodospagoEntity appMetodospagoEntity1 = new AppMetodospagoEntity();
                        appMetodospagoEntity1.setFechaVencimiento(appPagoSuscripcionPaciente.getFechaVencimiento());
                        appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
                        appMetodospagoEntity1.setPredeterminado(false);
                        appMetodospagoEntity1.setTipoTarjeta(appPagoSuscripcionPaciente.getTipoTarjeta());
                        appMetodospagoEntity1.setNombre(appPagoSuscripcionPaciente.getNombre());
                        appMetodospagoEntity1.setIdPaciente(appPagoSuscripcionPaciente.getIdPaciente());
                        appMetodospagoEntity1.setToken(tokenizacion.getPaymentToken());
                        AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(appMetodospagoEntity1);
                        logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity2.toString());
                    }
                    return suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
                } else {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("decision", sale.getDecision());
                    data.put("message", sale.getMessage());
                    return data;
                }
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("decision", tokenizacion.getDecision());
                data.put("message", tokenizacion.getMessage());
                return data;
            }
        } else {
            ReceiptModel model = iPaymentService.paymentSale(appPagoSuscripcionPaciente, appUsuarioEntity);
            logger.info("Suscripcion=" + appSuscripcionEntity.getDescripcion() + ", paciente=" + appUsuarioEntity.getNombre());
            if (model.getDecision().equals("ACCEPT")) {
                return suscripcion(appSuscripcionEntity, model, appPacienteEntity, appUsuarioEntity);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("decision", model.getDecision());
                data.put("message", model.getMessage());
                return data;
            }
        }

    }

    public AppSuscripcionEntity suscripcion(AppSuscripcionEntity appSuscripcionEntity, ReceiptModel model, AppPacienteEntity appPacienteEntity, AppUsuarioEntity appUsuarioEntity) {
        logger.info("decision=" + model.getDecision() + ", message=" + model.getMessage());
        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(appPacienteEntity.getId());

        appSuscripcionpacienteEntity.setEstado("ACTUALIZACION");
        appSuscripcionpacienteEntity.setMotivo(appSuscripcionEntity.getDescripcion());
        iSuscripcionPacienteRepository.save(appSuscripcionpacienteEntity);

        AppSuscripcionpacienteEntity suscripcionpacienteEntity = new AppSuscripcionpacienteEntity();
        suscripcionpacienteEntity.setIdPaciente(appPacienteEntity.getId());
        suscripcionpacienteEntity.setIdSuscripcion(appSuscripcionEntity.getId());
        suscripcionpacienteEntity.setEstado("VIGENTE");
        suscripcionpacienteEntity.setTipoAgenda(appSuscripcionEntity.getTipoAgenda());
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.MONTH, 1);
        suscripcionpacienteEntity.setVigencia(calendar.getTime());

        iSuscripcionPacienteRepository.save(suscripcionpacienteEntity);

        logger.log(Level.INFO, "Guardado " + suscripcionpacienteEntity.toString());

        List<AppSuscripcionservicioEntity> appSuscripcionservicioEntities = isuscripcionServicioRepository.findByIdSuscripcion(appSuscripcionEntity.getId());
        List<AppControlsuscripcionservicioEntity> getServiciosPaciente = iControlSuscripcionServicioRepository.findByIdPaciente(appPacienteEntity.getId());

        for (AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity : getServiciosPaciente) {
            AppControlsuscripcionservicioEntity update = iControlSuscripcionServicioRepository.findById(appControlsuscripcionservicioEntity.getId()).get();
            update.setEstado(false);
            iControlSuscripcionServicioRepository.save(update);
        }
        List<AppServicioEntity> listServices = new ArrayList<>();
        for (AppSuscripcionservicioEntity row : appSuscripcionservicioEntities) {
            AppControlsuscripcionservicioEntity controlsuscripcionservicioEntity = new AppControlsuscripcionservicioEntity();
            controlsuscripcionservicioEntity.setCantidad(row.getCantidad());
            controlsuscripcionservicioEntity.setIdPaciente(appPacienteEntity.getId());
            controlsuscripcionservicioEntity.setIdServicio(row.getIdServicio());
            controlsuscripcionservicioEntity.setEstado(true);
            iControlSuscripcionServicioRepository.save(controlsuscripcionservicioEntity);
            AppServicioEntity appServicioEntity = iservicioRepository.findById(row.getIdServicio()).get();
            listServices.add(appServicioEntity);
        }
        appSuscripcionEntity.setListServicios(listServices);

        appPacienteEntity.setDescripcion(appSuscripcionEntity.getDescripcion());
        iPacienteRepository.save(appPacienteEntity);

        logger.log(Level.INFO, "Guardado " + appPacienteEntity.toString());

        logger.log(Level.INFO, "Resultado " + appSuscripcionEntity.toString());

        logger.info("Paciente " + appUsuarioEntity.getNombre() + " suscrito correctamente");

        return appSuscripcionEntity;
    }


    public AppSuscripcionpacienteEntity getSuscripcionPaciente(long idPaciente) {
        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(idPaciente);
        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(appSuscripcionpacienteEntity.getIdSuscripcion()).get();

        List<AppSuscripcionservicioEntity> appSuscripcionservicioEntityList = isuscripcionServicioRepository.findByIdSuscripcion(appSuscripcionEntity.getId());
        List<AppServicioEntity> listServices = new ArrayList<>();
        for (AppSuscripcionservicioEntity rowSServices : appSuscripcionservicioEntityList) {
            AppServicioEntity appServicioEntity = iservicioRepository.findById(rowSServices.getIdServicio()).get();

            if(appServicioEntity.getCodigo().equals("CGG")){
                //String consultasGratuistas = rowSServices.getCantidad() + " " + appServicioEntity.getDescripcion();
                String consultasGratuistas = appServicioEntity.getDescripcion();
                appServicioEntity.setDescripcion(consultasGratuistas);
            }

            listServices.add(appServicioEntity);
        }
        appSuscripcionEntity.setListServicios(listServices);
        if (appSuscripcionpacienteEntity.getIdPago() != null) {
            AppPagoEntity appPagoEntity = iPagoRepository.findById(appSuscripcionpacienteEntity.getIdPago()).get();
            appSuscripcionpacienteEntity.setPago(appPagoEntity);
        }
        appSuscripcionpacienteEntity.setSuscripcion(appSuscripcionEntity);


        return appSuscripcionpacienteEntity;

    }

    public AppSuscripcionpacienteEntity updateCancelacion(long idPaciente, String motivo) {
        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(idPaciente);
        appSuscripcionpacienteEntity.setEstado("CANCELADO");
        appSuscripcionpacienteEntity.setMotivo(motivo);
        iSuscripcionPacienteRepository.save(appSuscripcionpacienteEntity);

        List<AppControlsuscripcionservicioEntity> getServiciosPaciente = iControlSuscripcionServicioRepository.findByIdPaciente(idPaciente);

        for (AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity : getServiciosPaciente) {
            AppControlsuscripcionservicioEntity update = iControlSuscripcionServicioRepository.findById(appControlsuscripcionservicioEntity.getId()).get();
            update.setEstado(false);
            iControlSuscripcionServicioRepository.save(update);
        }

        AppSuscripcionpacienteEntity suscripcionpacienteEntity = new AppSuscripcionpacienteEntity();
        suscripcionpacienteEntity.setIdPaciente(idPaciente);
        suscripcionpacienteEntity.setIdSuscripcion(1);
        suscripcionpacienteEntity.setEstado("VIGENTE");
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.MONTH, 1);
        suscripcionpacienteEntity.setVigencia(calendar.getTime());

        iSuscripcionPacienteRepository.save(suscripcionpacienteEntity);

        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById((long) 1).get();
        appSuscripcionpacienteEntity.setSuscripcion(appSuscripcionEntity);

        List<AppSuscripcionservicioEntity> appSuscripcionservicioEntities = isuscripcionServicioRepository.findByCodigo("I");

        for (AppSuscripcionservicioEntity row : appSuscripcionservicioEntities) {
            AppControlsuscripcionservicioEntity controlsuscripcionservicioEntity = new AppControlsuscripcionservicioEntity();
            controlsuscripcionservicioEntity.setCantidad(row.getCantidad());
            controlsuscripcionservicioEntity.setIdPaciente(idPaciente);
            controlsuscripcionservicioEntity.setIdServicio(row.getIdServicio());
            controlsuscripcionservicioEntity.setEstado(true);
            iControlSuscripcionServicioRepository.save(controlsuscripcionservicioEntity);

            logger.log(Level.INFO, "Listado de servicios ofertados " + controlsuscripcionservicioEntity.toString());
        }

        return appSuscripcionpacienteEntity;
    }


}