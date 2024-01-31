package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppDetalleNotificacionEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.IDetalleNotificacionRepository;
import org.allivia.api.alliviaapi.services.IDetalleNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleNotificacionServiceImpl extends BaseServiceImpl<AppDetalleNotificacionEntity, Long> implements IDetalleNotificacionService {
    @Autowired
    private IDetalleNotificacionRepository iDetalleNotificacionRepository;

    public DetalleNotificacionServiceImpl(IBaseRepository<AppDetalleNotificacionEntity, Long> baseRepository) {
        super(baseRepository);
    }
}
