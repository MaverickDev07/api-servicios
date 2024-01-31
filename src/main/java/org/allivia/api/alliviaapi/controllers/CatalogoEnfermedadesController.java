package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppCatalogoEnfermedadesEntity;
import org.allivia.api.alliviaapi.services.impl.CatalogoEnfermedadesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/catalogoEnfermedades")
public class CatalogoEnfermedadesController extends BaseControllerImpl<AppCatalogoEnfermedadesEntity, CatalogoEnfermedadesServiceImpl> {
    /*@Override
    public ResponseEntity<?> save(@RequestBody AppCatalogoEnfermedadesEntity appCatalogoEnfermedadesEntity) {
        try {
            List<String> enfermedades = new ArrayList<>();
            // enfermedades.add("Abrasión corneal");
            // enfermedades.add("Accidentes de tráfico");
            // enfermedades.add("Acné");
            for ( String e: enfermedades ) {
                AppCatalogoEnfermedadesEntity appCatalogo = new AppCatalogoEnfermedadesEntity();
                appCatalogo.setEnfermedad(e);
                appCatalogo.setTipo("");
                servicio.save(appCatalogo);
            }
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Error\": \""+e.getMessage()+"\"}");
        }
    }*/
}
