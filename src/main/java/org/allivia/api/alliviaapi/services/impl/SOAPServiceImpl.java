package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppSOAPEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ISOAPRepository;
import org.allivia.api.alliviaapi.services.ISOAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SOAPServiceImpl extends BaseServiceImpl<AppSOAPEntity, Long> implements ISOAPService {
    @Autowired
    private ISOAPRepository iSOAPRepository;

    public SOAPServiceImpl(IBaseRepository<AppSOAPEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
