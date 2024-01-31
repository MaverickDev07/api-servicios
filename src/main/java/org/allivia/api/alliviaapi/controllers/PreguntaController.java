package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.services.IPreguntaService;
import org.allivia.api.alliviaapi.services.ISintomaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PreguntaController {
    public static final Logger logger = LogManager.getLogger(PreguntaController.class);
    @Autowired
    private IPreguntaService preguntaService;

    @GetMapping("/pregunta/primera/idSintoma/{id}")
    public ResponseEntity<Object> getPrimerPregunta(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = preguntaService.findByIdPregunta(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/pregunta/id/{id}")
    public ResponseEntity<Object> getIdPregunta(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = preguntaService.findById(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
