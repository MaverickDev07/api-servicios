package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppNotificacionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INotificacionService extends IBaseService<AppNotificacionEntity, Long> {
    List<AppNotificacionEntity> findAllByIdReceptor(Long idReceptor) throws Exception;

    Page<AppNotificacionEntity> findAllByIdReceptor(Long idReceptor, Pageable pageable) throws Exception;

    AppNotificacionEntity saveByAgendaCita(AppAgendacitaEntity agendacita, String para, String estado) throws Exception;

    int updateEstadoNotificacion(Long id, String estado) throws Exception;
}
