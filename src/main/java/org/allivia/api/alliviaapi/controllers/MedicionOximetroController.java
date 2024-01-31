package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppMedicionOximetroEntity;
import org.allivia.api.alliviaapi.services.impl.MedicionOximetroServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/medicionOximetro")
public class MedicionOximetroController extends BaseControllerImpl<AppMedicionOximetroEntity, MedicionOximetroServiceImpl> {
  @GetMapping("/last/{id}")
  public ResponseEntity<?> getLastMedicionOximetroByPaciente(@PathVariable Long id) {
      try {
          return ResponseEntity.status(HttpStatus.OK).body(servicio.getLastMedicionOximetroByPaciente(id));
      } catch(Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
      }
  }

    @GetMapping("/{id}/{fecha}")
    public ResponseEntity<?> getMedicionOximetroByPacienteAndFecha(@PathVariable Long id, @PathVariable String fecha) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getMedicionOximetroByPacienteAndFecha(id, fecha));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }
}

