package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppSOAPEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ISOAPRepository extends IBaseRepository<AppSOAPEntity, Long> {
}
