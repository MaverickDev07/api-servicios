package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppItemPremiumEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IItemPremiumRepository;
import org.allivia.api.alliviaapi.services.IItemPremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemPremiumServiceImpl extends BaseServiceImpl<AppItemPremiumEntity, Long> implements IItemPremiumService {
    @Autowired
    private IItemPremiumRepository iItemPremiumRepository;

    public ItemPremiumServiceImpl(IBaseRepository<AppItemPremiumEntity, Long> baseRepository) { super(baseRepository); }
}
