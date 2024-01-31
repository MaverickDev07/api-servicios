package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppAgendadocummentosEntity;
import org.allivia.api.alliviaapi.entities.AppDoctorafiliacionesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IAgendaDocumentosRepository extends CrudRepository<AppAgendadocummentosEntity,Long> {

    List<AppAgendadocummentosEntity> findByIdAgendacita (long id);

    AppAgendadocummentosEntity findByNombrearchivo(String nombre);
}
