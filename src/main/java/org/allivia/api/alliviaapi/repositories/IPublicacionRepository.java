package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppPublicacionEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicacionRepository extends IBaseRepository<AppPublicacionEntity, Long> {
}
