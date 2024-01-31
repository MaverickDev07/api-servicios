package org.allivia.api.alliviaapi.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import com.voximplant.apiclient.ClientException;
import com.voximplant.apiclient.VoximplantAPIClient;
import com.voximplant.apiclient.request.GetUsersRequest;
import com.voximplant.apiclient.response.GetUsersResponse;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.entities.models.AppResponseFactura;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.IPaymentService;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.ReceiptModel;
import org.allivia.api.alliviaapi.repositories.*;


import org.allivia.api.alliviaapi.services.impl.SendMailServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;

@Component
public class AgendaCitaServiceImpl implements IAgendaCitaService {

    public static final Logger logger = LogManager.getLogger(AgendaCitaServiceImpl.class);
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;

    @Autowired
    private IPagoRepository iPagoRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IDiagnosticoRepository iDiagnosticoRepository;

    @Autowired
    private IFichaMedicaRepository iFichaMedicaRepository;

    @Autowired
    private IFMedicamentosRepository IFMedicamentosRepository;

    @Autowired
    private ILaboratoriosRepository iLaboratoriosRepository;

    @Autowired
    private ILaboratorioRepository iLaboratorioRepository;

    @Autowired
    private IProgramaRepository iProgramaRepository;

    @Autowired
    private IProgramaFaseRepository iProgramaFaseRepository;

    @Autowired
    private IExamenesRepository iExamenesRepository;

    @Autowired
    private ICompartirArchivosRepository iCompartirArchivosRepository;

    @Autowired
    private IHoraAtencionRepository iHoraAtencionRepository;

    @Autowired
    private IHorasRepository iHorasRepository;

    @Autowired
    private ITipoCitaRepository iTipoCitaRepository;

    @Autowired
    private ITiposConsultaRepository iTiposConsultaRepository;

    @Autowired
    private IAgendaDocumentosRepository iAgendaDocumentosRepository;

    @Autowired
    private IEspecialidadDoctorRepository iEspecialidadDoctorRepository;

    @Autowired
    private IEspecialidadesRepository iEspecialidadesRepository;

    @Autowired
    private IProgramasRepository iProgramasRepository;
    @Autowired
    private IAfiliacionesDoctorService iAfiliacionesDoctorService;

    @Autowired
    private IMedicamentosRepository iMedicamentosRepository;

    @Autowired
    private IAfiliacionesRepository iAfiliacionesRepository;

    @Autowired
    private ITratamientosRepository iTratamientosRepository;

    @Autowired
    private INotificacionService iNotificacionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFacturaRepository iFacturaRepository;

    @Autowired
    private SendMailServiceImpl mailService;

    @Autowired
    private INotificationPushService iNotificationPushService;

    @Autowired
    private ILaboratoriosPacienteService iLaboratoriosPacienteService;

    @Autowired
    private VoximplantAPIClient voximplantAPIClient;
    @Value("${voximplant.application.id}")
    private long applicationId;

    @Autowired
    private IPaymentService iPaymentService;
    @Value("${payment.currency.agendacita}")
    private String currencyBOB;

    @Value("${allivia-node.url}")
    private String facturaUrl;
    @Value("${allivia-node.nit}")
    private String facturaNit;
    @Value("${allivia-node.sucursal}")
    private Integer facturaSucursal;
    @Value("${allivia-node.actividadEconomica}")
    private Integer facturaActEco;

