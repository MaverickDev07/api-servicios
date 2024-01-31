package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class FichaMedicaServiceImpl implements IFichaMedicaService{
    public static final Logger logger = LogManager.getLogger(FichaMedicaServiceImpl.class);
    @Autowired
    private IFichaMedicaRepository iFichaMedicaRepository;

    @Autowired
    private IDiagnosticoRepository iDiagnosticoRepository;

    @Autowired
    private IFMedicamentosRepository IFMedicamentosRepository;

    @Autowired
    private ILaboratoriosRepository iLaboratoriosRepository;

    @Autowired
    private IProgramaRepository iProgramaRepository;

    @Autowired
    private IProgramaFaseRepository iProgramaFaseRepository;

    @Autowired
    private IExamenesRepository iExamenesRepository;

    @Autowired
    private IExamenRepository iExamenRepository;

    @Autowired
    private ILaboratorioRepository iLaboratorioRepository;

    @Autowired
    private ICompartirArchivosRepository iCompartirArchivosRepository;



    public Object saveFichaMedica(AppFichamedicaEntity appFichamedicaEntity) {
        logger.log(Level.INFO, "Guardado" + appFichamedicaEntity.toString());
        return iFichaMedicaRepository.save(appFichamedicaEntity);
    }

    public Object updateFichaMedica(AppFichamedicaEntity appFichamedicaEntity) {
        AppFichamedicaEntity a = iFichaMedicaRepository.findById(appFichamedicaEntity.getId()).get();
        a.setIdProgramafase(appFichamedicaEntity.getIdProgramafase());
        logger.log(Level.INFO, "Actualizado" + appFichamedicaEntity.toString());
        return iFichaMedicaRepository.save(a);
    }

    public Object saveDiagnostico(AppFichadiagnosticoEntity fichadiagnosticoEntity) {
        logger.log(Level.INFO, "Guardado" + fichadiagnosticoEntity.toString());
        return iDiagnosticoRepository.save(fichadiagnosticoEntity);
    }

    public Object saveMedicamentos(AppFichamedicamentosEntity fichamedicamentosEntity) {
        logger.log(Level.INFO, "Guardado" + fichamedicamentosEntity.toString());
        return IFMedicamentosRepository.save(fichamedicamentosEntity);
    }

    public Object saveLaboratorios(AppFichalaboratoriosEntity fichalaboratoriosEntity) {
        logger.log(Level.INFO, "Guardado" + fichalaboratoriosEntity.toString());
        return iLaboratoriosRepository.save(fichalaboratoriosEntity);
    }

    public Object saveExamenes(AppFichaexamenesEntity fichaexamenesEntity) {
        logger.log(Level.INFO, "Guardado" + fichaexamenesEntity.toString());
        return iExamenesRepository.save(fichaexamenesEntity);
    }

    public Object saveArchivos(AppFichadocumentosEntity fichadocumentosEntity) {
        logger.log(Level.INFO, "Guardado" + fichadocumentosEntity.toString());
        return iCompartirArchivosRepository.save(fichadocumentosEntity);
    }

    public Object getPrograma(){
        List<AppProgramaEntity> appProgramaEntityList = iProgramaRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "Listado de programas" + appProgramaEntityList.toString());
        return appProgramaEntityList;
    }

    public Object findExamenNombre(String descripcion){
        List<AppExamenesEntity> appExamenesEntityList;
        if(descripcion != null && !descripcion.isEmpty()){
            appExamenesEntityList = iExamenRepository.findByDescripcionContainingIgnoreCaseAndEliminadoFalse(descripcion);
            logger.log(Level.INFO, "Listado de examenes filtrado por la siguiente descripcion "+ descripcion + ", "+ appExamenesEntityList.toString());
        }else{
            appExamenesEntityList = (List<AppExamenesEntity>) iExamenRepository.findAll();
            logger.log(Level.INFO, "Listado de examenes, "+ appExamenesEntityList.toString());

        }
        return appExamenesEntityList;
    }

    public Object findLaboratorioNombre(String descripcion){
        List<AppLaboratoriosEntity> appLaboratoriosEntityList = new ArrayList<>();
        if(descripcion != null && !descripcion.isEmpty()){
            appLaboratoriosEntityList = iLaboratorioRepository.findByDescripcionContainingIgnoreCaseAndEliminadoFalse(descripcion);
            logger.log(Level.INFO, "Listado de laboratorios filtrado por la siguiente descripcion "+ descripcion + ", "+ appLaboratoriosEntityList.toString());
        }else{
            appLaboratoriosEntityList= (List<AppLaboratoriosEntity>) iLaboratorioRepository.findAll();
            logger.log(Level.INFO, "Listado de laboratorios, "+ appLaboratoriosEntityList.toString());

        }
        return appLaboratoriosEntityList;
    }


    public Object getProgramaFase(long idPrograma){
        logger.log(Level.INFO, "Listado de fases por el idPrograma" + idPrograma);

        List<AppProgramafaseEntity> appProgramafaseEntityList = iProgramaFaseRepository.findByIdProgramaAndEliminadoFalse(idPrograma);

        logger.log(Level.INFO, "Resultado " + appProgramafaseEntityList.toString());
        return appProgramafaseEntityList;
    }






}
