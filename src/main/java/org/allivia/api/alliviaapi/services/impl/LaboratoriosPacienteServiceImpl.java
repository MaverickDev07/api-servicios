package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppLaboratoriosPacienteEntity;
import org.allivia.api.alliviaapi.entities.AppSeguimientoItemPremiumEntity;
import org.allivia.api.alliviaapi.entities.AppSeguimientoPremiumEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.ILaboratoriosPacienteRepository;
import org.allivia.api.alliviaapi.repositories.ISeguimientoPremiumRepository;
import org.allivia.api.alliviaapi.services.ILaboratoriosPacienteService;
import org.allivia.api.alliviaapi.services.ISeguimientoItemPremiumService;
import org.allivia.api.alliviaapi.services.SuscripcionServicioServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LaboratoriosPacienteServiceImpl extends BaseServiceImpl<AppLaboratoriosPacienteEntity, Long> implements ILaboratoriosPacienteService {
    public static final Logger logger = LogManager.getLogger(LaboratoriosPacienteServiceImpl.class);
    @Autowired
    private ILaboratoriosPacienteRepository iLaboratoriosPacienteRepository;
    @Autowired
    private ISeguimientoPremiumRepository iSeguimientoPremiumRepository;
    @Autowired
    private ISeguimientoItemPremiumService iSeguimientoItemPremiumService;

    public LaboratoriosPacienteServiceImpl(IBaseRepository<AppLaboratoriosPacienteEntity, Long> baseRepository) { super(baseRepository); }

    public Boolean ifExamenPaciente(Long id) throws Exception {
        return true;
    }
    public void reclamarPremium(AppLaboratoriosPacienteEntity labPremiumPaciente) throws Exception {
        logger.log(Level.INFO, "reclamarPremium: " + labPremiumPaciente.toString());
        AppSeguimientoPremiumEntity resSeguimiento = iSeguimientoPremiumRepository.findSeguimientoPremiumPaciente(labPremiumPaciente.getPaciente().getId());
        if (resSeguimiento != null && !Objects.isNull(resSeguimiento)) {
            for(AppSeguimientoItemPremiumEntity item: resSeguimiento.getItems()) {
                if(item.getItem().getId().equals(labPremiumPaciente.getLaboratorios().get(0).getId()) || item.getItem().getId() == labPremiumPaciente.getLaboratorios().get(0).getId()) {
                    item.setEstado("Reclamado");
                    iSeguimientoItemPremiumService.update(item.getId(), item);
                }
            }
        }
    }
    public void reclamarConsultaPremium(Long idPaciente) throws Exception {
        AppSeguimientoPremiumEntity resSeguimiento = iSeguimientoPremiumRepository.findSeguimientoPremiumPaciente(idPaciente);
        if (resSeguimiento != null && !Objects.isNull(resSeguimiento)) {
            for(AppSeguimientoItemPremiumEntity item: resSeguimiento.getItems()) {
                if(item.getItem().getNombre().equals("Consulta")) {
                    item.setEstado("Reclamado");
                    iSeguimientoItemPremiumService.update(item.getId(), item);
                }
            }
        }
    }
}
