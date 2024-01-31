package org.allivia.api.alliviaapi.services.impl;


import org.allivia.api.alliviaapi.entities.AppLaboratorioEmpresaEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ILaboratorioEmpresaRepository;
import org.allivia.api.alliviaapi.services.ILaboratorioEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaboratorioEmpresaServiceImpl extends BaseServiceImpl<AppLaboratorioEmpresaEntity, Long> implements ILaboratorioEmpresaService {
    @Autowired
    private ILaboratorioEmpresaRepository iLaboratorioEmpresaRepository;

    public LaboratorioEmpresaServiceImpl(IBaseRepository<AppLaboratorioEmpresaEntity, Long> baseRepository) { super(baseRepository); }
}
