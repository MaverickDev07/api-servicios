package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;

import java.util.List;

public interface IMedicionBalanzaService extends IBaseService<AppMedicionBalanzaEntity, Long> {
  AppMedicionBalanzaEntity getLastMedicionBalanzaByPaciente(Long id) throws Exception;
  List<AppMedicionBalanzaEntity> getMedicionBalanzaByPacienteAndFecha(Long id, String fecha) throws Exception;
}
