package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.*;
import org.allivia.api.alliviaapi.services.IEntidadMedicaService;
import org.allivia.api.alliviaapi.services.impl.DetalleEnvioServiceImpl;
import org.allivia.api.alliviaapi.services.impl.ListarMedicamentosPdf;
import org.allivia.api.alliviaapi.services.impl.SendMailServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/detalleEnvio")
public class DetalleEnvioController extends BaseControllerImpl<AppDetalleEnvioEntity, DetalleEnvioServiceImpl> {
    public static final Logger logger = LogManager.getLogger(DetalleEnvioController.class);

    @Autowired
    private SendMailServiceImpl sendMailService;
    @Autowired
    private ListarMedicamentosPdf listarMedicamentosPdf;
    @Autowired
    private IEntidadMedicaService iEntidadMedicaService;

    @Override
    public ResponseEntity<?> save(@RequestBody AppDetalleEnvioEntity appDetalleEnvioEntity) {
        try {
            AppAgendacitaEntity cita = listarMedicamentosPdf.getRecetasPacienteByIdAgendacita(appDetalleEnvioEntity.getAgendaCita().getId());
            AppEntidadMedicaEntity appEntidadMedica = iEntidadMedicaService.findById(appDetalleEnvioEntity.getEntidadMedica().getId());
            String nombrePaciente = cita.getPaciente().getPerfilPaciente().getNombre() + " " + cita.getPaciente().getPerfilPaciente().getApellido();
            String body="<ul style=\"font-size: 15px;\">";

            for (AppFichamedicamentosEntity medicamentos: cita.getMedicamentos()) {
                body += "<li>" + medicamentos.getNombreMedicamento() + "</li>";
            }
            body += "</ul>";

            sendMailService.sendFarmaciaEmail(appEntidadMedica.getEmail(), appDetalleEnvioEntity.getTelefono(), nombrePaciente, "Pedido Allivia", body);

            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(appDetalleEnvioEntity));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Error\": \""+e.getMessage()+"\"}");
        }
    }
}
