package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppCatalogoEnfermedadesEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ICatalogoEnfermedadesRepository;
import org.allivia.api.alliviaapi.services.ICatalogoEnfermedadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoEnfermedadesServiceImpl extends BaseServiceImpl<AppCatalogoEnfermedadesEntity, Long> implements ICatalogoEnfermedadesService {
    @Autowired
    private ICatalogoEnfermedadesRepository iCatalogoEnfermedadesRepository;

    public CatalogoEnfermedadesServiceImpl(IBaseRepository<AppCatalogoEnfermedadesEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
