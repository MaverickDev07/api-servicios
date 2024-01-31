package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppLaboratoriosPacienteEntity;
import org.allivia.api.alliviaapi.services.impl.LaboratoriosPacienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/laboratoriosEmpresaPaciente")
public class LaboratoriosPacienteController extends BaseControllerImpl<AppLaboratoriosPacienteEntity, LaboratoriosPacienteServiceImpl> {
    @GetMapping("/verificar/{idPaciente}")
    public ResponseEntity<?> verificarExamenPaciente(@PathVariable Long idPaciente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.ifExamenPaciente(idPaciente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }

    @Override
    public ResponseEntity<?> save(@RequestBody AppLaboratoriosPacienteEntity labPremiumPaciente) {
        try {
            servicio.reclamarPremium(labPremiumPaciente);
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(labPremiumPaciente));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Error\": \""+e.getMessage()+"\"}");
        }
    }
}
