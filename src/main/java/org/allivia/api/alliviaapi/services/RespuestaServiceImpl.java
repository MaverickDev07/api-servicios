package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppDiagnosticoEntity;
import org.allivia.api.alliviaapi.entities.AppRespuestaEntity;
import org.allivia.api.alliviaapi.repositories.IRespuestaRepository;
import org.allivia.api.alliviaapi.repositories.IVerificadorDiagnosticoRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RespuestaServiceImpl implements IRespuestaService {
    public static final Logger logger = LogManager.getLogger(RespuestaServiceImpl.class);
    @Autowired
    private IRespuestaRepository iRespuestaRepository;

    @Autowired
    private IVerificadorDiagnosticoRepository iVerificadorDiagnosticoRepository;

    public Object findByRepuesta(long id) {
        logger.log(Level.INFO, "Obtener la respuesta con el id " + id);
        List<AppRespuestaEntity> appRespuestaEntity = iRespuestaRepository.findByPreguntaId(id);
        AppRespuestaEntity resp = null;
        List<AppRespuestaEntity> respuestas = new ArrayList<AppRespuestaEntity>();
        List<AppRespuestaEntity> respuestasEnlace = new ArrayList<AppRespuestaEntity>();
        for (AppRespuestaEntity row: appRespuestaEntity) {
            if ( row.getPreguntaIdSiguiente() != null )
                respuestasEnlace.add(row);
            else
                respuestas.add(row);
        }
        for (AppRespuestaEntity row: respuestasEnlace)
            respuestas.add(row);

        logger.log(Level.INFO, "Resultado " + respuestas.toString());
        return respuestas;
    }

    public Object findByDiagnostico(String codigo) {
        logger.log(Level.INFO, "Obtener el diagnostico mediante el codigo " + codigo);
        AppDiagnosticoEntity appDiagnosticoEntity = iVerificadorDiagnosticoRepository.findByCodigo(codigo);
        logger.log(Level.INFO, "Resultado " + appDiagnosticoEntity.toString());
        return appDiagnosticoEntity;
    }


}
