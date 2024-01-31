package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IMedicionBalanzaRepository;
import org.allivia.api.alliviaapi.services.IMedicionBalanzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicionBalanzaServiceImpl extends BaseServiceImpl<AppMedicionBalanzaEntity, Long> implements IMedicionBalanzaService {
    @Autowired
    private IMedicionBalanzaRepository iMedicionBalanzaRepository;

    public MedicionBalanzaServiceImpl(IBaseRepository<AppMedicionBalanzaEntity, Long> baseRepository) { super(baseRepository); }

    @Override
    public AppMedicionBalanzaEntity getLastMedicionBalanzaByPaciente(Long id) throws Exception {
        try {
            //AppMedicionBalanzaEntity mb = new AppMedicionBalanzaEntity();
            //mb.setId(id);
            //return mb;
            Optional<AppMedicionBalanzaEntity> optional = iMedicionBalanzaRepository.findLastMedicionBalanzaByPaciente(id);
            return optional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<AppMedicionBalanzaEntity> getMedicionBalanzaByPacienteAndFecha(Long id, String fecha) throws Exception {
        try {
            List<AppMedicionBalanzaEntity> list = iMedicionBalanzaRepository.listMedicionBalanzaByPacienteAndFecha(id, fecha);
            return list;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
