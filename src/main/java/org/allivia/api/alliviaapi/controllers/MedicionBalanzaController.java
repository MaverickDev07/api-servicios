package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;
import org.allivia.api.alliviaapi.services.impl.MedicionBalanzaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/medicionBalanza")
public class MedicionBalanzaController extends BaseControllerImpl<AppMedicionBalanzaEntity, MedicionBalanzaServiceImpl> {
    @GetMapping("/last/{id}")
    public ResponseEntity<?> getLastMedicionBalanzaByPaciente(@PathVariable Long id) {
        try {
            //return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Medicion balanza\"}");
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getLastMedicionBalanzaByPaciente(id));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

    @GetMapping("/{id}/{fecha}")
    public ResponseEntity<?> getMedicionBalanzaByPacienteAndFecha(@PathVariable Long id, @PathVariable String fecha) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getMedicionBalanzaByPacienteAndFecha(id, fecha));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

}

