package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppObjetivosEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IObjetivosRepository;
import org.allivia.api.alliviaapi.services.IObjetivosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivosServiceImpl extends BaseServiceImpl<AppObjetivosEntity, Long> implements IObjetivosService {
    @Autowired
    private IObjetivosRepository iObjetivosRepository;

    public ObjetivosServiceImpl(IBaseRepository<AppObjetivosEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
