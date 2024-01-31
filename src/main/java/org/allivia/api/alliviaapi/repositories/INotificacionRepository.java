package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppNotificacionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface INotificacionRepository extends IBaseRepository<AppNotificacionEntity, Long>  {
    @Query(value = "SELECT n FROM AppNotificacionEntity n WHERE n.usuarioReceptor.usuarioId = :idReceptor")
    List<AppNotificacionEntity> findAllByIdReceptor(@Param("idReceptor") Long idReceptor);

    @Query(value = "SELECT n FROM AppNotificacionEntity n WHERE n.usuarioReceptor.usuarioId = :idReceptor")
    Page<AppNotificacionEntity> findAllByIdReceptor(@Param("idReceptor") Long idReceptor, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE app_notificacion SET estado=:estado WHERE id=:id",nativeQuery = true)
    int updateEstadoNotificacion(@Param("id") long id, @Param("estado") String estado);
}
