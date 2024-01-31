package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;

import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.services.IFichaDiagnosticoEnfermedadesService;
import org.allivia.api.alliviaapi.services.IFichaMedicaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class FichaMedicaController {
    public static final Logger logger = LogManager.getLogger(FichaMedicaController.class);
    @Autowired
    private IFichaMedicaService iFichaMedicaService;
    @Autowired
    private IFichaDiagnosticoEnfermedadesService iFichaDiagnosticoEnfermedadesService;


    @PostMapping("/fichaMedica/add")
    public ResponseEntity<Object> add(@RequestBody AppFichamedicaEntity appFichamedicaEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveFichaMedica(appFichamedicaEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/diagnostico/add")
    public ResponseEntity<Object> add(@RequestBody AppFichadiagnosticoEntity fichadiagnosticoEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveDiagnostico(fichadiagnosticoEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/diagnosticoEnfermedades/add")
    public ResponseEntity<Object> add(@RequestBody AppFichaDiagnosticoEnfermedadesEntity fichaDiagnosticoEnfermedadesEntity) {
        try {
            Object result = iFichaDiagnosticoEnfermedadesService.save(fichaDiagnosticoEnfermedadesEntity);
            return ResponseEntity.ok(result);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/medicamentos/add")
    public ResponseEntity<Object> add(@RequestBody AppFichamedicamentosEntity fichamedicamentosEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveMedicamentos(fichamedicamentosEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/laboratorios/add")
    public ResponseEntity<Object> add(@RequestBody AppFichalaboratoriosEntity fichalaboratoriosEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveLaboratorios(fichalaboratoriosEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/programaFase/update")
    public ResponseEntity<Object> update(@RequestBody AppFichamedicaEntity fichamedicaEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.updateFichaMedica(fichamedicaEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/examenes/add")
    public ResponseEntity<Object> add(@RequestBody AppFichaexamenesEntity fichaexamenesEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveExamenes(fichaexamenesEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/archivo/add")
    public ResponseEntity<Object> add(@RequestBody AppFichadocumentosEntity fichadocumentosEntity) throws JsonProcessingException {
        try {
            Object result = iFichaMedicaService.saveArchivos(fichadocumentosEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/programas")
    public ResponseEntity<Object> programa() {
        try {
            Object result = iFichaMedicaService.getPrograma();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/programa/fases/idPrograma/{idPrograma}")
    public ResponseEntity<Object> programaFase(@PathVariable long idPrograma) {
        try {
            Object result = iFichaMedicaService.getProgramaFase(idPrograma);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/examenes/", "/examenes/{nombre}"})
    public ResponseEntity<Object> getListExamenNombre(@PathVariable(required = false) String nombre) {
        try {
            Object result = iFichaMedicaService.findExamenNombre(nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = {"/laboratorios/", "/laboratorios/{nombre}"})
    public ResponseEntity<Object> getListLaboratoriosNombre(@PathVariable(required = false) String nombre) {
        try {
            Object result = iFichaMedicaService.findLaboratorioNombre(nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
