package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppDiagnosticoEntity;
import org.allivia.api.alliviaapi.entities.AppFichadiagnosticoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IVerificadorDiagnosticoRepository extends CrudRepository<AppDiagnosticoEntity,Long> {

    AppDiagnosticoEntity findByCodigo(String codigo);

}
