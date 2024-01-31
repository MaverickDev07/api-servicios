package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppMedicionOximetroEntity;

import java.util.List;

public interface IMedicionOximetroService extends IBaseService<AppMedicionOximetroEntity, Long> {
  AppMedicionOximetroEntity getLastMedicionOximetroByPaciente(Long id) throws Exception;

  List<AppMedicionOximetroEntity> getMedicionOximetroByPacienteAndFecha(Long id, String fecha) throws Exception;
}

