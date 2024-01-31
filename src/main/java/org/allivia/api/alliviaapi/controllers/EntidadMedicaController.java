package org.allivia.api.alliviaapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppEntidadMedicaEntity;
import org.allivia.api.alliviaapi.services.impl.EntidadMedicaServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/entidadMedica")
public class EntidadMedicaController extends BaseControllerImpl<AppEntidadMedicaEntity, EntidadMedicaServiceImpl> {
    public static final Logger logger = LogManager.getLogger(EntidadMedicaController.class);

    @GetMapping("/tipoEntidad/{tipo}")
    public ResponseEntity<?> getEntidadMedicaByTipo(@PathVariable String tipo) {
        try {
            List<AppEntidadMedicaEntity> result = servicio.getEntidadMedicaByTipoEntidad(tipo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
