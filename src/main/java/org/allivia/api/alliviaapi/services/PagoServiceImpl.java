package org.allivia.api.alliviaapi.services;


import org.allivia.api.alliviaapi.entities.AppPagoEntity;
import org.allivia.api.alliviaapi.repositories.IPagoRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PagoServiceImpl implements IPagoService{
    public static final Logger logger = LogManager.getLogger(PagoServiceImpl.class);
    @Autowired
    private IPagoRepository iPagoRepository;



    public Object save(AppPagoEntity appPagoEntity) {

        logger.log(Level.INFO, "Guardado de pago " + appPagoEntity.toString());
        return iPagoRepository.save(appPagoEntity);
    }



    public Object update(String transaccion) {
        AppPagoEntity pagoEntity = iPagoRepository.findByTransaccion(transaccion);
        pagoEntity.setEstado("Completado");
        logger.log(Level.INFO, "Actualizacion de estado del pago " + pagoEntity.toString());
        return iPagoRepository.save(pagoEntity);
    }


}
