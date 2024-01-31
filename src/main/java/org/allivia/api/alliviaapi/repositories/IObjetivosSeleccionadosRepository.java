package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppObjetivosSeleccionadosEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IObjetivosSeleccionadosRepository extends IBaseRepository<AppObjetivosSeleccionadosEntity, Long> {
    //@Query(value = "SELECT os.* FROM AppObjetivosSeleccionadosEntity os INNER JOIN AppUsuarioEntity us ON os.usuario.usuarioId = us.usuarioId WHERE us.usuarioId = :usuarioId ORDER BY os.id DESC LIMIT 1")
    @Query(
            value = "SELECT os.* FROM app_objetivos_seleccionados os WHERE os.usuario_id = :usuarioId ORDER BY os.id DESC LIMIT 1",
            nativeQuery = true)
    Optional<AppObjetivosSeleccionadosEntity> findAllByIdUsuario(@Param("usuarioId") Long usuarioId);

    /*@Query(value = "SELECT os FROM AppObjetivosSeleccionadosEntity os INNER JOIN AppUsuarioEntity us ON os.usuario.usuarioId = us.usuarioId WHERE us.usuarioId = :usuarioId")
    Page<AppObjetivosSeleccionadosEntity> findAllByIdUsuario(@Param("usuarioId") Long usuarioId, Pageable pageable);*/
}
