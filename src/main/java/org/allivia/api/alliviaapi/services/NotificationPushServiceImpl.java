package org.allivia.api.alliviaapi.services;


import com.google.common.collect.ImmutableMap;
import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppDoctorEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.repositories.IAgendaCitaRepository;
import org.allivia.api.alliviaapi.repositories.IDoctorRepository;
import org.allivia.api.alliviaapi.repositories.IPacienteRepository;
import org.allivia.api.alliviaapi.repositories.IUserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;


@Component
public class NotificationPushServiceImpl implements INotificationPushService {
    public static final Logger logger = LogManager.getLogger(NotificationPushServiceImpl.class);
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IDoctorRepository iDoctorRepository;

    @Autowired
    private IPacienteRepository iPacienteRepository;

    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;
    @Autowired
    private Environment environment;


    public Object sendPush(long idCita, String titulo, String mensaje) {
        /// String test = "";
        logger.log(Level.INFO, "Envio de notificacion push al idCita " + idCita + " con el titulo " + titulo + " y mensaje " + mensaje);
        AppAgendacitaEntity appAgendacitaEntity = iAgendaCitaRepository.findById(idCita).get();
        AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(appAgendacitaEntity.getIdDoctor()).get();
        AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(appAgendacitaEntity.getIdPaciente()).get();

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        logger.log(Level.INFO, "|{parametros=" + this.parameters+ ",idDevice=" + idDevice +  ",titulo=" + titulo
//                + ",contenido=" + contenido+"}");
        headers.set("Content-Type", "application/json");
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));

        Boolean resultPaciente = false;
        Boolean resulDoctor = false;

        if ( appPacienteEntity != null ) {
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
            if ( appUsuarioEntity.getIdDevice() != null ) {
                logger.log(Level.INFO, "ID_DEVICES " + appUsuarioEntity.getIdDevice() +" paciente: "+ appUsuarioEntity.getNombre());

                headers.set("Authorization", "Basic " + String.valueOf(environment.getProperty("push.keypaciente")));
                Map<String, Object> body = ImmutableMap.of("app_id", String.valueOf(environment.getProperty("app.idpaciente")), "headings", ImmutableMap.of("en", titulo), "contents", ImmutableMap.of("en", mensaje), "include_player_ids", Arrays.asList(appUsuarioEntity.getIdDevice()));

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<Map> sample = rest.postForEntity(String.valueOf(environment.getProperty("push.url")), request, Map.class);
//        logger.log(Level.INFO, "|{parametros=" + this.parameters+ ",idDevice=" + idDevice +  ",titulo=" + titulo
//                + ",contenido=" + contenido+"} "+sample.getStatusCode());

                /// test = appAgendacitaEntity.toString() + " - " + appDoctorEntity.toString() + " - " + appPacienteEntity.toString() + " - " + appUsuarioEntity.toString() + "  - KeyPaciente: " + environment.getProperty("push.keypaciente") + " - Envio de notificacion Push URL: " + environment.getProperty("push.url") + " - Request: " + request + " -> " + Map.class;
                logger.log(Level.INFO, "Envio de notificacion " + sample.getStatusCode());
                resultPaciente = sample.getStatusCode() == HttpStatus.OK;
            } else
                resultPaciente = false;
        }
        /// return test;

        if ( appDoctorEntity != null ) {
            AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
            if ( appUsuarioEntity.getIdDevice() != null ) {
                logger.log(Level.INFO, "ID_DEVICES " + appUsuarioEntity.getIdDevice() +" dcotor: "+ appUsuarioEntity.getNombre());

                headers.set("Authorization", "Basic " + String.valueOf(environment.getProperty("push.key")));
                Map<String, Object> body = ImmutableMap.of("app_id", String.valueOf(environment.getProperty("app.id")), "headings", ImmutableMap.of("en", titulo), "contents", ImmutableMap.of("en", mensaje), "include_player_ids", Arrays.asList(appUsuarioEntity.getIdDevice()));

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                ResponseEntity<Map> sample = rest.postForEntity(String.valueOf(environment.getProperty("push.url")), request,
                        Map.class);
//        logger.log(Level.INFO, "|{parametros=" + this.parameters+ ",idDevice=" + idDevice +  ",titulo=" + titulo
//                + ",contenido=" + contenido+"} "+sample.getStatusCode());

                logger.log(Level.INFO, "Envio de notificacion " + sample.getStatusCode());
                resulDoctor = sample.getStatusCode() == HttpStatus.OK;
            } else
                resulDoctor = false;
        }

