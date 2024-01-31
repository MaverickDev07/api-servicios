package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppEntidadMedicaEntity;
import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IEntidadMedicaRepository;
import org.allivia.api.alliviaapi.services.IEntidadMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntidadMedicaServiceImpl extends BaseServiceImpl<AppEntidadMedicaEntity, Long> implements IEntidadMedicaService {
    @Autowired
    private IEntidadMedicaRepository iEntidadMedicaRepository;

    public EntidadMedicaServiceImpl(IBaseRepository<AppEntidadMedicaEntity, Long> baseRepository) { super(baseRepository); }

    @Override
    public List<AppEntidadMedicaEntity> getEntidadMedicaByTipoEntidad(String tipo) throws Exception {
        try {
            List<AppEntidadMedicaEntity> list = iEntidadMedicaRepository.listEntidadMedicaByTipoEntidad(tipo);
            return list;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