    public Object save(AppAgendacitaEntity appAgendacitaEntity, String token) throws MessagingException, Exception {
        // Revisar: idPaciente, idDoctor, en si lo que llega es de usuarioId en ambos
        /*appAgendacitaEntity.setIdPaciente(userService.findIdPacienteByIdUsuario(appAgendacitaEntity.getIdPaciente()));
        appAgendacitaEntity.setIdDoctor(userService.findIdDoctorByIdUsuario(appAgendacitaEntity.getIdDoctor()));*/
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.validarHorarioAgendado(appAgendacitaEntity.getIdDoctor(),appAgendacitaEntity.getFecha());
        if(appAgendacitaEntityList.size() > 0){
            return null;
        }else {
            ReceiptModel model = iPaymentService.paymentSaleToken(appAgendacitaEntity.getPrecio(), token,currencyBOB);

            AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appAgendacitaEntity.getIdPaciente()).get();
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
            // Create request factura
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
            Map<String, Object> body = ImmutableMap.of( "emisor", facturaNit, "comprador", appAgendacitaEntity.getNitComprador(), "razonSocial", appAgendacitaEntity.getRazonSocial(),"listaItems", Arrays.asList( ImmutableMap.of("articulo", "Pago Consulta Medica", "cantidad", 1, "precioUnitario", appAgendacitaEntity.getPrecio()) ), "actividadEconomica", facturaActEco );
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<AppResponseFactura> sample = rest.postForEntity(String.valueOf(facturaUrl+"/api/izi/facturas"), request, AppResponseFactura.class);
            // Create factura
            AppFacturaEntity appFacturaEntity = new AppFacturaEntity();
            appFacturaEntity.setPaciente(appPacienteEntity);
            appFacturaEntity.setFactura(sample.getBody().getData().getFactura().getLink());
            appFacturaEntity.setFacturaRollo(sample.getBody().getData().getFactura().getPdfRollo());
            appFacturaEntity.setAutorizacion(sample.getBody().getData().getFactura().getAutorizacion());
            appFacturaEntity.setDetalle(facturaUrl+"/api/izi/facturas/"+sample.getBody().getData().get_id());

            if (model.getDecision().equals("ACCEPT")) {
                AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(appAgendacitaEntity.getIdDoctor()).get();
                AppUsuarioEntity appUsuarioDocEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
                int nroPacientes = Integer.parseInt(appDoctorEntity.getPacientes());
                int suma = nroPacientes +1;
                appDoctorEntity.setPacientes(String.valueOf(suma));
                iDoctorRepository.save(appDoctorEntity);
                logger.info("decision=" + model.getDecision() + ", message=" + model.getMessage());
                AppPagoEntity appPagoEntity = new AppPagoEntity();
                appPagoEntity.setDetalle("Pago Consulta Medica");
                appPagoEntity.setMonto(appAgendacitaEntity.getPrecio());
                appPagoEntity.setTipo("Online");
                appPagoEntity.setTransaccion(UUID.randomUUID().toString());
                appPagoEntity.setEstado("Completado");
                iPagoRepository.save(appPagoEntity);

                logger.log(Level.INFO, "Guardar pago" + appPagoEntity.toString());

                appAgendacitaEntity.setEstadoconsulta("Pendiente");
                appAgendacitaEntity.setIdPago(appPagoEntity.getId());

                logger.log(Level.INFO, "Guardar agenda de cita " + appAgendacitaEntity.toString());
                logger.log(Level.INFO, "Cita agendada correctamente");

                if(appAgendacitaEntity.getPrecio() > 0) {
                    AppFacturaEntity appFactura = iFacturaRepository.save(appFacturaEntity);
                    if (appUsuarioEntity.getEmail() != null || !appUsuarioEntity.getEmail().isEmpty()) {
                        mailService.sendConfirmacionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appUsuarioDocEntity.getNombre() + " " + appUsuarioDocEntity.getApellido(), "Consulta Médica Allivia");
                    }
                }

                AppAgendacitaEntity saveAgendaCita = iAgendaCitaRepository.save(appAgendacitaEntity);
                AppNotificacionEntity notificacion = iNotificacionService.saveByAgendaCita(saveAgendaCita, "DOCTOR", "Pendiente");
                // iNotificationPushService.sendPush(saveAgendaCita.getId(), "Cita médica agendada", "Motivo de la consulta: "+saveAgendaCita.getMotivoConsulta());
                return saveAgendaCita;
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", model.getMessage());
                return data;
            }
        }
    }

    public Object save(AppAgendacitaEntity appAgendacitaEntity) throws Exception {
        // Revisar: idPaciente, idDoctor, en si lo que llega es de usuarioId en ambos
        /*appAgendacitaEntity.setIdPaciente(userService.findIdPacienteByIdUsuario(appAgendacitaEntity.getIdPaciente()));
        appAgendacitaEntity.setIdDoctor(userService.findIdDoctorByIdUsuario(appAgendacitaEntity.getIdDoctor()));*/
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.validarHorarioAgendado(appAgendacitaEntity.getIdDoctor(),appAgendacitaEntity.getFecha());
        if(appAgendacitaEntity.getIdTipoconsulta() == 0) {
            appAgendacitaEntity.setIdTipoconsulta(1);
        }
        if(appAgendacitaEntityList.size() > 0) {
            return null;
        } else {
            appAgendacitaEntity.setEstadoconsulta("Pendiente");
            if(appAgendacitaEntity.getTipoAgenda().equals("Premium")) {
                iLaboratoriosPacienteService.reclamarConsultaPremium(appAgendacitaEntity.getIdPaciente());
                appAgendacitaEntity.setEstadoconsulta("Confirmada");
            }

            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(appAgendacitaEntity.getIdDoctor()).get();
            AppUsuarioEntity appUsuarioDocEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
            int nroPacientes = Integer.parseInt(appDoctorEntity.getPacientes());
            int suma = nroPacientes +1;
            appDoctorEntity.setPacientes(String.valueOf(suma));
            iDoctorRepository.save(appDoctorEntity);
            AppPagoEntity appPagoEntity = new AppPagoEntity();
            appPagoEntity.setDetalle("Pago Consulta Medica");
            appPagoEntity.setMonto(appAgendacitaEntity.getPrecio());
            appPagoEntity.setTipo("Online");
            appPagoEntity.setTransaccion(UUID.randomUUID().toString());
            appPagoEntity.setEstado("Completado");
            iPagoRepository.save(appPagoEntity);

            logger.log(Level.INFO, "Guardar pago" + appPagoEntity.toString());
            appAgendacitaEntity.setIdPago(appPagoEntity.getId());

            logger.log(Level.INFO, "Guardar agenda de cita " + appAgendacitaEntity.toString());

            AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appAgendacitaEntity.getIdPaciente()).get();
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
            // Create request factura
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));
            Map<String, Object> body = ImmutableMap.of( "emisor", facturaNit, "comprador", appAgendacitaEntity.getNitComprador(), "razonSocial", appAgendacitaEntity.getRazonSocial(),"listaItems", Arrays.asList( ImmutableMap.of("articulo", "Pago Consulta Medica", "cantidad", 1, "precioUnitario", appAgendacitaEntity.getPrecio()) ), "actividadEconomica", facturaActEco );
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<AppResponseFactura> sample = rest.postForEntity(String.valueOf(facturaUrl+"/api/izi/facturas"), request, AppResponseFactura.class);
            // Create factura
            AppFacturaEntity appFacturaEntity = new AppFacturaEntity();
            appFacturaEntity.setPaciente(appPacienteEntity);
            appFacturaEntity.setFactura(sample.getBody().getData().getFactura().getLink());
            appFacturaEntity.setFacturaRollo(sample.getBody().getData().getFactura().getPdfRollo());
            appFacturaEntity.setAutorizacion(sample.getBody().getData().getFactura().getAutorizacion());
            appFacturaEntity.setDetalle(facturaUrl+"/api/izi/facturas/"+sample.getBody().getData().get_id());

            if(appAgendacitaEntity.getPrecio() > 0) {
                AppFacturaEntity appFactura = iFacturaRepository.save(appFacturaEntity);
                if (appUsuarioEntity.getEmail() != null && !appUsuarioEntity.getEmail().isEmpty()) {
                    mailService.sendConfirmacionEmail(appUsuarioEntity.getEmail(), appFacturaEntity.getFactura(), appUsuarioEntity.getNombre() + " " + appUsuarioEntity.getApellido(), appUsuarioDocEntity.getNombre() + " " + appUsuarioDocEntity.getApellido(), "Consulta Médica Allivia");
                }
            }

            AppAgendacitaEntity saveAgendaCita = iAgendaCitaRepository.save(appAgendacitaEntity);
            AppNotificacionEntity notificacion = iNotificacionService.saveByAgendaCita(saveAgendaCita, "DOCTOR", "Pendiente");
            // iNotificationPushService.sendPush(saveAgendaCita.getId(), "Cita médica agendada", "Motivo de la consulta: "+saveAgendaCita.getMotivoConsulta());
            return saveAgendaCita;
        }
    }

    public Object saveDoctor(AppAgendacitaEntity appAgendacitaEntity) throws Exception {
        iAgendaCitaRepository.updateReconsultaDoctor(appAgendacitaEntity.getIdPaciente());
        appAgendacitaEntity.setEstadoconsulta("Pendiente");
        appAgendacitaEntity.setReconsulta(true);
        logger.log(Level.INFO, "Datos de la agenda de cita" + appAgendacitaEntity.toString());

        AppAgendacitaEntity saveAgendaCita = iAgendaCitaRepository.save(appAgendacitaEntity);
        AppNotificacionEntity notificacion = iNotificacionService.saveByAgendaCita(saveAgendaCita, "DOCTOR", "Reconsulta");
        return saveAgendaCita;
    }

    public Object update(AppAgendacitaEntity appAgendacitaEntity) throws Exception {
        AppAgendacitaEntity appAgendacitaEntity1 = iAgendaCitaRepository.findById(appAgendacitaEntity.getId()).get();

        appAgendacitaEntity1.setEstadoconsulta(appAgendacitaEntity.getEstadoconsulta());
        if (appAgendacitaEntity.getInicioconsulta() != null) {
            appAgendacitaEntity1.setInicioconsulta(appAgendacitaEntity.getInicioconsulta());
        }
        if (appAgendacitaEntity.getFinconsulta() != null) {
            appAgendacitaEntity1.setFinconsulta(appAgendacitaEntity.getFinconsulta());
        }

        if (appAgendacitaEntity.getMotivoCancelacion() != null) {
            appAgendacitaEntity1.setMotivoCancelacion(appAgendacitaEntity.getMotivoCancelacion());
        }

        if (appAgendacitaEntity.getFecha() != null) {
            appAgendacitaEntity1.setFecha(appAgendacitaEntity.getFecha());
        }

        logger.log(Level.INFO, "Actualizacion de la cita agendada" + appAgendacitaEntity.toString());

        AppAgendacitaEntity saveAgendaCita = iAgendaCitaRepository.save(appAgendacitaEntity1);
        AppNotificacionEntity notificacion = iNotificacionService.saveByAgendaCita(saveAgendaCita, "DOCTOR", appAgendacitaEntity1.getEstadoconsulta());
        AppNotificacionEntity notificacion2 = iNotificacionService.saveByAgendaCita(saveAgendaCita, "PACIENTE", appAgendacitaEntity1.getEstadoconsulta());
        iNotificationPushService.sendPush(saveAgendaCita.getId(), "Cita médica", "Estado de la consulta "+appAgendacitaEntity1.getEstadoconsulta());
        return saveAgendaCita;
    }

    public Object getConsultasPacienteDoctor(long idPaciente, String nombreDoctor, List<String> estadoList) {
        logger.log(Level.INFO, "Consulta paciente idPaciente " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList;

        if (estadoList != null && estadoList.size() > 0 && nombreDoctor != null && !nombreDoctor.isEmpty()) {
            logger.log(Level.INFO, "Busqueda por los estados: " + estadoList.toString() + " y busqueda por nombre del doctor: " + nombreDoctor);
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultasPaciente(idPaciente, estadoList, nombreDoctor);
        } else {
            if (estadoList != null && estadoList.size() > 0) {
                logger.log(Level.INFO, "Busqueda por los estados: " + estadoList.toString());
                appAgendacitaEntityList = iAgendaCitaRepository.findConsultasPaciente(idPaciente, estadoList);
            } else {
                if (nombreDoctor != null && !nombreDoctor.isEmpty()) {
                    logger.log(Level.INFO, "Busqueda por nombre del doctor: " + nombreDoctor);
                    appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPacienteDoctor(idPaciente, nombreDoctor);
                } else {
                    logger.log(Level.INFO, "Busqueda por idPaciente: " + idPaciente);
                    appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
                }
            }
        }

        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(row.getIdDoctor()).get();
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            List<AppDoctoresespecialidadesEntity> appEspecialidadEntityList = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());
            List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
            for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : appEspecialidadEntityList) {
                AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                appEspecialidadEntities.add(especialidadEntity);
            }

            List<AppDoctorafiliacionesEntity> afiliacionesServiceList= iAfiliacionesDoctorService.getAfiliacionesDoctor(appDoctorEntity.getId());
            List<AppAfiliacionesEntity> appAfiliacionesEntityList = new ArrayList<>();
            if(afiliacionesServiceList != null){
                for(AppDoctorafiliacionesEntity row2:afiliacionesServiceList){
                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(row2.getIdAfiliaciones()).get();
                    appAfiliacionesEntityList.add(appAfiliacionesEntity);
                }
            }

            appDoctorEntity.setListAfiliaciones(appAfiliacionesEntityList);
            appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
            row.setDoctor(appDoctorEntity);
        }
        logger.log(Level.INFO, "Listado de consultas pacientes: " + appAgendacitaEntityList.toString());
        return getDoctorPacienteInformation(appAgendacitaEntityList);
    }

    public Object getConsultasVoxImplant(String idVoxImplant, String fecha) {
        logger.log(Level.INFO, "Consulta usuario VoxImplant idVoxImplant: " + idVoxImplant + ", con fecha: " + fecha);

        String hora = fecha.split(" ")[1];

        String fechaFormat = fecha.split(" ")[0];
        StringBuilder fechaInicio = new StringBuilder();
        fechaInicio.append(fechaFormat).append(" 00:00:00");

        StringBuilder fechaFin = new StringBuilder();
        fechaFin.append(fechaFormat).append(" 23:59:59");

        System.out.println(fechaInicio + " <-> " + fechaFin);
        /*if ( Comprobar(hora, fechaInicio.toString(), fechaFin.toString()) )
            System.out.println();*/

        List<AppAgendacitaEntity> appAgendacitaEntityList = new ArrayList<>();
        List<AppAgendacitaEntity> appAgendacitaEntityListResponse = new ArrayList<>();
        AppUsuarioEntity user = iUserRepository.findByUsuario(idVoxImplant);
        if ( user.getDescricpion().equals("PACIENTE") ) {
            AppPacienteEntity paciente = iPacienteRepository.findByUsuarioId(user.getUsuarioId());
            logger.log(Level.INFO, "Busqueda por idPaciente: " + paciente.getId());
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPacienteFecha(paciente.getId(), fechaInicio.toString(), fechaFin.toString());
        } else if ( user.getDescricpion().equals("DOCTOR") ) {
            AppDoctorEntity doctor = iDoctorRepository.findByUsuarioId(user.getUsuarioId());
            logger.log(Level.INFO, "Busqueda por idDoctor: " + doctor.getId());
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultaDoctorFecha(doctor.getId(), fechaInicio.toString(), fechaFin.toString());
        }

        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            System.out.println(row.getHorario().split(":")[0] + " <-> " + hora.split(":")[0]);

            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(row.getIdDoctor()).get();
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            List<AppDoctoresespecialidadesEntity> appEspecialidadEntityList = iEspecialidadDoctorRepository.findByIdDoctor(appDoctorEntity.getId());
            List<AppEspecialidadEntity> appEspecialidadEntities = new ArrayList<>();
            for (AppDoctoresespecialidadesEntity rowEspecialidadDoctor : appEspecialidadEntityList) {
                AppEspecialidadEntity especialidadEntity = iEspecialidadesRepository.findById(rowEspecialidadDoctor.getIdEspecialidad()).get();
                appEspecialidadEntities.add(especialidadEntity);
            }

            List<AppDoctorafiliacionesEntity> afiliacionesServiceList= iAfiliacionesDoctorService.getAfiliacionesDoctor(appDoctorEntity.getId());
            List<AppAfiliacionesEntity> appAfiliacionesEntityList = new ArrayList<>();
            if(afiliacionesServiceList != null){
                for(AppDoctorafiliacionesEntity row2:afiliacionesServiceList){
                    AppAfiliacionesEntity appAfiliacionesEntity = iAfiliacionesRepository.findById(row2.getIdAfiliaciones()).get();
                    appAfiliacionesEntityList.add(appAfiliacionesEntity);
                }
            }

            appDoctorEntity.setListAfiliaciones(appAfiliacionesEntityList);
            appDoctorEntity.setListEspecialidad(appEspecialidadEntities);
            row.setDoctor(appDoctorEntity);
            if ( row.getHorario().split(":")[0].equals(hora.split(":")[0]) ) {
                appAgendacitaEntityListResponse.add(row);
            }
        }
        return getDoctorPacienteInformation(appAgendacitaEntityListResponse);
    }

    public Object testVoxImplant(int idVoxImplant) throws IOException, ClientException {
        GetUsersRequest req = new GetUsersRequest();
        req.setApplicationId(applicationId);
        req.setUserId(idVoxImplant);
        GetUsersResponse res = voximplantAPIClient.getUsers(req);
        return res;
    }

    public Object getListPacientesDoctor(long idDoctor, Date fecha) {

        List<AppAgendacitaEntity> appAgendacitaEntityList;
        if (fecha != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String fechaFormat = dateFormat.format(fecha);
            StringBuilder fechaInicio = new StringBuilder();
            fechaInicio.append(fechaFormat).append(" 00:00:00");

            StringBuilder fechaFin = new StringBuilder();
            fechaFin.append(fechaFormat).append(" 23:59:59");

            logger.log(Level.INFO, "Obtener listado paciente del idDoctor: " + idDoctor + ", por la fecha: " + fechaFormat);
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultaDoctor(idDoctor, fechaInicio.toString(),
                    fechaFin.toString());
            logger.log(Level.INFO, "Listado de pacientes por idDoctor: " + idDoctor + ", por la fecha: " + fechaFormat + ", " + appAgendacitaEntityList.toString());
        } else {
            logger.log(Level.INFO, "Obtener listado paciente del idDoctor: " + idDoctor);
            appAgendacitaEntityList = iAgendaCitaRepository.findConsultaDoctor(idDoctor);
            logger.log(Level.INFO, "Listado de pacientes por idDoctor: " + idDoctor + ", " + appAgendacitaEntityList.toString());
        }

        return getDoctorPacienteInformation(appAgendacitaEntityList);
    }

    public List<AppAgendacitaEntity> getHistorialConsultasDoctor(long idDoctor, List<String> estadoList) {

        List<AppAgendacitaEntity> appAgendacitaEntityList;
        if (estadoList.size() > 0 && !estadoList.contains("Todos")) {
            appAgendacitaEntityList = iAgendaCitaRepository.findHistorialAdminConsultas(idDoctor, estadoList);

            logger.log(Level.INFO, "Historial consulta doctor: " + idDoctor + ", y por el estado: " + estadoList.toString() + ", " + appAgendacitaEntityList.toString());
        } else {
            appAgendacitaEntityList = iAgendaCitaRepository.findHistorialConsultas(idDoctor);

            logger.log(Level.INFO, "Historial consulta doctor: " + idDoctor + ", " + appAgendacitaEntityList.toString());
        }

        return getDoctorPacienteInformation(appAgendacitaEntityList);

    }

    public List<AppAgendacitaEntity> getLastPendingHistorialConsultasDoctor(long idDoctor) {
        List<AppAgendacitaEntity> appAgendacitaEntityList;
        appAgendacitaEntityList = Collections.singletonList(iAgendaCitaRepository.findLastHistorialConsultas(idDoctor));

        logger.log(Level.INFO, "Último Historial consulta pendiente doctor: " + idDoctor);
        return getDoctorPacienteInformation(appAgendacitaEntityList);
    }

    public Object findAgendaCitaById(long id) {
        List<AppAgendacitaEntity> appAgendacitaEntityList;
        appAgendacitaEntityList = Collections.singletonList(iAgendaCitaRepository.findAgendaCitaById(id));

        logger.log(Level.INFO, "findAgendaCitaById: " + id);
        return getDoctorPacienteInformation(appAgendacitaEntityList);
    }

    public Object getAdminPacientesDoctor(long idDoctor, List<String> estadoList) {

        List<AppAgendacitaEntity> appAgendacitaEntityList;
        if (estadoList.size() > 0 && !estadoList.contains("Todos")) {
            appAgendacitaEntityList = iAgendaCitaRepository.findHistorialAdminConsultas(idDoctor, estadoList);
            logger.log(Level.INFO, "Administracion de paciente doctor: " + idDoctor + ", y por el estado: " + estadoList.toString() + ", " + appAgendacitaEntityList.toString());
        } else {
            appAgendacitaEntityList = iAgendaCitaRepository.findAdminPacientes(idDoctor);
            logger.log(Level.INFO, "Administracion de paciente doctor: " + idDoctor + ", " + appAgendacitaEntityList.toString());
        }

        return getDoctorPacienteInformation(appAgendacitaEntityList);

    }

    public List<AppAgendacitaEntity> getDoctorPacienteInformation(List<AppAgendacitaEntity> appAgendacitaEntityList) {
        if ( appAgendacitaEntityList.contains(null) || Objects.isNull(appAgendacitaEntityList)  )
            return appAgendacitaEntityList;


        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(row.getIdDoctor()).get();
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();

            appDoctorEntity.setPerfilDoctor(appUsuarioEntity);
            row.setDoctor(appDoctorEntity);

            AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(row.getIdPaciente()).get();
            AppUsuarioEntity appUsuarioEntityPaciente = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
            appPacienteEntity.setPerfilPaciente(appUsuarioEntityPaciente);
            row.setPaciente(appPacienteEntity);
            AppTipocitaEntity appTipocitaEntity = iTipoCitaRepository.findById(row.getIdTipocita()).get();
            row.setTipoCita(appTipocitaEntity);

            AppTipoconsultaEntity appTipoconsultaEntity = iTiposConsultaRepository.findById(row.getIdTipoconsulta()).get();
            row.setTipoConsulta(appTipoconsultaEntity);


            List<AppAgendadocummentosEntity> appAgendadocummentosEntity = iAgendaDocumentosRepository.findByIdAgendacita(row.getId());
            if (appAgendadocummentosEntity != null) {
                row.setMotivoarchivos(appAgendadocummentosEntity);
            }


            if (row.getEstadoconsulta().equals("Finalizada")) {


                AppFichamedicaEntity appFichamedicaEntity = iFichaMedicaRepository.findByIdAgendacita(row.getId());

                if (appFichamedicaEntity != null) {

                    AppFichadiagnosticoEntity appFichadiagnosticoEntity = iDiagnosticoRepository.findByIdFichamedica(appFichamedicaEntity.getId());
                    row.setDiagnostico(appFichadiagnosticoEntity);

                    List<AppFichamedicamentosEntity> appFichamedicamentosEntityList = IFMedicamentosRepository.findByIdFichamedica(appFichamedicaEntity.getId());

                    for (AppFichamedicamentosEntity rowFichamedicamentos : appFichamedicamentosEntityList) {
                        if (rowFichamedicamentos.getIdMedicamento() != null) {
                            Optional<AppMedicamentosEntity> appMedicamentosEntity = iMedicamentosRepository.findById(rowFichamedicamentos.getIdMedicamento());
                            rowFichamedicamentos.setMedicamento(appMedicamentosEntity);
                        }
                    }

                    row.setMedicamentos(appFichamedicamentosEntityList);

                    List<AppFichalaboratoriosEntity> appFichalaboratoriosEntityList = iLaboratoriosRepository
                            .findByIdFichamedica(appFichamedicaEntity.getId());
                    for (AppFichalaboratoriosEntity rowfichaLab : appFichalaboratoriosEntityList) {
                        AppLaboratoriosEntity appLaboratoriosEntity = iLaboratorioRepository.findById(rowfichaLab.getIdLaboratorios()).get();
                        rowfichaLab.setLaboratorio(appLaboratoriosEntity);
                    }
                    row.setLaboratorios(appFichalaboratoriosEntityList);

                    if (appFichamedicaEntity.getIdProgramafase() != null) {
                        AppProgramafaseEntity appProgramafaseEntity = iProgramaFaseRepository
                                .findById(appFichamedicaEntity.getIdProgramafase()).get();
                        if (appProgramafaseEntity != null) {
                            AppProgramaEntity appProgramaEntity = iProgramaRepository
                                    .findById(appProgramafaseEntity.getIdPrograma()).get();
                            appProgramaEntity.setFase(appProgramafaseEntity);
                            row.setPrograma(appProgramaEntity);
                        }
                    }


                    List<AppFichaexamenesEntity> appFichaexamenesEntityList = iExamenesRepository
                            .findByIdFichamedica(appFichamedicaEntity.getId());
                    row.setExamenes(appFichaexamenesEntityList);

                    List<AppFichadocumentosEntity> appFichadocumentosEntities = iCompartirArchivosRepository
                            .findByIdFichamedica(appFichamedicaEntity.getId());
                    row.setArchivos(appFichadocumentosEntities);
                }

            }

        }


        logger.log(Level.INFO, "Informacion de ficha medica paciente cita agendada: " + appAgendacitaEntityList.toString());

        return appAgendacitaEntityList;
    }

    public List<AppAgendacitaEntity> getFindTodos(long id, String sintoma, String nombre) {
        logger.log(Level.INFO, "Busqueda de pacientes por el idDoctor: " + id + " Sintoma: " + sintoma + " NombrePaciente: " + nombre);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaTodos(id, sintoma,
                nombre);

        logger.log(Level.INFO, "Resultado: " + appAgendacitaEntityList);
        return getDoctorPacienteInformation(appAgendacitaEntityList);
    }

    @Override
    public Object getListPacientesDoctorPorMes(long id, Date fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String fechaIni = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        String fechaFin = dateFormat.format(calendar.getTime());


        logger.log(Level.INFO, "Obtener listado de paciente por mes idDoctor: " + id + " fecha: " + fechaIni);


        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaDoctor(id, fechaIni, fechaFin);

        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            List<AppAgendadocummentosEntity> appAgendadocummentosEntityList = iAgendaDocumentosRepository.findByIdAgendacita(row.getId());
            row.setMotivoarchivos(appAgendadocummentosEntityList);
        }

        logger.log(Level.INFO, "Resultado: " + appAgendacitaEntityList.toString());
        return appAgendacitaEntityList;
    }

    public Object getListHorariosFechaDoctor(long idDoctor, String fecha, Boolean restante) {


        logger.log(Level.INFO, "Obtener listado de horario del doctor por fecha, idDoctor: " + idDoctor + " fecha: " + fecha);
        if (restante == null || restante == false) {
            List<AppHorarioatencionEntity> appHorarioatencionEntityList;
            if (fecha == null || fecha.trim().isEmpty()) {
                appHorarioatencionEntityList = iHoraAtencionRepository.findByIdDoctor(idDoctor);
            } else {
                appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(idDoctor,
                        fecha);
            }
            System.out.println("List " + appHorarioatencionEntityList.size());
            List<AppHorasEntity> horasEntityList = new ArrayList<>();
            for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                horasEntityList.add(appHorasEntity);
            }

            logger.log(Level.INFO, "Resultado: " + horasEntityList.toString());
            return horasEntityList;
        } else {
            List<AppHorarioatencionEntity> appHorarioatencionEntityList = iHoraAtencionRepository.findByFecha(idDoctor,
                    fecha);
            System.out.println("List " + appHorarioatencionEntityList.size());
            List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(idDoctor);
            List<AppHorasEntity> horasEntityList = new ArrayList<>();
            for (AppHorarioatencionEntity row : appHorarioatencionEntityList) {
                AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
                String[] hora = appHorasEntity.getDescripcion().split("-");

                String fechaFinal = fecha + " " + hora[0].trim() + ":00";
                if (!appAgendacitaEntityListDoctor.contains(fechaFinal)) {
                    horasEntityList.add(appHorasEntity);
                }

            }
            logger.log(Level.INFO, "Resultado: " + horasEntityList.toString());
            return horasEntityList;
        }

    }

    @Override
    public Object getAgendaCitaActual(long idDoctor,String fecha) {
        LocalTime horaC = LocalTime.now();
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaActual(idDoctor,fecha);
        try {
            for (AppAgendacitaEntity cita : appAgendacitaEntityList) {
                String horarios[] = cita.getHorario().trim().split("-");
                LocalTime horaI = LocalTime.parse(horarios[0].trim());
                LocalTime horaF = LocalTime.parse(horarios[1].trim());
                if ((horaC.isAfter(horaI) && horaC.isBefore(horaF))) {
                    AppPacienteEntity pacienteEntity = iPacienteRepository.findById(cita.getIdPaciente()).get();
                    AppUsuarioEntity usuarioEntity = iUserRepository.findById(pacienteEntity.getUsuarioId()).get();
                    pacienteEntity.setPerfilPaciente(usuarioEntity);
                    cita.setPaciente(pacienteEntity);
                    return cita;
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }


    public List<AppAgendacitaEntity> getLitHistorial(long idPaciente) {
        logger.log(Level.INFO, "Obtener listado de historial con idPaciente: " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList1 = getDoctorPacienteInformation(appAgendacitaEntityList);
        logger.log(Level.INFO, "Listado de historial: " + appAgendacitaEntityList1.toString());
        return appAgendacitaEntityList1;
    }

    public List<AppFichamedicamentosEntity> getListMedicamentos(long idPaciente) {
        logger.log(Level.INFO, "Obtener listado de medicamentos con idPaciente: " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
        List<AppFichamedicamentosEntity> medicamentos = new ArrayList<>();
        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            AppFichamedicaEntity appFichamedicaEntity = iFichaMedicaRepository.findByIdAgendacita(row.getId());
            if (appFichamedicaEntity != null) {
                List<AppFichamedicamentosEntity> appFichamedicamentosEntityList = IFMedicamentosRepository.findByIdFichamedica(appFichamedicaEntity.getId());
                for (AppFichamedicamentosEntity rowMedicamentos : appFichamedicamentosEntityList) {
                    rowMedicamentos.setFecha(row.getFecha());
                    rowMedicamentos.setIdAgendacita(row.getId());
                    medicamentos.add(rowMedicamentos);
                }
                for (AppFichamedicamentosEntity rowFichamedicamentos : appFichamedicamentosEntityList) {
                    if (rowFichamedicamentos.getIdMedicamento() != null) {
                        Optional<AppMedicamentosEntity> appMedicamentosEntity = iMedicamentosRepository.findById(rowFichamedicamentos.getIdMedicamento());
                        rowFichamedicamentos.setMedicamento(appMedicamentosEntity);
                    }
                }
            }
        }

        logger.log(Level.INFO, "Listado de medicamentos: " + medicamentos.toString());
        return medicamentos;
    }

    public List<AppFichalaboratoriosEntity> getListLaboratorios(long idPaciente) {
        logger.log(Level.INFO, "Obtener listado de laboratorios con idPaciente: " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
        List<AppFichalaboratoriosEntity> laboratorios = new ArrayList<>();
        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            AppFichamedicaEntity appFichamedicaEntity = iFichaMedicaRepository.findByIdAgendacita(row.getId());
            if (appFichamedicaEntity != null) {
                List<AppFichalaboratoriosEntity> appFichalaboratoriosEntityList = iLaboratoriosRepository.findByIdFichamedica(appFichamedicaEntity.getId());

                for (AppFichalaboratoriosEntity rowLaboratorio : appFichalaboratoriosEntityList) {
                    rowLaboratorio.setFecha(row.getFecha());
                    laboratorios.add(rowLaboratorio);
                }
            }
        }
        logger.log(Level.INFO, "Listado de laboratorios: " + laboratorios.toString());
        return laboratorios;
    }

    public List<ProgramasEntity> getLitProgramas(long idPaciente) {
        logger.log(Level.INFO, "Obtener listado de programas con idPaciente: " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
        List<ProgramasEntity> programas = new ArrayList<>();
        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            ProgramasEntity programasEntity = iProgramasRepository.findByIdcita(row.getId());
            if(programasEntity != null){
                programas.add(programasEntity);
            }

        }

        logger.log(Level.INFO, "Listado de programas: " + programas.toString());

        return programas;
    }

    public List<TratamientosEntity> getLitTratamientos(long idPaciente) {
        logger.log(Level.INFO, "Obtener listado de tratamientos con idPaciente: " + idPaciente);
        List<AppAgendacitaEntity> appAgendacitaEntityList = iAgendaCitaRepository.findConsultaPaciente(idPaciente);
        List<TratamientosEntity> tratamientosEntityArrayList = new ArrayList<>();
        for (AppAgendacitaEntity row : appAgendacitaEntityList) {
            List<TratamientosEntity> tratamientosEntityList = iTratamientosRepository.findByIdcita(row.getId());
            if(tratamientosEntityList != null){
                for(TratamientosEntity row2:tratamientosEntityList){
                    tratamientosEntityArrayList.add(row2);
                }

            }

        }

        logger.log(Level.INFO, "Listado de tratamientos: " + tratamientosEntityArrayList.toString());
        return tratamientosEntityArrayList;
    }

    public boolean Comprobar(String dActual, String dInicial, String dFinal) throws ParseException {
        Date actual = new SimpleDateFormat("HH:mm").parse(dActual.trim());
        Date inicial = new SimpleDateFormat("HH:mm").parse(dInicial.trim());
        Date dfinal = new SimpleDateFormat("HH:mm").parse(dFinal.trim());
        if(actual.after(inicial) && actual.before(dfinal)){
            return true;
        }
        return false;
    }

}
