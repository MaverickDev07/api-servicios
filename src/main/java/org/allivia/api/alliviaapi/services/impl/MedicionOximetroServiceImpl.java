package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMedicionOximetroEntity;
import org.allivia.api.alliviaapi.entities.AppMedicionOximetroEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IMedicionOximetroRepository;
import org.allivia.api.alliviaapi.services.IMedicionOximetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicionOximetroServiceImpl extends BaseServiceImpl<AppMedicionOximetroEntity, Long> implements IMedicionOximetroService {
  @Autowired
  private IMedicionOximetroRepository iMedicionOximetroRepository;

  public MedicionOximetroServiceImpl(IBaseRepository<AppMedicionOximetroEntity, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public AppMedicionOximetroEntity getLastMedicionOximetroByPaciente(Long id) throws Exception {
    try {
      Optional<AppMedicionOximetroEntity> optional = iMedicionOximetroRepository.findLastMedicionOximetroByPaciente(id);
      return optional.get();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public List<AppMedicionOximetroEntity> getMedicionOximetroByPacienteAndFecha(Long id, String fecha) throws Exception {
    try {
      List<AppMedicionOximetroEntity> list = iMedicionOximetroRepository.listMedicionOximetroByPacienteAndFecha(id, fecha);
      return list;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}

