package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMedicionGlucometroEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IMedicionGlucometroRepository;
import org.allivia.api.alliviaapi.services.IMedicionGlucometroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicionGlucometroServiceImpl extends BaseServiceImpl<AppMedicionGlucometroEntity, Long> implements IMedicionGlucometroService {
  @Autowired
  private IMedicionGlucometroRepository iMedicionGlucometroRepository;

  public MedicionGlucometroServiceImpl(IBaseRepository<AppMedicionGlucometroEntity, Long> baseRepository) { super(baseRepository); }
  
  @Override
  public AppMedicionGlucometroEntity getLastMedicionGlucometroByPaciente(Long id) throws Exception {
    try {
      Optional<AppMedicionGlucometroEntity> optional = iMedicionGlucometroRepository.findLastMedicionGlucometroByPaciente(id);
      return optional.get();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public List<AppMedicionGlucometroEntity> getMedicionGlucometroByPacienteAndFecha(Long id, String fecha) throws Exception {
    try {
      List<AppMedicionGlucometroEntity> list = iMedicionGlucometroRepository.listMedicionGlucometroByPacienteAndFecha(id, fecha);
      return list;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
