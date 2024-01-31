package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppSeguimientoPremiumEntity;
import org.allivia.api.alliviaapi.services.impl.SeguimientoPremiumServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/seguimientoPremium")
public class SeguimientoPremiumController extends BaseControllerImpl<AppSeguimientoPremiumEntity, SeguimientoPremiumServiceImpl> {
    @GetMapping("/paciente/{id}")
    public ResponseEntity<?> findAllByIdUsuario(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findSeguimientoPremiumPaciente(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }
}
