package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppObjetivosSeleccionadosEntity;
import org.allivia.api.alliviaapi.services.impl.ObjetivosSeleccionadosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/objetivosSelecionados")
public class ObjetivosSeleccionadosController extends BaseControllerImpl<AppObjetivosSeleccionadosEntity, ObjetivosSeleccionadosServiceImpl> {
    @GetMapping("/byIdUsuario")
    public ResponseEntity<?> findAllByIdUsuario(@RequestParam Long usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findObjetivosSeleccionadosByUsuario(usuarioId));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \""+e.getMessage()+"\"}");
        }
    }
}
