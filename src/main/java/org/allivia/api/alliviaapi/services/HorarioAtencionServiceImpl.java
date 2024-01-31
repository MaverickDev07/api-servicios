package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppDiasEntity;
import org.allivia.api.alliviaapi.entities.AppHorarioatencionEntity;
import org.allivia.api.alliviaapi.entities.AppHorasEntity;
import org.allivia.api.alliviaapi.repositories.IAgendaCitaRepository;
import org.allivia.api.alliviaapi.repositories.IDiasRepository;
import org.allivia.api.alliviaapi.repositories.IHoraAtencionRepository;
import org.allivia.api.alliviaapi.repositories.IHorasRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Component
public class HorarioAtencionServiceImpl implements IHorarioAtencionService{
    public static final Logger logger = LogManager.getLogger(HorarioAtencionServiceImpl.class);
    @Autowired
    private IHoraAtencionRepository iHoraAtencionRepository;

    @Autowired
    private IDiasRepository iDiasRepository;

    @Autowired
    private IHorasRepository iHorasRepository;

    @Autowired
    private IAgendaCitaRepository iAgendaCitaRepository;





    public Object findAllHorarios(){

        List<AppHorasEntity> appHorasEntityList = iHorasRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "Listado de horas " + appHorasEntityList.toString());
        return appHorasEntityList;
    }

    public Object findAllDias(){

        List<AppDiasEntity> appDiasEntityList = iDiasRepository.findAllByEliminadoFalse();
        logger.log(Level.INFO, "Listado de dias " + appDiasEntityList.toString());
        return appDiasEntityList;
    }

    public Object save(AppHorarioatencionEntity appHorarioatencionEntity) {
        AppHorarioatencionEntity find =  iHoraAtencionRepository.findByFechaAndIdHorasAndIdDoctor(appHorarioatencionEntity.getFecha(), appHorarioatencionEntity.getIdHoras(), appHorarioatencionEntity.getIdDoctor());
        if(find == null){
            appHorarioatencionEntity.setEstado(true);
            logger.log(Level.INFO, "Guardado" + appHorarioatencionEntity.toString());
            return iHoraAtencionRepository.save(appHorarioatencionEntity);
        }
        return null;

    }

    public Object getHorarioAtencionDoctor(long idDoctor, String fecha) {
        logger.log(Level.INFO, "Horas habilitadas y no habilitadas por el idDoctor" + idDoctor+" de la fecha: "+fecha);
        HashMap<Object,Object> result = new HashMap<>();
        List<AppHorarioatencionEntity> appHorarioatencionEntity = iHoraAtencionRepository.findByFecha(idDoctor, fecha);
        List<AppHorasEntity> horasSeleccionadas = new ArrayList<>();
        List<AppHorasEntity> horasNoSeleccionadas = (List<AppHorasEntity>) iHorasRepository.findAll();
        for (AppHorarioatencionEntity row : appHorarioatencionEntity) {

            AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
            if (appHorasEntity != null) {
                horasSeleccionadas.add(appHorasEntity);
                horasNoSeleccionadas.remove(appHorasEntity);
            }
        }

        result.put("horasSeleccionadas",horasSeleccionadas);
        result.put("horasNoSeleccionadas",horasNoSeleccionadas);


        logger.log(Level.INFO, "Horas habilitadas" + horasSeleccionadas.toString());
        logger.log(Level.INFO, "Horas horasNoHabilitadas" + horasNoSeleccionadas.toString());
        return result;

    }

    public Object updateHoraAtencion(long id, boolean estado){
        AppHorarioatencionEntity horarioatencionEntity = iHoraAtencionRepository.findById(id).get();
        horarioatencionEntity.setEstado(estado);
        logger.log(Level.INFO, "Habilitacion y desabilitacion de hora" + horarioatencionEntity.toString());
        return iHoraAtencionRepository.save(horarioatencionEntity);
    }


    public Object getHorasAtencionDoctor(long idDoctor, String fecha) throws ParseException {
        logger.log(Level.INFO, "Horas habilitadas del idDoctor" + idDoctor+" de la fecha: "+fecha);
        Date fechaSistema = new Date();

        HashMap<Object,Object> result = new HashMap<>();
        List<AppHorarioatencionEntity> appHorarioatencionEntity = iHoraAtencionRepository.findByFecha(idDoctor, fecha);
        List<String> appAgendacitaEntityListDoctor = iAgendaCitaRepository.findHorasAgendadasDoctor(idDoctor);
//        List<AppHorasEntity> horasEntityList = new ArrayList<>();
        List<String> horas = new ArrayList<>();
        for (AppHorarioatencionEntity row : appHorarioatencionEntity) {
            AppHorasEntity appHorasEntity = iHorasRepository.findById(row.getIdHoras()).get();
            String[] hora = appHorasEntity.getDescripcion().split("-");

            String fechaFinal = fecha + " " + hora[0].trim() + ":00";
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fechaHora = dateFormat2.parse(fechaFinal);
            if (!appAgendacitaEntityListDoctor.contains(fechaFinal)  && fechaHora.after(fechaSistema)) {
//                horasEntityList.add(appHorasEntity);
                horas.add(appHorasEntity.getDescripcion());
            }

        }

//        result.put("horasHabilitadas",horasEntityList);
        result.put("horasHabilitadas",horas);
        logger.log(Level.INFO, "Horas habilitadas" + horas.toString());
        return result;

    }

}
