package org.allivia.api.alliviaapi.services.impl;

import Invokers.ApiException;
import com.cybersource.authsdk.core.ConfigException;
import com.google.common.collect.ImmutableMap;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.entities.models.AppPacienteTarjeta;
import org.allivia.api.alliviaapi.entities.models.AppResponseFactura;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.IPaymentService;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.ReceiptModel;
import org.allivia.api.alliviaapi.repositories.*;
import org.allivia.api.alliviaapi.security.Encryption;
import org.allivia.api.alliviaapi.services.IFacturacionService;
import org.allivia.api.alliviaapi.services.SuscripcionServicioServiceImpl;
import org.allivia.api.alliviaapi.utils.UtilsPayment;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacturacionServiceImpl extends BaseServiceImpl<AppFacturaEntity, Long> implements IFacturacionService {
    public static final Logger logger = LogManager.getLogger(SuscripcionServicioServiceImpl.class);
    @Autowired
    private IFacturaRepository iFacturaRepository;

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

    @Autowired
    private SuscripcionServicioServiceImpl suscripcionServicioService;

    @Value("${payment.currency}")
    private String currency;

    @Autowired
    private SendMailServiceImpl mailService;

    @Value("${allivia-node.url}")
    private String facturaUrl;
    @Value("${allivia-node.nit}")
    private String facturaNit;
    @Value("${allivia-node.sucursal}")
    private Integer facturaSucursal;
    @Value("${allivia-node.actividadEconomica}")
    private Integer facturaActEco;

    @Value("${payment.apellido}")
    private String apellido;
    @Value("${payment.email}")
    private String email;
    @Value("${payment.celular}")
    private String celular;

    public FacturacionServiceImpl(IBaseRepository<AppFacturaEntity, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public Object saveFactura(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException {
        logger.log(Level.INFO, "REVISAR -> Suscripcion Paciente con facturacion valores de busqueda  idSuscripcion: " + appPagoSuscripcionPaciente.getIdSuscripcion() + ", idPaciente: " + appPagoSuscripcionPaciente.getIdPaciente());
        // logger.log(Level.INFO, "REVISAR -> Servicio saveFactura: " + appPagoSuscripcionPaciente.toString());

        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(appPagoSuscripcionPaciente.getIdSuscripcion()).get();
        if(appSuscripcionEntity.getPrecio() < 1)
            return null;
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appPagoSuscripcionPaciente.getIdPaciente()).get();
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();

        logger.log(Level.INFO, "REVISAR -> Suscripcion: " + appSuscripcionEntity.toString());
        logger.log(Level.INFO, "REVISAR -> Paciente: " + appPacienteEntity.toString());
        logger.log(Level.INFO, "REVISAR -> Usuario: " + appUsuarioEntity.toString());

        // Create request factura
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        Map<String, Object> body = ImmutableMap.of( "emisor", facturaNit, "comprador", appPagoSuscripcionPaciente.getNitComprador(), "razonSocial", appPagoSuscripcionPaciente.getRazonSocial(), "listaItems", Arrays.asList( ImmutableMap.of("articulo", appSuscripcionEntity.getDetalle(), "cantidad", 1, "precioUnitario", appSuscripcionEntity.getPrecio()) ), "actividadEconomica", facturaActEco );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        //Map<String, Object> response = ImmutableMap.of("body", ImmutableMap.of("link", String));
        ResponseEntity<AppResponseFactura> sample = rest.postForEntity(String.valueOf(facturaUrl+"/api/izi/facturas"), request, AppResponseFactura.class);

        AppFacturaEntity appFacturaEntity = new AppFacturaEntity();
        appFacturaEntity.setPaciente(appPacienteEntity);
        appFacturaEntity.setFactura(sample.getBody().getData().getFactura().getLink());
        appFacturaEntity.setFacturaRollo(sample.getBody().getData().getFactura().getPdfRollo());
        appFacturaEntity.setAutorizacion(sample.getBody().getData().getFactura().getAutorizacion());
        appFacturaEntity.setDetalle(facturaUrl+"/api/izi/facturas/"+sample.getBody().getData().get_id());

        //return baseRepository.save(appFacturaEntity);

        if (appPagoSuscripcionPaciente.getToken() != null) {
            if (!appPagoSuscripcionPaciente.getToken().isEmpty()) {
                logger.log(Level.INFO, "paymentSaleToken - Precio: " + appSuscripcionEntity.getPrecio() + "Token: " + appPagoSuscripcionPaciente.getToken() + " currency: " + currency );
                ReceiptModel sale = iPaymentService.paymentSaleToken(appSuscripcionEntity.getPrecio(), appPagoSuscripcionPaciente.getToken(), currency);
                if (sale.getDecision().equals("ACCEPT")) {
                    AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
                    if (appUsuarioEntity.getEmail() != null) {
                        mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle() , "Suscripción Allivia");
                    }
                    logger.log(Level.INFO, "REVISAR -> Primera forma de pago");
                    return suscripcionServicioService.suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
                } else {
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("decision", sale.getDecision());
                    data.put("message", sale.getMessage());
                    return data;
                }
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

        logger.log(Level.INFO, "REVISAR -> MetodosPago: " + appMetodospagoEntity.toString());

        if (appPagoSuscripcionPaciente.isGuardarTarjeta()) {
            ReceiptModel tokenizacion = iPaymentService.tokenizacion(appMetodospagoEntity, appUsuarioEntity, appPagoSuscripcionPaciente.getCvn());
            if (tokenizacion.getDecision().equals("ACCEPT")) {
                ReceiptModel sale = iPaymentService.paymentSaleToken(appSuscripcionEntity.getPrecio(), tokenizacion.getPaymentToken(), currency);
                if (tokenizacion.getDecision().equals("ACCEPT")) {
                    logger.log(Level.INFO, "REVISAR -> Tokenizacion getDecision Ok ");
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
                    AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
                    if (appUsuarioEntity.getEmail() != null) {
                        mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle(), "Suscripción Allivia");
                    }
                    logger.log(Level.INFO, "REVISAR -> Segunda forma de pago");
                    return suscripcionServicioService.suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
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
                AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
                if (appUsuarioEntity.getEmail() != null) {
                    mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle(), "Suscripción Allivia");
                }
                logger.log(Level.INFO, "REVISAR -> Tercera forma de pago");
                return suscripcionServicioService.suscripcion(appSuscripcionEntity, model, appPacienteEntity, appUsuarioEntity);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("decision", model.getDecision());
                data.put("message", model.getMessage());
                return data;
            }
        }
        /*if (appPagoSuscripcionPaciente.isGuardarTarjeta()) {
            System.out.println("Oh siiii");
            System.out.println( appMetodospagoEntity.toString() + " <-> " + appUsuarioEntity.toString() + " <-> " + appPagoSuscripcionPaciente.getCvn() );
            ReceiptModel tokenizacion = iPaymentService.tokenizacion(appMetodospagoEntity, appUsuarioEntity, appPagoSuscripcionPaciente.getCvn());
            System.out.println("tokenizacion: " + tokenizacion.getDecision() + tokenizacion.getMessage());
            if (tokenizacion.getDecision().equals("ACCEPT")) {
                System.out.println("Duuro");
            }
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("decision", "Prueba");
        data.put("message", "joder");
        return data;*/
    }


    @Override
    public ReceiptModel savePayment(AppPacienteTarjeta appPacienteTarjeta, AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        //logger.log(Level.INFO, "REVISAR PAGO -> appPacienteTarjeta: " + appPacienteTarjeta.toString() + " appPagoSuscripcionPaciente" + appPagoSuscripcionPaciente.toString());
        if (appPagoSuscripcionPaciente.getToken() != null && !appPagoSuscripcionPaciente.getToken().isEmpty()) {
            logger.log(Level.INFO, "REVISAR -> Primera forma de pago exitoso, cuando tiene TOKEN");
            ReceiptModel sale = iPaymentService.paymentSaleToken(appPagoSuscripcionPaciente.getMonto(), appPagoSuscripcionPaciente.getToken(), currency);
            return sale;
        }
        String cardType = UtilsPayment.cardType(appPagoSuscripcionPaciente.getNumeroTarjeta());
        String payDate = UtilsPayment.expireDateCard(appPagoSuscripcionPaciente.getFechaVencimiento());

        appPagoSuscripcionPaciente.setTipoTarjeta(cardType);
        appPagoSuscripcionPaciente.setFechaVencimiento(payDate);

        AppMetodospagoEntity appMetodospagoEntity = new AppMetodospagoEntity();
        appMetodospagoEntity.setToken(appPagoSuscripcionPaciente.getToken());
        appMetodospagoEntity.setIdPaciente(appPacienteTarjeta.getIdPaciente());
        appMetodospagoEntity.setPredeterminado(false);
        appMetodospagoEntity.setNombre(appPagoSuscripcionPaciente.getNombre());
        appMetodospagoEntity.setNumeroTarjeta(appPagoSuscripcionPaciente.getNumeroTarjeta());
        appMetodospagoEntity.setTipoTarjeta(cardType);
        appMetodospagoEntity.setFechaVencimiento(payDate);

        if (appPagoSuscripcionPaciente.isGuardarTarjeta()) {
            logger.log(Level.INFO, "REVISAR -> Segunda forma de pago exitoso, cuando tiene NO TIENE TOKEN y Guarda Tarjeta");
            ReceiptModel tokenizacion = iPaymentService.tokenizacionNew(appPacienteTarjeta, appMetodospagoEntity, appPacienteTarjeta.getUsuario(), appPagoSuscripcionPaciente.getCvn());
            if (tokenizacion.getDecision().equals("ACCEPT")) {
                ReceiptModel sale = iPaymentService.paymentSaleToken(appPagoSuscripcionPaciente.getMonto(), tokenizacion.getPaymentToken(), currency);
                if (sale.getDecision().equals("ACCEPT")) {
                    String cardEncrypted = new Encryption().encrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
                    AppMetodospagoEntity existe = iMetodosPagoRepository.findByNumeroTarjeta(cardEncrypted);
                    if (existe != null) {
                        existe.setFechaVencimiento(appPagoSuscripcionPaciente.getFechaVencimiento());
                        existe.setNumeroTarjeta(cardEncrypted);
                        existe.setPredeterminado(false);
                        existe.setTipoTarjeta(appPagoSuscripcionPaciente.getTipoTarjeta());
                        existe.setNombre(appPagoSuscripcionPaciente.getNombre());
                        existe.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                        existe.setToken(tokenizacion.getPaymentToken());
                        AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(existe);
                        logger.log(Level.INFO, "guardar metodo de pago si existe tarjeta: " + appMetodospagoEntity2.toString());
                    } else {
                        AppMetodospagoEntity appMetodospagoEntity1 = new AppMetodospagoEntity();
                        appMetodospagoEntity1.setFechaVencimiento(appPagoSuscripcionPaciente.getFechaVencimiento());
                        appMetodospagoEntity1.setNumeroTarjeta(cardEncrypted);
                        appMetodospagoEntity1.setPredeterminado(false);
                        appMetodospagoEntity1.setTipoTarjeta(appPagoSuscripcionPaciente.getTipoTarjeta());
                        appMetodospagoEntity1.setNombre(appPagoSuscripcionPaciente.getNombre());
                        appMetodospagoEntity1.setIdPaciente(appMetodospagoEntity.getIdPaciente());
                        appMetodospagoEntity1.setToken(tokenizacion.getPaymentToken());
                        AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(appMetodospagoEntity1);
                        logger.log(Level.INFO, "guardar metodo de pago: " + appMetodospagoEntity2.toString());
                    }
                }
                return sale;
            } else {
                return tokenizacion;
            }
        } else {
            logger.log(Level.INFO, "REVISAR -> Tercera forma de pago exitoso, cuando tiene NO TIENE TOKEN y NO Guarda Tarjeta");
            ReceiptModel model = iPaymentService.paymentSaleNew(appPagoSuscripcionPaciente, appPacienteTarjeta);
            return model;
        }
    }

    @Override
    public AppPacienteTarjeta getDataPaciente(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws Exception {
        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(appPagoSuscripcionPaciente.getIdSuscripcion()).get();
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appPagoSuscripcionPaciente.getIdPaciente()).get();
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();

        AppPacienteTarjeta appPacienteTarjeta = new AppPacienteTarjeta();
        appPacienteTarjeta.setIdPaciente(appPacienteEntity.getId());
        if (appUsuarioEntity.getApellido() != null && !appUsuarioEntity.getApellido().isEmpty()) {
            appPacienteTarjeta.setApellido(appUsuarioEntity.getApellido());
        } else {
            appPacienteTarjeta.setApellido(apellido);
        }
        if (appUsuarioEntity.getEmail() != null && !appUsuarioEntity.getEmail().isEmpty()) {
            appPacienteTarjeta.setEmail(appUsuarioEntity.getEmail());
        } else {
            appPacienteTarjeta.setEmail(email);
        }
        if (appUsuarioEntity.getTelefono() != null && !appUsuarioEntity.getTelefono().isEmpty()) {
            appPacienteTarjeta.setCelular(appUsuarioEntity.getTelefono());
        } else {
            appPacienteTarjeta.setCelular(celular);
        }
        appPacienteTarjeta.setPaciente(appPacienteEntity);
        appPacienteTarjeta.setUsuario(appUsuarioEntity);
        appPacienteTarjeta.setSuscripcion(appSuscripcionEntity);

        return appPacienteTarjeta;
    }

    @Override
    public AppSuscripcionEntity saveFacturaSuscripcion(AppPacienteTarjeta appPacienteTarjeta, AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws Exception {
        // Create request factura
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        Map<String, Object> body = ImmutableMap.of( "emisor", facturaNit, "comprador", appPagoSuscripcionPaciente.getNitComprador(), "razonSocial", appPagoSuscripcionPaciente.getRazonSocial(), "listaItems", Arrays.asList( ImmutableMap.of("articulo", appPacienteTarjeta.getSuscripcion().getDetalle(), "cantidad", 1, "precioUnitario", appPacienteTarjeta.getSuscripcion().getPrecio()) ), "actividadEconomica", facturaActEco );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        //Map<String, Object> response = ImmutableMap.of("body", ImmutableMap.of("link", String));
        ResponseEntity<AppResponseFactura> sample = rest.postForEntity(String.valueOf(facturaUrl+"/api/izi/facturas"), request, AppResponseFactura.class);

        AppFacturaEntity appFacturaEntity = new AppFacturaEntity();
        appFacturaEntity.setPaciente(appPacienteTarjeta.getPaciente());
        appFacturaEntity.setFactura(sample.getBody().getData().getFactura().getLink());
        appFacturaEntity.setFacturaRollo(sample.getBody().getData().getFactura().getPdfRollo());
        appFacturaEntity.setAutorizacion(sample.getBody().getData().getFactura().getAutorizacion());
        appFacturaEntity.setDetalle(facturaUrl+"/api/izi/facturas/"+sample.getBody().getData().get_id());

        AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
        if (appPacienteTarjeta.getUsuario().getEmail() != null && !appPacienteTarjeta.getUsuario().getEmail().isEmpty()) {
            mailService.sendSuscripcionEmail(appPacienteTarjeta.getUsuario().getEmail(), appFacturaEntity.getFactura(), appPacienteTarjeta.getUsuario().getNombre() + " " + appPacienteTarjeta.getUsuario().getApellido(), appPacienteTarjeta.getSuscripcion().getDetalle() , "Suscripción Allivia");
        }
        ReceiptModel model =new ReceiptModel();
        model.setDecision("ACCEPT");
        model.setMessage("Pago con targeta fue exitoso");
        return suscripcionServicioService.suscripcion(appPacienteTarjeta.getSuscripcion(), model, appPacienteTarjeta.getPaciente(), appPacienteTarjeta.getUsuario());
    }

    @Override
    public Object saveFacturaTest(AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, ConfigException, ApiException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, MessagingException {
        appPagoSuscripcionPaciente.setTipoTarjeta("001");
        appPagoSuscripcionPaciente.setFechaVencimiento("10-2024");
        logger.log(Level.INFO, "REVISAR -> Suscripcion Paciente con facturacion valores de busqueda  idSuscripcion: " + appPagoSuscripcionPaciente.getIdSuscripcion() + ", idPaciente: " + appPagoSuscripcionPaciente.getIdPaciente());
        // logger.log(Level.INFO, "REVISAR -> Servicio saveFactura test: " + appPagoSuscripcionPaciente.toString());

        AppSuscripcionEntity appSuscripcionEntity = isuscripcionRepository.findById(appPagoSuscripcionPaciente.getIdSuscripcion()).get();
        if(appSuscripcionEntity.getPrecio() < 1)
            return null;
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appPagoSuscripcionPaciente.getIdPaciente()).get();
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();

        logger.log(Level.INFO, "REVISAR -> Suscripcion: " + appSuscripcionEntity.toString());
        logger.log(Level.INFO, "REVISAR -> Paciente: " + appPacienteEntity.toString());
        logger.log(Level.INFO, "REVISAR -> Usuario: " + appUsuarioEntity.toString());

        // Create request factura
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
        Map<String, Object> body = ImmutableMap.of( "emisor", facturaNit, "comprador", appPagoSuscripcionPaciente.getNitComprador(), "razonSocial", appPagoSuscripcionPaciente.getRazonSocial(), "listaItems", Arrays.asList( ImmutableMap.of("articulo", appSuscripcionEntity.getDetalle(), "cantidad", 1, "precioUnitario", appSuscripcionEntity.getPrecio()) ), "actividadEconomica", facturaActEco );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        //Map<String, Object> response = ImmutableMap.of("body", ImmutableMap.of("link", String));
        ResponseEntity<AppResponseFactura> sample = rest.postForEntity(String.valueOf(facturaUrl+"/api/izi/facturas"), request, AppResponseFactura.class);

        AppFacturaEntity appFacturaEntity = new AppFacturaEntity();
        appFacturaEntity.setPaciente(appPacienteEntity);
        appFacturaEntity.setFactura(sample.getBody().getData().getFactura().getLink());
        appFacturaEntity.setFacturaRollo(sample.getBody().getData().getFactura().getPdfRollo());
        appFacturaEntity.setAutorizacion(sample.getBody().getData().getFactura().getAutorizacion());
        appFacturaEntity.setDetalle(facturaUrl+"/api/izi/facturas/"+sample.getBody().getData().get_id());

        // return baseRepository.save(appFacturaEntity);

        if (appPagoSuscripcionPaciente.getToken() != null) {
            if (!appPagoSuscripcionPaciente.getToken().isEmpty()) {
                logger.log(Level.INFO, "paymentSaleToken - Precio: " + appSuscripcionEntity.getPrecio() + "Token: " + appPagoSuscripcionPaciente.getToken() + " currency: " + currency );
                ReceiptModel sale = new ReceiptModel();
                logger.log(Level.INFO, "REVISAR -> Primera forma de pago");
                AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
                if (appUsuarioEntity.getEmail() != null) {
                    mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle() , "Suscripción Allivia");
                }
                return suscripcionServicioService.suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
            }
        }
        /*String cardType = UtilsPayment.cardType(appPagoSuscripcionPaciente.getNumeroTarjeta());
        String payDate = UtilsPayment.expireDateCard(appPagoSuscripcionPaciente.getFechaVencimiento());*/
        String cardType = "Card Type Prueba";
        String payDate = "Pay Date Prueba";

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
        appMetodospagoEntity.setCvc(appPagoSuscripcionPaciente.getCvn());

        logger.log(Level.INFO, "REVISAR -> MetodosPago: " + appMetodospagoEntity.toString());

        if (appPagoSuscripcionPaciente.isGuardarTarjeta()) {
            logger.log(Level.INFO, "REVISAR -> Tokenizacion getDecision Ok ");
            String cardEncrypted = new Encryption().encrypt(appMetodospagoEntity.getNumeroTarjeta(), String.valueOf(appMetodospagoEntity.getIdPaciente()));
            AppMetodospagoEntity existe = iMetodosPagoRepository.findByNumeroTarjeta(cardEncrypted);
            ReceiptModel sale = new ReceiptModel();
            if (existe != null) {
                existe.setFechaVencimiento(appPagoSuscripcionPaciente.getFechaVencimiento());
                existe.setNumeroTarjeta(cardEncrypted);
                existe.setPredeterminado(false);
                existe.setTipoTarjeta(appPagoSuscripcionPaciente.getTipoTarjeta());
                existe.setNombre(appPagoSuscripcionPaciente.getNombre());
                existe.setIdPaciente(appPagoSuscripcionPaciente.getIdPaciente());
                existe.setToken("tokenizacion prueba");
                existe.setCvc(appPagoSuscripcionPaciente.getCvn());
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
                appMetodospagoEntity1.setToken("tokenizacion prueba");
                appMetodospagoEntity1.setCvc(appPagoSuscripcionPaciente.getCvn());
                AppMetodospagoEntity appMetodospagoEntity2 = iMetodosPagoRepository.save(appMetodospagoEntity1);
                logger.log(Level.INFO, "guardar metodo de pago " + appMetodospagoEntity2.toString());
            }
            AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
            if (appUsuarioEntity.getEmail() != null) {
                mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle(), "Suscripción Allivia");
            }
            logger.log(Level.INFO, "REVISAR -> Segunda forma de pago");
            return suscripcionServicioService.suscripcion(appSuscripcionEntity, sale, appPacienteEntity, appUsuarioEntity);
        } else {
            ReceiptModel model = new ReceiptModel();
            logger.info("Suscripcion=" + appSuscripcionEntity.getDescripcion() + ", paciente=" + appUsuarioEntity.getNombre());
            AppFacturaEntity appFactura = baseRepository.save(appFacturaEntity);
            if (appUsuarioEntity.getEmail() != null) {
                mailService.sendSuscripcionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appSuscripcionEntity.getDetalle(), "Suscripción Allivia");
            }
            logger.log(Level.INFO, "REVISAR -> Tercera forma de pago");
            return suscripcionServicioService.suscripcion(appSuscripcionEntity, model, appPacienteEntity, appUsuarioEntity);
        }
    }

    @Override
    public void sendEmail() throws Exception {
        mailService.sendSuscripcionEmail("gary.2810.dav@gmail.com", "enlaceFactura", "Gary Guzman", "Detalle" , "Suscripción Allivia");
    }
}
