package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppAgendadocummentosEntity;
import org.allivia.api.alliviaapi.entities.AppDoctorEntity;
import org.allivia.api.alliviaapi.entities.AppFichadocumentosEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.repositories.IAgendaDocumentosRepository;
import org.allivia.api.alliviaapi.repositories.ICompartirArchivosRepository;
import org.allivia.api.alliviaapi.repositories.IDoctorRepository;
import org.allivia.api.alliviaapi.repositories.IUserRepository;
import org.allivia.api.alliviaapi.security.DocumentStorageException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


/**
 * Created by yeri_ on 3/30/2021.
 */
@Service
public class DocumentServices {
    public static final Logger logger = LogManager.getLogger(DocumentServices.class);
    private final Path fileStorageLocation;

    @Autowired
    ICompartirArchivosRepository docStorageRepo;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IAgendaDocumentosRepository iAgendaDocumentosRepository;

    @Autowired
    IDoctorRepository iDoctorRepository;

    @Autowired
    public DocumentServices(AppFichadocumentosEntity fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getPath())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            logger.log(Level.WARN, "Could not create the directory where the uploaded files will be stored." + ex);
            throw new DocumentStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, long id, String tipo) throws IOException {
        // Normalize file name
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = "";
        try {
            // Check if the file's name contains invalid characters
            if (originalFileName.contains("..")) {
                logger.log(Level.WARN, "Sorry! Filename contains invalid path sequence" + originalFileName);
                throw new DocumentStorageException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = null;
            if (tipo.equals("PACIENTE")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("paciente/"+fileName);
                logger.log(Level.INFO, "Ruta directorio paciente " + targetLocation.toString());
                AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(id).get();
                appUsuarioEntity.setNombrearchivo(fileName);
                appUsuarioEntity.setPath(targetLocation.toString());
                iUserRepository.save(appUsuarioEntity);
                logger.log(Level.INFO, "Guardado correctamente " + appUsuarioEntity.toString());
            }

            if (tipo.equals("DOCTOR")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("doctor/" + fileName);
                logger.log(Level.INFO, "Ruta directorio doctor" + targetLocation.toString());
                AppUsuarioEntity appUsuarioEntity = iUserRepository.findById(id).get();
                appUsuarioEntity.setNombrearchivo(fileName);
                appUsuarioEntity.setPath(targetLocation.toString());
                iUserRepository.save(appUsuarioEntity);
                logger.log(Level.INFO, "Guardado correctamente " + appUsuarioEntity.toString());
            }

            if (tipo.equals("FICHAMEDICA")) {

                AppFichadocumentosEntity appFichadocumentosEntity = new AppFichadocumentosEntity();
                appFichadocumentosEntity.setIdFichamedica(id);
                appFichadocumentosEntity.setFormato(file.getContentType());
                appFichadocumentosEntity.setDescripcion(file.getName());
                AppFichadocumentosEntity guardado = docStorageRepo.save(appFichadocumentosEntity);
                fileName = guardado.getId() + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("fichaMedica/" + fileName);
                logger.log(Level.INFO, "Ruta directorio fichaMedica " + targetLocation.toString());
                AppFichadocumentosEntity get = docStorageRepo.findById(guardado.getId()).get();
                get.setPath(targetLocation.toString());
                get.setNombrearchivo(fileName);
                docStorageRepo.save(get);
                logger.log(Level.INFO, "Guardado correctamente " + get.toString() + " Ficha documento: " + guardado.toString());
            }

            if (tipo.equals("LICENCIAMEDICA")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("licenciaMedica/" + fileName);
                logger.log(Level.INFO, "Ruta directorio licenciaMedica " + targetLocation.toString());
                AppDoctorEntity appDoctorEntity = iDoctorRepository.findById(id).get();
                appDoctorEntity.setNombrearchivo(fileName);
                appDoctorEntity.setPath(targetLocation.toString());
                iDoctorRepository.save(appDoctorEntity);
                logger.log(Level.INFO, "Guardado correctamente " + appDoctorEntity.toString());
            }

            if (tipo.equals("AGENDACITA")) {

                AppAgendadocummentosEntity appAgendadocummentosEntity = new AppAgendadocummentosEntity();
                appAgendadocummentosEntity.setIdAgendacita(id);
                AppAgendadocummentosEntity guardado = iAgendaDocumentosRepository.save(appAgendadocummentosEntity);
                fileName = guardado.getId() + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("agendaCita/" + fileName);
                logger.log(Level.INFO, "Ruta directorio agendacita " + targetLocation.toString());

                AppAgendadocummentosEntity update = iAgendaDocumentosRepository.findById(guardado.getId()).get();
                update.setNombrearchivo(fileName);
                iAgendaDocumentosRepository.save(update);
                logger.log(Level.INFO, "Guardado correctamente " + appAgendadocummentosEntity.toString());
            }

            if (tipo.equals("PUBLICACION")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("publicacion/" + fileName);
                logger.log(Level.INFO, "Ruta directorio publicacion " + targetLocation.toString());
            }

            if (tipo.equals("FIRMA")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("firma/" + fileName);
                logger.log(Level.INFO, "Ruta directorio firma " + targetLocation.toString());
            }

            if (tipo.equals("ENTIDADES")) {
                fileName = id + "_" + file.getOriginalFilename();
                targetLocation = this.fileStorageLocation.resolve("entidades/" + fileName);
                logger.log(Level.INFO, "Ruta directorio entidades medicas " + targetLocation.toString());
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            logger.log(Level.WARN, "Could not store file " + fileName + ". Please try again!", ex);
            throw new DocumentStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

        return fileName;
    }

    public Resource loadFileAsResource(String fileName, String tipo) throws Exception {

        try {
            Path targetLocation = null;
            if (tipo.equals("PACIENTE")) {
                targetLocation = this.fileStorageLocation.resolve("paciente/" + fileName);
                logger.log(Level.INFO, "Ruta directorio paciente " + targetLocation.toString());
            }

            if (tipo.equals("DOCTOR")) {
                targetLocation = this.fileStorageLocation.resolve("doctor/" + fileName);
                logger.log(Level.INFO, "Ruta directorio doctor " + targetLocation.toString());
            }

            if (tipo.equals("FICHAMEDICA")) {
                targetLocation = this.fileStorageLocation.resolve("fichaMedica/" + fileName);
                logger.log(Level.INFO, "Ruta directorio fichaMedica " + targetLocation.toString());
            }

            if (tipo.equals("LICENCIAMEDICA")) {
                targetLocation = this.fileStorageLocation.resolve("licenciaMedica/" + fileName);
                logger.log(Level.INFO, "Ruta directorio licenciaMedica " + targetLocation.toString());
            }

            if (tipo.equals("AGENDACITA")) {
                targetLocation = this.fileStorageLocation.resolve("agendaCita/" + fileName);
                logger.log(Level.INFO, "Ruta directorio agendaCita " + targetLocation.toString());
            }

            if (tipo.equals("PUBLICACION")) {
                targetLocation = this.fileStorageLocation.resolve("publicacion/" + fileName);
                logger.log(Level.INFO, "Ruta directorio publicacion " + targetLocation.toString());
            }

            if (tipo.equals("FIRMA")) {
                targetLocation = this.fileStorageLocation.resolve("firma/" + fileName);
                logger.log(Level.INFO, "Ruta directorio firma " + targetLocation.toString());
            }

            if (tipo.equals("ENTIDADES")) {
                targetLocation = this.fileStorageLocation.resolve("entidades/" + fileName);
                logger.log(Level.INFO, "Ruta directorio entidades médicas: " + targetLocation.toString());
            }

            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.log(Level.WARN, "File not found " + fileName);
                throw new FileNotFoundException("File not found " + fileName);
            }

        } catch (MalformedURLException ex) {
            logger.log(Level.WARN, "File not found " + fileName);
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    public String getDocumentName(String nombre) {
        logger.log(Level.INFO, "Busqueda de archivo por nombre " + nombre);
        AppFichadocumentosEntity appFichadocumentosEntity = docStorageRepo.findByNombrearchivo(nombre);
        if ( appFichadocumentosEntity != null ) {
            logger.log(Level.INFO, "Resultado " + appFichadocumentosEntity.toString());
            return appFichadocumentosEntity.getNombrearchivo();
        } else {
            return "";
        }
    }

    public String getDocumentNameUsuario(String nombre) {
        logger.log(Level.INFO, "Busqueda de archivo por nombre " + nombre);
        AppUsuarioEntity appUsuarioEntity = iUserRepository.findByNombrearchivo(nombre);
        if ( appUsuarioEntity != null ) {
            logger.log(Level.INFO, "Resultado " + appUsuarioEntity.toString());
            return appUsuarioEntity.getNombrearchivo();
        } else {
            return "";
        }
    }

    public String getDocumentNameLicencia(String nombre) {
        logger.log(Level.INFO, "Busqueda de archivo por nombre " + nombre);
        AppDoctorEntity appDoctorEntity = iDoctorRepository.findByNombrearchivo(nombre);
        if ( appDoctorEntity != null ) {
            logger.log(Level.INFO, "Resultado " + appDoctorEntity.toString());
            return appDoctorEntity.getNombrearchivo();
        } else {
            return "";
        }
    }

    public String getAgendaDocumento(String nombre) {
        logger.log(Level.INFO, "Busqueda de archivo por nombre " + nombre);
        AppAgendadocummentosEntity appAgendadocummentosEntity = iAgendaDocumentosRepository.findByNombrearchivo(nombre);
        logger.log(Level.INFO, "Resultado " + appAgendadocummentosEntity.toString());
        return appAgendadocummentosEntity.getNombrearchivo();
    }

    public List<AppFichadocumentosEntity> getListDocumentFicha(long id) {
        logger.log(Level.INFO, "Busqueda de archivo por id ficha documentos" + id);
        List<AppFichadocumentosEntity> appFichadocumentosEntityList = docStorageRepo.findByIdFichamedica(id);
        logger.log(Level.INFO, "Resultado " + appFichadocumentosEntityList.toString());
        return appFichadocumentosEntityList;
    }


    public List<AppAgendadocummentosEntity> getListAgendaDocumentos(long id) {
        logger.log(Level.INFO, "Busqueda de archivo por id agenda documentos" + id);
        List<AppAgendadocummentosEntity> appAgendadocummentosEntityList = iAgendaDocumentosRepository.findByIdAgendacita(id);
        logger.log(Level.INFO, "Resultado " + appAgendadocummentosEntityList.toString());
        return appAgendadocummentosEntityList;
    }
}
