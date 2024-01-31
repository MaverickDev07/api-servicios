package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMetodospagoEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMetodosPagoRepository extends CrudRepository<AppMetodospagoEntity, Long> {
    void deleteById(long idMetodosPago);

    List<AppMetodospagoEntity> findAllByIdPaciente(long idPaciente);

    @Query(value = "select am.* from app_metodospago am where am.predeterminado = :predeterminado and am.id_paciente = :idPaciente", nativeQuery = true)
    AppMetodospagoEntity findPredeterminado(@Param("predeterminado") boolean predeterminado, @Param("idPaciente") long idPaciente);

    AppMetodospagoEntity findByNumeroTarjeta(String numeroTarjeta);
}
