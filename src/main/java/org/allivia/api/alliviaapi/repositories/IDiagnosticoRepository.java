package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppFichadiagnosticoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IDiagnosticoRepository extends CrudRepository<AppFichadiagnosticoEntity,Long> {

    AppFichadiagnosticoEntity findByIdFichamedica(long idFichaMedica);

}
