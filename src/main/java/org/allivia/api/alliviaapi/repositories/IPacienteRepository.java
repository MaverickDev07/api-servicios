package org.allivia.api.alliviaapi.repositories;



import org.allivia.api.alliviaapi.entities.AppPacienteEntity;

import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IPacienteRepository extends CrudRepository<AppPacienteEntity,Long> {
    @Query(
            value = "SELECT * FROM app_paciente WHERE usuario_id=:id",
            nativeQuery = true)
    AppPacienteEntity findByUsuario(@Param("id") long id);
    AppPacienteEntity findByUsuarioId( long idUsuario);
    @Query(
            value = "select ap.* from app_paciente ap  inner join app_usuario au on ap.usuario_id = au.usuario_id  where   au.nombre ilike  CONCAT('%',:pnombre,'%')",
            nativeQuery = true)
    List<AppPacienteEntity> findByNombreContains(@Param("pnombre") String nombre);

    @Query(
            value = "select pc.* FROM app_paciente pc INNER JOIN app_usuario us on us.usuario_id = pc.usuario_id ORDER BY pc.id DESC LIMIT 5",
            nativeQuery = true)
    List<AppPacienteEntity> findLastUsersPaciente();
}
