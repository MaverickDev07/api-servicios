package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppCategoriaPublicacionEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ICategoriaPublicacionRepository;
import org.allivia.api.alliviaapi.services.ICategoriaPublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaPublicacionServiceImpl extends BaseServiceImpl<AppCategoriaPublicacionEntity, Long> implements ICategoriaPublicacionService {
    @Autowired
    private ICategoriaPublicacionRepository iCategoriaPublicacionRepository;

    public CategoriaPublicacionServiceImpl(IBaseRepository<AppCategoriaPublicacionEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
