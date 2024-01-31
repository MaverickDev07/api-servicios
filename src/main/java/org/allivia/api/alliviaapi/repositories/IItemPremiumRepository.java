package org.allivia.api.alliviaapi.repositories;

import org.allivia.api.alliviaapi.entities.AppItemPremiumEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemPremiumRepository extends IBaseRepository<AppItemPremiumEntity, Long> {
    @Query(value = "SELECT * from app_item_premium ORDER BY id ASC", nativeQuery = true)
    List<AppItemPremiumEntity> listItemPremiumOrder();
}
