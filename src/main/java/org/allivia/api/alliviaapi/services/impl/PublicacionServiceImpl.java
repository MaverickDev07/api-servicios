package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppPublicacionEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IPublicacionRepository;
import org.allivia.api.alliviaapi.services.IPublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicacionServiceImpl extends BaseServiceImpl<AppPublicacionEntity, Long> implements IPublicacionService {
    @Autowired
    private IPublicacionRepository iPublicacionRepository;

    public PublicacionServiceImpl(IBaseRepository<AppPublicacionEntity, Long> baseRepository) { super(baseRepository); }
}
