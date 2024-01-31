package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.entities.LaboratoriospacienteEntity;

import java.util.List;

public interface IPacienteService {
    Object savePaciente(AppUsuarioEntity userEntity);
    Object findAll(long idDoctor, String fechaCalendar);
    List<AppPacienteEntity> findByNombreContains(String nombre);
    AppPacienteEntity update(AppUsuarioEntity userEntity);
    List<LaboratoriospacienteEntity> getListLaboratoriosPaciente(long idPaciente);
    List<AppAgendacitaEntity> getListRecetasPaciente(long idPaciente, String nombreDoctor);
    List<AppPacienteEntity> getLastUsersPaciente();
}
