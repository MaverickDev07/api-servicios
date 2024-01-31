package org.allivia.api.alliviaapi.repositories;




import org.allivia.api.alliviaapi.entities.AppSuscripcionservicioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ISuscripcionServicioRepository extends CrudRepository<AppSuscripcionservicioEntity,Long> {

    @Query(value = "SELECT asu.* FROM app_suscripcionservicio asu INNER JOIN app_suscripcion ap on asu.id_suscripcion = ap.id WHERE ap.codigo = :codigo",nativeQuery = true)
    List<AppSuscripcionservicioEntity> findByCodigo(@Param("codigo") String codigo);
    List<AppSuscripcionservicioEntity> findByIdSuscripcion(long idSuscripcion);

    @Query(value = "SELECT asu.* FROM app_suscripcionservicio asu INNER JOIN app_suscripcion ap on asu.id_suscripcion = ap.id WHERE asu.id_servicio = :idServicio AND ap.eliminado = FALSE",nativeQuery = true)
    List<AppSuscripcionservicioEntity> findByIdServicio(@Param("idServicio") long idServicio);
    AppSuscripcionservicioEntity findByIdServicioAndIdSuscripcion(long idServicio, long idSuscripcion);

}
