package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppPreguntaPremiumEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IPreguntaPremiumRepository;
import org.allivia.api.alliviaapi.services.IPreguntaPremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreguntaPremiumServiceImpl extends BaseServiceImpl<AppPreguntaPremiumEntity, Long> implements IPreguntaPremiumService {
    @Autowired
    private IPreguntaPremiumRepository iPreguntaPremiumRepository;

    public PreguntaPremiumServiceImpl(IBaseRepository<AppPreguntaPremiumEntity, Long> baseRepository) { super(baseRepository); }
}
