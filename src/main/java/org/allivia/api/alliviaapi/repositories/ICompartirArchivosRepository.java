package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppFichadocumentosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICompartirArchivosRepository extends CrudRepository<AppFichadocumentosEntity,Long> {

    List<AppFichadocumentosEntity> findByIdFichamedica(long idFichaMedica);

    AppFichadocumentosEntity findByNombrearchivo(String nombreArchivo);

}
