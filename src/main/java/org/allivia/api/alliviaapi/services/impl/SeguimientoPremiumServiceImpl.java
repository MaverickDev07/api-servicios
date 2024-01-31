package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.repositories.*;
import org.allivia.api.alliviaapi.services.ISeguimientoPremiumService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class SeguimientoPremiumServiceImpl extends BaseServiceImpl<AppSeguimientoPremiumEntity, Long> implements ISeguimientoPremiumService {
    public static final Logger logger = LogManager.getLogger(SeguimientoPremiumServiceImpl.class);
    @Autowired
    private ISeguimientoPremiumRepository iSeguimientoPremiumRepository;
    @Autowired
    private IPacienteRepository iPacienteRepository;
    @Autowired
    private IItemPremiumRepository iItemPremiumRepository;
    @Autowired
    private ISeguimientoItemPremiumRepository iSeguimientoItemPremiumRepository;
    @Autowired
    private IUserRepository iUserRepository;

    public SeguimientoPremiumServiceImpl(IBaseRepository<AppSeguimientoPremiumEntity, Long> baseRepository) { super(baseRepository); }

    @Override
    public AppSeguimientoPremiumEntity findSeguimientoPremiumPaciente(Long id) throws Exception {
        logger.log(Level.INFO, "findSeguimientoPremiumPaciente ID: " + id );
        try {
            AppSeguimientoPremiumEntity resSeguimiento = iSeguimientoPremiumRepository.findSeguimientoPremiumPaciente(id);

            if (resSeguimiento == null) {
                AppSeguimientoPremiumEntity seguimiento = new AppSeguimientoPremiumEntity();
                AppPacienteEntity paciente = iPacienteRepository.findById(id).get();
                seguimiento.setPaciente(paciente);
                AppSeguimientoPremiumEntity seguimientoGuardado = iSeguimientoPremiumRepository.save(seguimiento);
                List<AppItemPremiumEntity> itemPremium = iItemPremiumRepository.listItemPremiumOrder();
                for( AppItemPremiumEntity item: itemPremium ) {
                    AppSeguimientoItemPremiumEntity seguimientoItem = new AppSeguimientoItemPremiumEntity();
                    seguimientoItem.setSeguimiento(seguimientoGuardado);
                    seguimientoItem.setItem(item);
                    seguimientoItem.setEstado("SinReclamar");
                    //seguimientoGuardado.addItemsPremium(item, "Libre");
                    AppSeguimientoItemPremiumEntity seguimientoItemGuardado = iSeguimientoItemPremiumRepository.save(seguimientoItem);
                    seguimientoItemGuardado.setItemPremium(item);
                    seguimientoGuardado.getItems().add(seguimientoItemGuardado);
                }
                AppSeguimientoPremiumEntity seguimientoRespuesta = findById(seguimientoGuardado.getId());
                AppUsuarioEntity usuarioEntity = iUserRepository.findByUsuarioId(paciente.getUsuarioId());
                seguimientoRespuesta.getPaciente().setPerfilPaciente(usuarioEntity);

                return seguimientoRespuesta;
            } else {
                AppPacienteEntity paciente = iPacienteRepository.findById(id).get();
                AppUsuarioEntity usuarioEntity = iUserRepository.findByUsuarioId(paciente.getUsuarioId());
                resSeguimiento.getPaciente().setPerfilPaciente(usuarioEntity);


                for (int i=0; i<resSeguimiento.getItems().size()-1; i++) {
                    AppSeguimientoItemPremiumEntity seguimientoItemPremiumAUX;
                    for (int j=i+1; j<resSeguimiento.getItems().size(); j++) {
                        System.out.println(resSeguimiento.getItems().get(i).getItem().getId() + " <---> " + resSeguimiento.getItems().get(j).getItem().getId());
                        if ( resSeguimiento.getItems().get(i).getItem().getId() > resSeguimiento.getItems().get(j).getItem().getId() ) {
                            seguimientoItemPremiumAUX = resSeguimiento.getItems().get(i);
                            resSeguimiento.getItems().set(i, resSeguimiento.getItems().get(j));
                            resSeguimiento.getItems().set(j, seguimientoItemPremiumAUX);
                        }
                    }
                }
                /*Collections.sort(resSeguimiento.getItems(), new Comparator<AppSeguimientoItemPremiumEntity>() {
                    @Override
                    public int compare(AppSeguimientoItemPremiumEntity asip1, AppSeguimientoItemPremiumEntity asip2) {
                        return new Integer(Math.toIntExact(asip1.getItem().getId())).compareTo(new Integer(Math.toIntExact(asip2.getItem().getId())));
                    }
                });*/
                for (AppSeguimientoItemPremiumEntity item: resSeguimiento.getItems()) {
                    AppItemPremiumEntity itemPremium = new AppItemPremiumEntity();
                    System.out.println(item.getItem().getId());
                    itemPremium.setId(item.getItem().getId());
                    itemPremium.setCreado(item.getItem().getCreado());
                    itemPremium.setModificado(item.getItem().getModificado());
                    itemPremium.setIdData(item.getItem().getIdData());
                    itemPremium.setNombre(item.getItem().getNombre());
                    itemPremium.setTitulo(item.getItem().getTitulo());
                    itemPremium.setSubtitulo(item.getItem().getSubtitulo());
                    itemPremium.setTextoBoton(item.getItem().getTextoBoton());
                    item.setItemPremium(itemPremium);
                }
                /*for (int i=0; i<resSeguimiento.getItems().size()-1; i++) {
                    AppSeguimientoItemPremiumEntity seguimientoItemPremium;
                    for (int j=i+1; j<resSeguimiento.getItems().size(); j++) {
                        if ( resSeguimiento.getItems().get(i).getId() > resSeguimiento.getItems().get(j).getId() ) {
                            seguimientoItemPremium = resSeguimiento.getItems().get(i);
                            resSeguimiento.getItems().set(i, resSeguimiento.getItems().get(j));
                            resSeguimiento.getItems().set(j, seguimientoItemPremium);
                        }
                    }
                }*/
                return resSeguimiento;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
