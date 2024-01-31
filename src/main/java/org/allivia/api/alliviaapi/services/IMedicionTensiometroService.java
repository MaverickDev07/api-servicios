package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppMedicionTensiometroEntity;

import java.util.List;

public interface IMedicionTensiometroService extends IBaseService<AppMedicionTensiometroEntity, Long> {
  AppMedicionTensiometroEntity getLastMedicionTensiometroByPaciente(Long id) throws Exception;

  List<AppMedicionTensiometroEntity> getMedicionTensiometroByPacienteAndFecha(Long id, String fecha) throws Exception;
}
