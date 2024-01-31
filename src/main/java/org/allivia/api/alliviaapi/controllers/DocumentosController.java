package org.allivia.api.alliviaapi.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.UploadFileResponse;
import org.allivia.api.alliviaapi.services.DocumentServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
public class DocumentosController {
    public static final Logger logger = LogManager.getLogger(DocumentosController.class);
    @Autowired
    private DocumentServices documneStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("tipo") String tipo,

                                         @RequestParam(value = "id") Long id) throws IOException {

        String fileName = documneStorageService.storeFile(file, id, tipo);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()

                .path("/downloadFile/")
                .path(tipo + "/")
                .path(fileName)
                .toUriString();


        return new UploadFileResponse(fileName, fileDownloadUri,

                file.getContentType(), file.getSize());

    }

    @GetMapping("/downloadFile/{tipo}/{nombre}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("nombre") String nombre, @PathVariable("tipo") String tipo,
                                                 HttpServletRequest request) {

        String fileName="";
        if (tipo.equals("PACIENTE") || tipo.equals("DOCTOR")) {
            fileName = documneStorageService.getDocumentNameUsuario(nombre);
        } else {
            if (tipo.equals("LICENCIAMEDICA")) {
                fileName = documneStorageService.getDocumentNameLicencia(nombre);
            } else {
                if (tipo.equals("AGENDACITA")) {
                    fileName = documneStorageService.getAgendaDocumento(nombre);
                } else {
                    if (tipo.equals("PUBLICACION")) {
                        fileName = nombre;
                    } else {
                        if (tipo.equals("FIRMA")) {
                            fileName = nombre;
                        } else {
                            if (tipo.equals("ENTIDADES")) {
                                fileName = nombre;
                            } else {
                                fileName = documneStorageService.getDocumentName(nombre);
                            }
                        }
                    }
                }
            }
        }

        /*System.out.println(fileName);
        return null;*/

        Resource resource = null;

        if (!fileName.isEmpty() || fileName != "") {

            try {

                resource = documneStorageService.loadFileAsResource(fileName, tipo);

            } catch (Exception e) {
                logger.info(e.getMessage());
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }

            // Try to determine file's content type

            String contentType = null;

            try {

                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            } catch (IOException ex) {

                logger.info("Could not determine file type.");

            }

            if (contentType == null) {

                contentType = "application/octet-stream";

            }

            return ResponseEntity.ok()

                    .contentType(MediaType.parseMediaType(contentType))

                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")

                    .body(resource);

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @GetMapping("/documentos/list/fichamedica/{id}")
    public ResponseEntity<Object> getListDocumentos(@PathVariable long id) throws JsonProcessingException {
        try {
            Object result = documneStorageService.getListDocumentFicha(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
