package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppControlconsultaservicioEntity;
import org.allivia.api.alliviaapi.entities.AppDoctoreducacionEntity;
import org.allivia.api.alliviaapi.repositories.IControlConsultaServicioRepository;
import org.allivia.api.alliviaapi.repositories.IControlSuscripcionServicioRepository;
import org.allivia.api.alliviaapi.repositories.IEducacionDoctorRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ControlConsultaServicioServiceImpl implements IControlConsultaServicioService{

    public static final Logger logger = LogManager.getLogger(ControlConsultaServicioServiceImpl.class);

    @Autowired
    private IControlConsultaServicioRepository iControlConsultaServicioRepository;



    public Object save(AppControlconsultaservicioEntity appControlconsultaservicioEntity) {
        logger.log(Level.INFO, "Guardado" + appControlconsultaservicioEntity.toString());
        return iControlConsultaServicioRepository.save(appControlconsultaservicioEntity);
    }


    public Object update(AppControlconsultaservicioEntity appControlconsultaservicioEntity) {
        AppControlconsultaservicioEntity app = iControlConsultaServicioRepository.findById(appControlconsultaservicioEntity.getId()).get();
        app.setCantidad(appControlconsultaservicioEntity.getCantidad());
        if(appControlconsultaservicioEntity.getFecha() != null){
            app.setFecha(appControlconsultaservicioEntity.getFecha());
        }

        logger.log(Level.INFO, "Actualizado" + appControlconsultaservicioEntity.toString());
        return iControlConsultaServicioRepository.save(app);
    }


}
