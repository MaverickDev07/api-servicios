package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppFichamedicaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IFichaMedicaRepository extends CrudRepository<AppFichamedicaEntity,Long> {

    String queryPrimary = "select * from app_view_usuario avu inner join app_paciente ap on ";
    String querySecond = "inner join app_agendacita aa on ap.id = aa.id_paciente " + 
                         "inner join app_fichamedica af on aa.id = af.id_agendacita " +
                         "inner join app_fichamedicamentos af2 on af.id = af2.id_fichamedica ";

    AppFichamedicaEntity findByIdAgendacita(long idAgendaCita);

    // @Query(value = queryPrimary + "avu.usuario_id = :id_usuario and ap.usuario_id = :id_usuario " + querySecond, nativeQuery = true)
    // @Query(value = queryPrimary + "avu.usuario_id = :id_usuario and ap.id = :id_usuario " + querySecond, nativeQuery = true)
    // List<AppFichamedicaEntity> findByidProgramaFase(@Param("id_usuario") int id_usuario);
}
