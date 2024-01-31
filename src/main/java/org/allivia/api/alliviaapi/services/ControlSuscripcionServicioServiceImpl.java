package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppControlsuscripcionservicioEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.entities.AppSuscripcionservicioEntity;
import org.allivia.api.alliviaapi.repositories.IControlSuscripcionServicioRepository;
import org.allivia.api.alliviaapi.repositories.ISuscripcionRepository;
import org.allivia.api.alliviaapi.repositories.ISuscripcionServicioRepository;
import org.allivia.api.alliviaapi.repositories.ISuscripcionPacienteRepository;
import org.allivia.api.alliviaapi.entities.AppSuscripcionpacienteEntity;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class ControlSuscripcionServicioServiceImpl implements IControlSuscripcionServicioService{
    public static final Logger logger = LogManager.getLogger(ControlSuscripcionServicioServiceImpl.class);
    @Autowired
    private IControlSuscripcionServicioRepository iControlSuscripcionServicioRepository;

    @Autowired
    private ISuscripcionServicioRepository iSuscripcionServicioRepository;

    @Autowired
    private ISuscripcionPacienteRepository iSuscripcionPacienteRepository;

    @Autowired
    private ISuscripcionRepository iSuscripcionRepository;

    @Autowired
    private IUserService userService;


    public Object save(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity) {
        logger.log(Level.INFO, "Guardado" + appControlsuscripcionservicioEntity.toString());
        return iControlSuscripcionServicioRepository.save(appControlsuscripcionservicioEntity);
    }

//    public Object update(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity) {
//        AppControlsuscripcionservicioEntity app = iControlSuscripcionServicioRepository.find(appControlsuscripcionservicioEntity.getIdPaciente(),appControlsuscripcionservicioEntity.getIdServicio());
//        app.setCantidad(appControlsuscripcionservicioEntity.getCantidad());
//        logger.log(Level.INFO, "Actualizado" + app.toString());
//        return iControlSuscripcionServicioRepository.save(app);
//    }


    public AppControlsuscripcionservicioEntity updateControlSuscripcionServicio(AppControlsuscripcionservicioEntity appControlsuscripcionservicioEntity){

        logger.log(Level.INFO, "Actualizar " + appControlsuscripcionservicioEntity.toString());
        AppControlsuscripcionservicioEntity update = iControlSuscripcionServicioRepository.findByIdServicioAndIdPaciente(appControlsuscripcionservicioEntity.getIdServicio(),appControlsuscripcionservicioEntity.getIdPaciente());
        if(update.getCantidad() < 0){
            update.setCantidad(appControlsuscripcionservicioEntity.getCantidad() - update.getCantidad());
        }else{
            update.setCantidad(appControlsuscripcionservicioEntity.getCantidad() + update.getCantidad());
        }

        logger.log(Level.INFO, "Resultado " + update.toString());
        return iControlSuscripcionServicioRepository.save(update);
    }

    public AppControlsuscripcionservicioEntity updateControlSuscripcionServicioTipo(long idPaciente, int cantidad, String codigo){

//        logger.log(Level.INFO, "Actualizar " + appControlsuscripcionservicioEntity.toString());
        AppControlsuscripcionservicioEntity update = iControlSuscripcionServicioRepository.getControlSuscripcion(codigo,idPaciente);
        update.setCantidad(cantidad + update.getCantidad());

        logger.log(Level.INFO, "Resultado " + update.toString());
        return iControlSuscripcionServicioRepository.save(update);
    }

    public AppControlsuscripcionservicioEntity getControlSuscripcionServicioTipo(String tipo, long idPaciente) throws ParseException {
        // Revisar: idPaciente en si lo que llega es usuarioId
        // Long pacienteId = userService.findIdPacienteByIdUsuario(idPaciente);
        logger.log(Level.INFO, "Obtener el registro Control suscripcion servicio por tipo " + tipo + ", y idPaciente= " +idPaciente);
        AppControlsuscripcionservicioEntity get = iControlSuscripcionServicioRepository.getControlSuscripcion(tipo,idPaciente);

        AppSuscripcionpacienteEntity appSuscripcionpacienteEntity = iSuscripcionPacienteRepository.findByIdPacienteSuscripcion(idPaciente);

        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        String fecha_actual = formateador.format(ahora);
        String fecha_vigencia = formateador.format(appSuscripcionpacienteEntity.getVigencia());
        Date actual = formateador.parse(fecha_actual);
        Date vigencia = formateador.parse(fecha_vigencia);
        System.out.println(vigencia);
        System.out.println(actual);
        if ( vigencia.after(actual) || vigencia.equals(actual) )
            get.setIdSuscripcion(appSuscripcionpacienteEntity.getIdSuscripcion());
        else
            get.setIdSuscripcion(Long.valueOf(1));

        List<AppSuscripcionservicioEntity> appSuscripcionservicioEntityList = iSuscripcionServicioRepository.findByIdServicio(get.getIdServicio());
        for(AppSuscripcionservicioEntity row: appSuscripcionservicioEntityList){
            AppSuscripcionEntity appSuscripcionEntity = iSuscripcionRepository.findById(row.getIdSuscripcion()).get();
            row.setSuscripcion(appSuscripcionEntity);
        }
        get.setSuscripcionServicio(appSuscripcionservicioEntityList);



        logger.log(Level.INFO, "Resultado " + get.toString());
        return get;
    }


}
