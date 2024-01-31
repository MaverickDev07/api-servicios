package org.allivia.api.alliviaapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.voximplant.apiclient.ClientException;
import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.security.AlreadyExistsException;
import org.allivia.api.alliviaapi.services.IAgendaCitaService;
import org.allivia.api.alliviaapi.services.INotificacionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class AgendaCitaController {
    public static final Logger logger = LogManager.getLogger(AgendaCitaController.class);
    @Autowired
    private IAgendaCitaService iAgendaCitaService;

    @Autowired
    private INotificacionService iNotificacionService;

    @PostMapping("/agendaCita/addPago")
    public ResponseEntity<Object> Add(@RequestBody AppAgendacitaEntity appAgendacitaEntity, @RequestParam String token) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.save(appAgendacitaEntity,token);
            if (result != null) {

                if(result.equals(appAgendacitaEntity)){
                    return ResponseEntity.ok(result);
                }else{
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(result);
                }
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "La hora seleccionada para esta cita ya fue agendada por favor, seleccione una hora o fecha diferente.");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(data);
            }
        } catch (PayException e){
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(data);
        }catch (Exception e) {
            logger.error("Error al guardar", e);
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", "Error al agendar cita");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(data);
        }
    }

    @PostMapping("/agendaCita/add")
    public ResponseEntity<Object> Add(@RequestBody AppAgendacitaEntity appAgendacitaEntity) throws JsonProcessingException {
        try {
            // System.out.println(appAgendacitaEntity.toString());
            // AppNotificacionEntity notificacion = iNotificacionService.saveByAgendaCita(appAgendacitaEntity, "DOCTOR");
            Object result = iAgendaCitaService.save(appAgendacitaEntity);

            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "La hora seleccionada para esta cita ya fue agendada por favor, seleccione una hora o fecha diferente.");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(data);
            }

        } catch (Exception e) {
            logger.error("Error al guardar", e);
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", "Error al agendar cita");
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(data);
        }
    }

    @PostMapping("/agendaCita/doctor/add")
    public ResponseEntity<Object> AddDoctor(@RequestBody AppAgendacitaEntity appAgendacitaEntity) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.saveDoctor(appAgendacitaEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al guardar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/agendaCita/update")
    public ResponseEntity<Object> Update(@RequestBody AppAgendacitaEntity appAgendacitaEntity) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.update(appAgendacitaEntity);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al actualizar", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/consultas/find/doctor/paciente")
    public ResponseEntity<Object> getListConsultasNombreDoctor(@RequestParam long idPaciente, @RequestParam(value = "nombreDoctor", required = false) String nombreDoctor, @RequestParam(value = "estado", required = false) List<String> estado) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getConsultasPacienteDoctor(idPaciente, nombreDoctor, estado);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/consultas/find/dia")
    public ResponseEntity<Object> getListConsultasNombreDoctor(@RequestParam String idVoxImplant, @RequestParam String fecha) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getConsultasVoxImplant(idVoxImplant, fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/test/voximplant")
    public ResponseEntity<Object> getListUsersVoximplant(@RequestParam int idVoxImplant) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.testVoxImplant(idVoxImplant);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.log(Level.ERROR, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/consultas/find/pacientes/doctor/dia")
    public ResponseEntity<Object> getListConsultasPacientesDoctor(@RequestParam long idDoctor, @RequestParam(value = "fecha", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getListPacientesDoctor(idDoctor, fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @GetMapping("/agendaCita/historialConsultas/doctor")
    public ResponseEntity<Object> getListHistorialConsultas(@RequestParam long idDoctor, @RequestParam(value = "estado") List<String> estado) throws JsonProcessingException {
        try {
            List<AppAgendacitaEntity> result = iAgendaCitaService.getHistorialConsultasDoctor(idDoctor, estado);
            if ( result.contains(null) || Objects.isNull(result)  )
                return ResponseEntity.ok("{\"status\": \"No se encontró hitorial consultas para este doctor\"}");
            else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/LastPendingHistorialConsultas/doctor")
    public ResponseEntity<Object> getLastPendingHistorialConsultas(@RequestParam long idDoctor) throws JsonProcessingException {
        try {
            List<AppAgendacitaEntity> result = iAgendaCitaService.getLastPendingHistorialConsultasDoctor(idDoctor);
            if ( result.contains(null) || Objects.isNull(result)  )
                return ResponseEntity.ok("{\"status\": \"No se encontró pendientes\"}");
            else
                return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/agendaCita/adminPacientes/doctor")
    public ResponseEntity<Object> getListAdminPacientesDoctor(@RequestParam long idDoctor, @RequestParam(value = "estado") List<String> estado) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getAdminPacientesDoctor(idDoctor, estado);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/find/generico/doctor")
    public ResponseEntity<Object> getListAdminPacientesDoctor(@RequestParam long idDoctor, @RequestParam String sintoma, @RequestParam String nombre) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getFindTodos(idDoctor, sintoma, nombre);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/consultas/find/pacientes/doctor/mes")
    public ResponseEntity<Object> getListConsultasPacientesDoctorMes(@RequestParam long idDoctor, @RequestParam(value = "fecha", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getListPacientesDoctorPorMes(idDoctor, fecha);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/agendaCita/horarios/fecha/doctor")
    public ResponseEntity<Object> getListHorariosFechaDoctor(@RequestParam long idDoctor, @RequestParam(value = "fecha", required = false) String fecha, @RequestParam(value = "restante", required = false) Boolean restante) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getListHorariosFechaDoctor(idDoctor, fecha, restante);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/agendaCita/adminPaciente/paciente/historial/{id}")
    public ResponseEntity<Object> getLitHistorial(@PathVariable long id) {
        try {
            List<AppAgendacitaEntity> result = iAgendaCitaService.getLitHistorial(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/adminPaciente/paciente/medicamentos/{id}")
    public ResponseEntity<Object> getListMedicamentos(@PathVariable long id) {
        try {
            List<AppFichamedicamentosEntity> result = iAgendaCitaService.getListMedicamentos(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/adminPaciente/paciente/laboratorios/{id}")
    public ResponseEntity<Object> getListLaboratorios(@PathVariable long id) {
        try {
            List<AppFichalaboratoriosEntity> result = iAgendaCitaService.getListLaboratorios(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/adminPaciente/paciente/programas/{id}")
    public ResponseEntity<Object> getListProgramas(@PathVariable long id) {
        try {
            List<ProgramasEntity> result = iAgendaCitaService.getLitProgramas(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/agendaCita/adminPaciente/paciente/tratamientos/{id}")
    public ResponseEntity<Object> getListTratamientos(@PathVariable long id) {
        try {
            List<TratamientosEntity> result = iAgendaCitaService.getLitTratamientos(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/agendaCita/doctor/actual")
    public ResponseEntity<Object> getAgendaCitaActual(@RequestParam long idDoctor,@RequestParam String fecha) throws JsonProcessingException {
        try {
            Object result = iAgendaCitaService.getAgendaCitaActual(idDoctor,fecha);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "No hay citas para fecha y hora");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(data);
            }
        } catch (Exception e) {
            logger.error("Error al realizar la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
