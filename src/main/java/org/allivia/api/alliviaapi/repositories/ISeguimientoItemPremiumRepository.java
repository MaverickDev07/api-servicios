package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppSeguimientoItemPremiumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeguimientoItemPremiumRepository extends IBaseRepository<AppSeguimientoItemPremiumEntity, Long> {
    @Query(value = "SELECT id, creado, modificado, estado, id_item, id_seguimiento FROM app_seguimientoitem_premium asip WHERE asip.id_seguimiento=:idSeguimiento", nativeQuery = true)
    List<AppSeguimientoItemPremiumEntity> getByIdSeguimiento(@Param("idSeguimiento") long idSeguimiento);
}
