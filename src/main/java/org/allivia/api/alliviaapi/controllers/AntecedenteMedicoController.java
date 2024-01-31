package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.AppAntecedenteMedicoEntity;
import org.allivia.api.alliviaapi.services.IAntecedenteMedicosService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class AntecedenteMedicoController {
    public static final Logger logger = LogManager.getLogger(AntecedenteMedicoController.class);
    @Autowired
    private IAntecedenteMedicosService iAntecedenteMedicosService;

    @PostMapping("/antecedenteMedico/add")
    public ResponseEntity<Object> save(@RequestBody AppAntecedenteMedicoEntity appAntecedenteMedicoEntity) throws JsonProcessingException {
        try {
            Object result = iAntecedenteMedicosService.save(appAntecedenteMedicoEntity);
            return ResponseEntity.ok(result);
        }catch (ClientException e){
            logger.error("Error al guardar antecedente medico", e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }catch (Exception e) {
            logger.error("Error al guardar antecedente medico", e);
            HashMap<String, Object> error = new HashMap<>();
            error.put("mensaje", "Error interno");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/antecedenteMedico/{idPaciente}")
    public ResponseEntity<Object> getAntecedenteMedico(@PathVariable int idPaciente) throws JsonProcessingException {
        try {
            Object result = iAntecedenteMedicosService.findByIdPaciente(idPaciente);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener alergias", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
