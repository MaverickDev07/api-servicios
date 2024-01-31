package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMedicionTensiometroEntity;
import org.allivia.api.alliviaapi.entities.AppMedicionTensiometroEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IMedicionTensiometroRepository;
import org.allivia.api.alliviaapi.services.IMedicionTensiometroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicionTensiometroServiceImpl extends BaseServiceImpl<AppMedicionTensiometroEntity, Long> implements IMedicionTensiometroService {
  @Autowired
  private IMedicionTensiometroRepository iMedicionTensiometroRepository;

  public MedicionTensiometroServiceImpl(IBaseRepository<AppMedicionTensiometroEntity, Long> baseRepository) {
    super(baseRepository);
  }

  @Override
  public AppMedicionTensiometroEntity getLastMedicionTensiometroByPaciente(Long id) throws Exception {
    try {
      Optional<AppMedicionTensiometroEntity> optional = iMedicionTensiometroRepository.findLastMedicionTensiometroByPaciente(id);
      return optional.get();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public List<AppMedicionTensiometroEntity> getMedicionTensiometroByPacienteAndFecha(Long id, String fecha) throws Exception {
    try {
      List<AppMedicionTensiometroEntity> list = iMedicionTensiometroRepository.listMedicionTensiometroByPacienteAndFecha(id, fecha);
      return list;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}

