package org.allivia.api.alliviaapi.services;

import org.allivia.api.alliviaapi.entities.AppLaboratoriosPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppMedicionBalanzaEntity;

public interface ILaboratoriosPacienteService extends IBaseService<AppLaboratoriosPacienteEntity, Long> {
    Boolean ifExamenPaciente(Long id) throws Exception;
    void reclamarPremium(AppLaboratoriosPacienteEntity labPremiumPaciente) throws Exception;
    void reclamarConsultaPremium(Long idPaciente) throws Exception;
}
