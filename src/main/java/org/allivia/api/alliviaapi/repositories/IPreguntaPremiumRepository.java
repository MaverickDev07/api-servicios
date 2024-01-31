package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppEntidadMedicaEntity;
import org.allivia.api.alliviaapi.entities.AppPreguntaPremiumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPreguntaPremiumRepository extends IBaseRepository<AppPreguntaPremiumEntity, Long> {
    @Query(value = "select * from app_pregunta_premium pp where pp.id_sintoma=:idSintoma", nativeQuery = true)
    List<AppPreguntaPremiumEntity> findByPregunta(@Param("idSintoma") Long idSintoma);
}
