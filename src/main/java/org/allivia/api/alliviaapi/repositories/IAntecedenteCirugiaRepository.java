package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppAntecedenteCirugiaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IAntecedenteCirugiaRepository extends CrudRepository<AppAntecedenteCirugiaEntity,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from app_antecedente_cirugia where id_antecedente_medico = :idAntecedente", nativeQuery = true)
    void deleteByIdAntecedente(@Param("idAntecedente") long idAntecedente);
}
