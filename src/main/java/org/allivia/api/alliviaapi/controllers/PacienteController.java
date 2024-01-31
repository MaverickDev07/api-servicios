package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.LaboratoriospacienteEntity;
import org.allivia.api.alliviaapi.services.IPacienteService;
import org.allivia.api.alliviaapi.services.impl.ListarMedicamentosPdf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class PacienteController {
    public static final Logger logger = LogManager.getLogger(PacienteController.class);
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private ListarMedicamentosPdf listarMedicamentosPdf;

    @PostMapping("/users/add/paciente")
    public ResponseEntity<Object> addPaciente(@RequestBody AppUsuarioEntity userEntity) throws JsonProcessingException {
        try {
            Object result = pacienteService.savePaciente(userEntity);

            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                HashMap<String, String> error = new HashMap<>();
                error.put("Error", "El paciente ya se encuentra registrado con una suscripción inicial");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/pacientes")
    public ResponseEntity<Object> getListPacientes(@RequestParam(defaultValue = "") String name, @RequestParam(required = false) long  idDoctor, @RequestParam(required = false) String  fecha) throws JsonProcessingException {
        try {
            Object result;
            if (name == null || name.trim().isEmpty()) {
                result = pacienteService.findAll(idDoctor,fecha);
            } else {
                result = pacienteService.findByNombreContains(name);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pacientes/ultimosPacientes")
    public ResponseEntity<Object> lastPacientes() {
        try {
            List<AppPacienteEntity> result = pacienteService.getLastUsersPaciente();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/perfil/paciente/update")
    public ResponseEntity<Object> update(@RequestBody AppUsuarioEntity userEntity) {
        try {
            AppPacienteEntity user = pacienteService.update(userEntity);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GetMapping("/paciente/laboratorios")
    public ResponseEntity<Object> getListPacientes(@RequestParam long idPaciente) throws JsonProcessingException {
        try {
            List<LaboratoriospacienteEntity> list = pacienteService.getListLaboratoriosPaciente(idPaciente);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paciente/recetas")
    public ResponseEntity<Object> getListRecetas(@RequestParam long idPaciente, @RequestParam String nombre) throws JsonProcessingException {
        try {
            List<AppAgendacitaEntity> list = pacienteService.getListRecetasPaciente(idPaciente,nombre);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paciente/medicamentos/pdf/{idAgendacita}")
    public void generatePDF(HttpServletResponse response, @PathVariable Long idAgendacita) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        listarMedicamentosPdf.export(response, idAgendacita);
    }
}
