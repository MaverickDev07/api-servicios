package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppMedicionGlucometroEntity;
import org.allivia.api.alliviaapi.services.impl.MedicionGlucometroServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/medicionGlucometro")
public class MedicionGlucometroController extends BaseControllerImpl<AppMedicionGlucometroEntity, MedicionGlucometroServiceImpl> {
  @GetMapping("/last/{id}")
  public ResponseEntity<?> getLastMedicionGlucometroByPaciente(@PathVariable Long id) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(servicio.getLastMedicionGlucometroByPaciente(id));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
    }
  }

  @GetMapping("/{id}/{fecha}")
  public ResponseEntity<?> getMedicionGlucometroByPacienteAndFecha(@PathVariable Long id, @PathVariable String fecha) {
    try {
      return ResponseEntity.status(HttpStatus.OK).body(servicio.getMedicionGlucometroByPacienteAndFecha(id, fecha));
    } catch(Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
    }
  }
}

