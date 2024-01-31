package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppFichaDiagnosticoEnfermedadesEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IFichaDiagnosticoEnfermedadesRepository;
import org.allivia.api.alliviaapi.services.IFichaDiagnosticoEnfermedadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FichaDiagnosticoEnfermedadesServiceImpl  extends BaseServiceImpl<AppFichaDiagnosticoEnfermedadesEntity, Long> implements IFichaDiagnosticoEnfermedadesService {
    @Autowired
    private IFichaDiagnosticoEnfermedadesRepository iFichaDiagnosticoEnfermedadesRepository;

    public FichaDiagnosticoEnfermedadesServiceImpl(IBaseRepository<AppFichaDiagnosticoEnfermedadesEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
