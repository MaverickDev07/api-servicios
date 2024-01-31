package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppNotificacionEntity;
import org.allivia.api.alliviaapi.services.impl.NotificacionServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/notificacion")
public class NotificacionController extends BaseControllerImpl<AppNotificacionEntity, NotificacionServiceImpl> {
    @GetMapping("/byIdReceptor")
    public ResponseEntity<?> findAllByIdReceptor(@RequestParam Long idReceptor) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAllByIdReceptor(idReceptor));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

    @GetMapping("/byIdReceptorPaged")
    public ResponseEntity<?> findAllByIdReceptor(@RequestParam Long idReceptor, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAllByIdReceptor(idReceptor, pageable));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @PostMapping("/addCita")
    public ResponseEntity<?> saveByAgendaCita(@RequestBody AppAgendacitaEntity agendacita) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.saveByAgendaCita(agendacita, "PACIENTE", "Pendiente"));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

    @PutMapping("/estado/{id}/{estado}")
    public ResponseEntity<?> updateEstado(@PathVariable Long id,@PathVariable String estado) {
        try {
            int update = servicio.updateEstadoNotificacion(id, estado);
            if (update == 1)
                return ResponseEntity.status(HttpStatus.OK).body("{\"id\": "+id+",\"status\": \"actualizado\"}");
            else
                return ResponseEntity.status(HttpStatus.OK).body("{\"id\": "+id+",\"status\": \"No se pudo actualizar, revise el id\"}");
            // return ResponseEntity.status(HttpStatus.OK).body("{\"id\": "+id+",\"estado\": \""+estado+"\"}");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }
}
