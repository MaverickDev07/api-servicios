package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppObjetivosSeleccionadosEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IObjetivosSeleccionadosRepository;
import org.allivia.api.alliviaapi.services.IObjetivosSeleccionadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivosSeleccionadosServiceImpl extends BaseServiceImpl<AppObjetivosSeleccionadosEntity, Long> implements IObjetivosSeleccionadosService {
    @Autowired
    private IObjetivosSeleccionadosRepository iObjetivosSeleccionadosRepository;

    public ObjetivosSeleccionadosServiceImpl(IBaseRepository<AppObjetivosSeleccionadosEntity, Long> baseRepository) { super(baseRepository); }

    @Override
    public AppObjetivosSeleccionadosEntity findObjetivosSeleccionadosByUsuario(Long usuarioId) throws Exception {
        try {
            Optional<AppObjetivosSeleccionadosEntity> objetivosSeleccionados = iObjetivosSeleccionadosRepository.findAllByIdUsuario(usuarioId);
            return objetivosSeleccionados.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*@Override
    public Page<AppObjetivosSeleccionadosEntity> findObjetivosSeleccionadosByUsuario(Long usuarioId, Pageable pageable) throws Exception {
        try {
            Page<AppObjetivosSeleccionadosEntity> objetivosSeleccionados = iObjetivosSeleccionadosRepository.findAllByIdUsuario(usuarioId, pageable);
            return objetivosSeleccionados;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }*/
}
