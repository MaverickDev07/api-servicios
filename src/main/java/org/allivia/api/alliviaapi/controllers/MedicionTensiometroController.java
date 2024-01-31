package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppMedicionTensiometroEntity;
import org.allivia.api.alliviaapi.services.impl.MedicionTensiometroServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/medicionTensiometro")
public class MedicionTensiometroController extends BaseControllerImpl<AppMedicionTensiometroEntity, MedicionTensiometroServiceImpl> {
  @GetMapping("/last/{id}")
  public ResponseEntity<?> getLastMedicionTensiometroByPaciente(@PathVariable Long id) {
      try {
          return ResponseEntity.status(HttpStatus.OK).body(servicio.getLastMedicionTensiometroByPaciente(id));
      } catch(Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
      }
  }

  @GetMapping("/{id}/{fecha}")
  public ResponseEntity<?> getMedicionTensiometroByPacienteAndFecha(@PathVariable Long id, @PathVariable String fecha) {
      try {
          return ResponseEntity.status(HttpStatus.OK).body(servicio.getMedicionTensiometroByPacienteAndFecha(id, fecha));
      } catch(Exception e) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
      }
  }
}
