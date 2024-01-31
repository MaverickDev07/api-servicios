package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.allivia.api.alliviaapi.services.IEducacionService;
import org.allivia.api.alliviaapi.services.INotificationPushService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class NotificationPushController {
    public static final Logger logger = LogManager.getLogger(NotificationPushController.class);
    @Autowired
    private INotificationPushService iNotificationPushService;


    @GetMapping("/notification/push")
    public ResponseEntity<Object> getListAdminPacientesDoctor(@RequestParam long idCita, @RequestParam String titulo, @RequestParam String mensaje) throws JsonProcessingException {
        try {
            Object result = iNotificationPushService.sendPush(idCita, titulo, mensaje);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al notificar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/notification/push/message/{tipo}")
    public ResponseEntity<Object> getListAdminPacienteOrDoctor(@PathVariable("tipo") String tipo, @RequestParam long id, @RequestParam String titulo, @RequestParam String mensaje) throws JsonProcessingException {
        try {
            Object result = iNotificationPushService.sendPushDocOrPac(id, tipo, titulo, mensaje);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al notificar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
