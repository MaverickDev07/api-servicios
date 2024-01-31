package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppFacturaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacturaRepository extends IBaseRepository<AppFacturaEntity, Long> {
}
