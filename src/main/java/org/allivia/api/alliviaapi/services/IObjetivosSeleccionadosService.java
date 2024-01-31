package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppObjetivosSeleccionadosEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IObjetivosSeleccionadosService extends IBaseService<AppObjetivosSeleccionadosEntity, Long> {
    AppObjetivosSeleccionadosEntity findObjetivosSeleccionadosByUsuario(Long usuarioId) throws Exception;
    //Page<AppObjetivosSeleccionadosEntity> findObjetivosSeleccionadosByUsuario(Long usuarioId, Pageable pageable) throws Exception;
}
