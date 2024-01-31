package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppMailEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IMailRepository extends IBaseRepository<AppMailEntity, Long> {
}
