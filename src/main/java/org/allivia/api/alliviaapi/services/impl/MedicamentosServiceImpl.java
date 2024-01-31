package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMedicamentosEntity;
import org.allivia.api.alliviaapi.repositories.IBaseSimpleRepository;
import org.allivia.api.alliviaapi.repositories.IMedicamentosRepository;
import org.allivia.api.alliviaapi.services.IMedicamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentosServiceImpl extends BaseSimpleServiceImpl<AppMedicamentosEntity, Integer> implements IMedicamentosService {
    @Autowired
    private IMedicamentosRepository iMedicamentosRepository;

    public MedicamentosServiceImpl(IBaseSimpleRepository<AppMedicamentosEntity, Integer> baseRepository) { super(baseRepository); }
}
