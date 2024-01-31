package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppAntecedenteMedicoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAntecedenteMedicoRepository extends CrudRepository<AppAntecedenteMedicoEntity,Long> {
    @Query(value = "select * from app_antecedente_medico aam where aam.id_paciente = :idPaciente",nativeQuery = true)
    AppAntecedenteMedicoEntity findByIdPaciente(@Param("idPaciente") long idPaciente);

    //AppAntecedenteMedicoEntity update


}
