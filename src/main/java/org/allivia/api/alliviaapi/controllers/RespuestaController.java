package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IRespuestaService;
import org.allivia.api.alliviaapi.services.ISintomaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RespuestaController {
    public static final Logger logger = LogManager.getLogger(RespuestaController.class);
    @Autowired
    private IRespuestaService respuestaService;

    @GetMapping("/respuestas/idPregunta/{id}")
    public ResponseEntity<Object> getRespuestaPregunta(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = respuestaService.findByRepuesta(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/diagnostico/verificador/sintomas/codigo/")
    public ResponseEntity<Object> getDiagnostico(@RequestParam String codigo) throws JsonProcessingException {
        try {
            Object result = respuestaService.findByDiagnostico(codigo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
