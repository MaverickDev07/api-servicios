package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppMedicionGlucometroEntity;

import java.util.List;

public interface IMedicionGlucometroService extends IBaseService<AppMedicionGlucometroEntity, Long> {
  AppMedicionGlucometroEntity getLastMedicionGlucometroByPaciente(Long id) throws Exception;

  List<AppMedicionGlucometroEntity> getMedicionGlucometroByPacienteAndFecha(Long id, String fecha) throws Exception;
}
