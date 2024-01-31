package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.services.IMetodosPagoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class MetodosPagoController {
    public static final Logger logger = LogManager.getLogger(MetodosPagoController.class);
    @Autowired
    private IMetodosPagoService iMetodosPagoService;

    @GetMapping("/metodospago")
    public ResponseEntity<Object> getListMetodosPago(@RequestParam long idPaciente) throws JsonProcessingException {
        try {
            Object result = iMetodosPagoService.findAll(idPaciente);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/metodospago/save")
    public ResponseEntity<Object> save(@RequestBody AppMetodospagoEntity appMetodospagoEntity) throws JsonProcessingException {
        try {
            Object result = iMetodosPagoService.save(appMetodospagoEntity);
            return ResponseEntity.ok(result);
        }catch (PayException e){
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(data);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/metodospago/update")
    public ResponseEntity<Object> update(@RequestBody AppMetodospagoEntity appMetodospagoEntity) {
        try {
            Object obj = iMetodosPagoService.update(appMetodospagoEntity);
            return ResponseEntity.ok(obj);
        } catch (PayException e){
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(data);
        }catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/metodospago/delete")
    public ResponseEntity<Object> delete(@RequestParam long idMetodoPago) throws JsonProcessingException {
        try {
            iMetodosPagoService.delete(idMetodoPago);
            HashMap<Object, Object> result = new HashMap<>();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al eliminar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/card/{idMetodoPago}")
    public ResponseEntity<Object> getCard(@PathVariable long idMetodoPago) throws JsonProcessingException {
        try {
            Object result = iMetodosPagoService.findById(idMetodoPago);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
