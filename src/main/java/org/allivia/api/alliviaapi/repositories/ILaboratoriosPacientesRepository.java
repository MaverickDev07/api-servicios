package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.LaboratoriospacienteEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILaboratoriosPacientesRepository extends CrudRepository<LaboratoriospacienteEntity,Long> {

    List<LaboratoriospacienteEntity> findByIdPaciente(long idPaciente);

    @Query(value = "SELECT lpp.* FROM laboratoriospacientepremium lpp WHERE lpp.id_paciente=:idPaciente", nativeQuery = true)
    List<LaboratoriospacienteEntity> laboratoriospacientepremiumFindByIdPaciente(@Param("idPaciente") Long idPaciente);

}
