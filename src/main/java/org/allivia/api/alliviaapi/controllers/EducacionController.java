package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppEducacionEntity;
import org.allivia.api.alliviaapi.services.IEducacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EducacionController {
    public static final Logger logger = LogManager.getLogger(EducacionController.class);
    @Autowired
    private IEducacionService iEducacionService;

    @PostMapping("/educacion/add")
    public ResponseEntity<Object> Add(@RequestBody AppEducacionEntity educacionEntity) throws JsonProcessingException {
        try {
            Object result = iEducacionService.save(educacionEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
