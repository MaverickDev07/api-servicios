package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppSeguimientoPremiumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISeguimientoPremiumRepository extends IBaseRepository<AppSeguimientoPremiumEntity, Long> {
    @Query(
            value = "SELECT sp.* FROM app_seguimiento_premium sp INNER JOIN app_paciente pc ON sp.id_paciente=pc.id WHERE sp.id_paciente=:id ORDER BY sp.id DESC limit 1",
            nativeQuery = true)
    AppSeguimientoPremiumEntity findSeguimientoPremiumPaciente(@Param("id") Long id);
}
