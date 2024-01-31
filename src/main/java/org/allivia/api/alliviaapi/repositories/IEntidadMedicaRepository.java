package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppEntidadMedicaEntity;
import org.allivia.api.alliviaapi.entities.AppVacunaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEntidadMedicaRepository extends IBaseRepository<AppEntidadMedicaEntity, Long> {
    @Query(value = "select * from app_entidad_medica em where em.tipo_entidad ilike %:tipo%", nativeQuery = true)
    List<AppEntidadMedicaEntity> listEntidadMedicaByTipoEntidad(@Param("tipo") String tipo);
}
