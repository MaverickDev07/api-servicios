package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppImgPublicacionEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IImgPublicacionRepository;
import org.allivia.api.alliviaapi.services.IImgPublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgPublicacionServiceImpl extends BaseServiceImpl<AppImgPublicacionEntity, Long> implements IImgPublicacionService {
    @Autowired
    private IImgPublicacionRepository iImgPublicacionRepository;

    public ImgPublicacionServiceImpl(IBaseRepository<AppImgPublicacionEntity, Long> baseRepository) { super(baseRepository); }
}
