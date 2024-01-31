package org.allivia.api.alliviaapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.services.DocumentServices;
import org.allivia.api.alliviaapi.services.IDoctorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
public class DoctorController {
    public static final Logger logger = LogManager.getLogger(DoctorController.class);
    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private DocumentServices documneStorageService;

    @PostMapping("/doctor/add")
    public ResponseEntity<Object> addPerfilDoctor(@RequestBody AppUsuarioEntity userEntity) throws JsonProcessingException {
        try {
            Object result=doctorService.savePerfilDoctor(userEntity);

            if(result != null){
                return  ResponseEntity.ok(result);
            }else{
                HashMap<String,String> error = new HashMap<>();
                error.put("Error","El doctor ya se encuentra registrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/doctor/firma")
    public ResponseEntity<Object> uploadFirmaDoctor(@RequestParam(value = "idDoctor") Long idDoctor, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            Object data = doctorService.findById(idDoctor);

            if (data != null) {
                String fileName = documneStorageService.storeFile(file, idDoctor, "FIRMA");
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path("FIRMA/")
                        .path(fileName)
                        .toUriString();

                if ( fileDownloadUri != null && fileDownloadUri != "" && !fileDownloadUri.isEmpty() ) {
                    AppDoctorEntity doctorUpdate = doctorService.updateFirma(idDoctor, fileDownloadUri);
                    return ResponseEntity.ok(doctorUpdate);
                } else {
                    HashMap<String,String> error = new HashMap<>();
                    error.put("Error ","No se pudo obtener el URI de la firma");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                }
            } else {
                HashMap<String,String> error = new HashMap<>();
                error.put("Error ","No se encontro un doctor con el id: "+idDoctor);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            logger.error("Error al subir firma para un doctor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/doctor/update")
    public ResponseEntity<Object> updateLicenciaDoctor(@RequestBody AppDoctorEntity appDoctorEntity) throws JsonProcessingException {
        try {
        Object result=doctorService.updateLicenciaDoctor(appDoctorEntity);
        return  ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/doctor/updateInfEducacion")
    public ResponseEntity<Object> updateLicenciaDoctor(@RequestBody InfoEducacion infoEducacion) throws JsonProcessingException {
        try {
            doctorService.updateInfoEducacion(infoEducacion);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/doctor/updateInfLaboral")
    public ResponseEntity updateLicenciaDoctor(@RequestBody InformacionLaboral informacionLaboral) throws JsonProcessingException {
        try {
            doctorService.updateInfoLaboral(informacionLaboral);
            return ResponseEntity.ok().build();
        }catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/doctor/{id}")
    public ResponseEntity getDoctor(@PathVariable(name = "id") long id) throws JsonProcessingException {
        try {
            Object data = doctorService.findById(id);
            return ResponseEntity.ok(data);
        }catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/doctor/especialidad")
    public ResponseEntity<List> getDoctorEspecialidad() throws JsonProcessingException {
        try {
            List result=doctorService.findAllEspecialidad();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
