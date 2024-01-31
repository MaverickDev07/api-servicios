package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMedicamentosEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicamentosRepository extends IBaseSimpleRepository<AppMedicamentosEntity, Integer> {
}
