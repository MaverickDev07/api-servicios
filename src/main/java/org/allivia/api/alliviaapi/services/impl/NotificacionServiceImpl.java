package org.allivia.api.alliviaapi.services.impl;

import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppDetalleNotificacionEntity;
import org.allivia.api.alliviaapi.entities.AppNotificacionEntity;
import org.allivia.api.alliviaapi.entities.AppUsuarioEntity;
import org.allivia.api.alliviaapi.repositories.IBaseRepository;
import org.allivia.api.alliviaapi.repositories.INotificacionRepository;
import org.allivia.api.alliviaapi.services.IDetalleNotificacionService;
import org.allivia.api.alliviaapi.services.INotificacionService;
import org.allivia.api.alliviaapi.services.IUserService;
import org.allivia.api.alliviaapi.services.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotificacionServiceImpl extends BaseServiceImpl<AppNotificacionEntity, Long> implements INotificacionService {
    public static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private INotificacionRepository iNotificacionRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private INotificacionService iNotificacionService;
    @Autowired
    private IDetalleNotificacionService iDetalleNotificacionService;

    public NotificacionServiceImpl(IBaseRepository<AppNotificacionEntity, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<AppNotificacionEntity> findAllByIdReceptor(Long idReceptor) throws Exception {
        try {
            List<AppNotificacionEntity> notificaciones = iNotificacionRepository.findAllByIdReceptor(idReceptor);
            return notificaciones;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Page<AppNotificacionEntity> findAllByIdReceptor(Long idReceptor, Pageable pageable) throws Exception {
        try {
            Page<AppNotificacionEntity> notificaciones = iNotificacionRepository.findAllByIdReceptor(idReceptor, pageable);
            return notificaciones;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public AppNotificacionEntity saveByAgendaCita(AppAgendacitaEntity agendacita, String para, String estado) throws Exception {
        try {
            logger.log(Level.INFO, "saveByAgendaCita, request Agendacita recived: " + agendacita.toString());
            AppNotificacionEntity notificacion = new AppNotificacionEntity();
            AppUsuarioEntity usuarioPaciente = new AppUsuarioEntity();
            AppUsuarioEntity usuarioDoctor = new AppUsuarioEntity();
            AppDetalleNotificacionEntity detalle = new AppDetalleNotificacionEntity();

            Long usuarioIdP = userService.findIdUsuarioByIdPaciente(agendacita.getIdPaciente());
            Long usuarioIdD = userService.findIdUsuarioByIdDoctor(agendacita.getIdDoctor());

            usuarioPaciente.setUsuarioId(usuarioIdP);
            usuarioDoctor.setUsuarioId(usuarioIdD);

            // Guardamos el Detalle de la Notificación
            detalle.setFecha(agendacita.getFecha().split(" ")[0]);
            detalle.setHora(agendacita.getHorario().split(" - ")[0]);
            detalle.setEstado(estado);
            detalle.setIdAgendacita(agendacita);
            AppDetalleNotificacionEntity saveDetalle = iDetalleNotificacionService.save(detalle);

            // notificacion.setMensaje("Para la fecha: "+agendacita.getFecha().split(" ")[0]+", en el horario de: "+agendacita.getHorario()+", motivo de consulta: "+agendacita.getMotivoConsulta()+". Estado de la consulta: "+estado);
            notificacion.setTitulo("Cita médica agendada");
            notificacion.setMensaje("Motivo de la consulta: "+agendacita.getMotivoConsulta());
            notificacion.setDetalle(saveDetalle);
            if (para == "DOCTOR") {
                notificacion.setUsuarioEmisor(usuarioDoctor);
                notificacion.setUsuarioReceptor(usuarioDoctor);
            }
            if (para == "PACIENTE") {
                notificacion.setUsuarioEmisor(usuarioDoctor);
                notificacion.setUsuarioReceptor(usuarioPaciente);
            }

            AppNotificacionEntity response = iNotificacionService.save(notificacion);
            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int updateEstadoNotificacion(Long id, String estado) throws Exception {
        // Verificar si existe el estado
        /*AppNotificacionEntity notificacion = iNotificacionRepository.getById(id);
        if (notificacion == null || Objects.isNull(notificacion)) {

        }*/
        return iNotificacionRepository.updateEstadoNotificacion(id, estado);
    }
}
