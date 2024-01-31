package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppParametrosEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.services.IPacienteService;
import org.allivia.api.alliviaapi.services.IParametroService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
public class ParametroController {
    public static final Logger logger = LogManager.getLogger(ParametroController.class);
    @Autowired
    private IParametroService parametroService;

    @GetMapping("/parametro/clave/{clave}")
    public ResponseEntity<Object> getClaveParametro(@PathVariable String clave) throws JsonProcessingException {
        try {
            AppParametrosEntity result = parametroService.findParametro(clave);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping("/parametro/grupo/{grupo}")
    public ResponseEntity<Object> getParametroByGrupo(@PathVariable String grupo) throws JsonProcessingException {
        try {
            List<AppParametrosEntity> result = parametroService.findByParType(grupo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