        if( resultPaciente && resulDoctor ) {
            logger.log(Level.INFO, "Envio de notificacion a paciente y doctor fue exitoso");
            return true;
        } else {
            if( resultPaciente ) {
                logger.log(Level.INFO, "Envio de notificacion a paciente fue exitoso");
            }
            if( resulDoctor ) {
                logger.log(Level.INFO, "Envio de notificacion a doctor fue exitoso");
            }
            return false;
        }
    }

    public Object sendPushDocOrPac(long id, String docORpac, String titulo, String mensaje) {
        logger.log(Level.INFO, "Envio de notificacion push al " + docORpac + " con id= "+ id + " con el titulo " + titulo + " y mensaje " + mensaje);
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setAcceptCharset(Arrays.asList(Charset.forName("UTF-8")));

        Boolean resultPaciente = false;
        Boolean resulDoctor = false;

        if ( docORpac.equals("DOCTOR") ) {
            AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(id).get();
            if ( appDoctorEntity != null ) {
                AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appDoctorEntity.getUsuarioId()).get();
                if ( appUsuarioEntity.getIdDevice() != null ) {
                    logger.log(Level.INFO, "ID_DEVICES " + appUsuarioEntity.getIdDevice() +" dcotor: "+ appUsuarioEntity.getNombre());

                    headers.set("Authorization", "Basic " + String.valueOf(environment.getProperty("push.key")));
                    Map<String, Object> body = ImmutableMap.of("app_id", String.valueOf(environment.getProperty("app.id")), "headings", ImmutableMap.of("en", titulo), "contents", ImmutableMap.of("en", mensaje), "include_player_ids", Arrays.asList(appUsuarioEntity.getIdDevice()));

                    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                    ResponseEntity<Map> sample = rest.postForEntity(String.valueOf(environment.getProperty("push.url")), request,
                            Map.class);

                    logger.log(Level.INFO, "Envio de notificacion " + sample.getStatusCode());
                    resulDoctor = sample.getStatusCode() == HttpStatus.OK;
                } else
                    resulDoctor = false;
            }
            if( resulDoctor ) {
                logger.log(Level.INFO, "Envio de notificacion a doctor fue exitoso");
                return true;
            }
        }
        if(docORpac.equals("PACIENTE")) {
            AppPacienteEntity appPacienteEntity = iPacienteRepository.findById(id).get();
            if ( appPacienteEntity != null ) {
                AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(appPacienteEntity.getUsuarioId()).get();
                if ( appUsuarioEntity.getIdDevice() != null ) {
                    logger.log(Level.INFO, "ID_DEVICES " + appUsuarioEntity.getIdDevice() +" paciente: "+ appUsuarioEntity.getNombre());

                    headers.set("Authorization", "Basic " + String.valueOf(environment.getProperty("push.keypaciente")));
                    Map<String, Object> body = ImmutableMap.of("app_id", String.valueOf(environment.getProperty("app.idpaciente")), "headings", ImmutableMap.of("en", titulo), "contents", ImmutableMap.of("en", mensaje), "include_player_ids", Arrays.asList(appUsuarioEntity.getIdDevice()));

                    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
                    ResponseEntity<Map> sample = rest.postForEntity(String.valueOf(environment.getProperty("push.url")), request, Map.class);

                    logger.log(Level.INFO, "Envio de notificacion " + sample.getStatusCode());
                    resultPaciente = sample.getStatusCode() == HttpStatus.OK;
                } else
                    resultPaciente = false;
            }
            if( resultPaciente ) {
                logger.log(Level.INFO, "Envio de notificacion a paciente fue exitoso");
                return true;
            }
        }

        return false;
    }
}
