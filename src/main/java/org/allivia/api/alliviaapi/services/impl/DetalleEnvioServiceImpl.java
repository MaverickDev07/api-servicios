package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppDetalleEnvioEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IDetalleEnvioRepository;
import org.allivia.api.alliviaapi.services.IDetalleEnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleEnvioServiceImpl extends BaseServiceImpl<AppDetalleEnvioEntity, Long> implements IDetalleEnvioService {
    @Autowired
    private IDetalleEnvioRepository iDetalleEnvioRepository;

    public DetalleEnvioServiceImpl(IBaseRepository<AppDetalleEnvioEntity, Long> baseRepository) { super(baseRepository); }
}
