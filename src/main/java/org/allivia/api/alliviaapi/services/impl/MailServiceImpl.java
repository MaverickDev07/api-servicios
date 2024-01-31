package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppMailEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IMailRepository;
import org.allivia.api.alliviaapi.services.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl extends BaseServiceImpl<AppMailEntity, Long> implements IMailService {
    @Autowired
    private IMailRepository iMailRepository;

    public MailServiceImpl(IBaseRepository<AppMailEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
