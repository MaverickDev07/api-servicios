package org.allivia.api.alliviaapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.allivia.api.alliviaapi.entities.AppAgendacitaEntity;
import org.allivia.api.alliviaapi.entities.AppFacturaEntity;
import org.allivia.api.alliviaapi.entities.AppPagoSuscripcionPaciente;
import org.allivia.api.alliviaapi.entities.AppSuscripcionEntity;
import org.allivia.api.alliviaapi.entities.models.AppPacienteTarjeta;
import org.allivia.api.alliviaapi.entities.models.AppPagoTarjetaPaciente;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.PayException;
import org.allivia.api.alliviaapi.paymentscbs.checkoutapi.ReceiptModel;
import org.allivia.api.alliviaapi.services.IFacturacionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/facturacion")
public class FacturacionController {
    public static final Logger logger = LogManager.getLogger(SuscripcionServicioController.class);
    @Autowired
    private IFacturacionService iFacturacionService;

    @PostMapping("/suscripcion/paciente/add")
    public ResponseEntity<?> saveFactucacion(@RequestBody AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws PayException, Exception {
        try {
            AppSuscripcionEntity suscripcion;
            AppPacienteTarjeta appPacienteTarjeta = iFacturacionService.getDataPaciente(appPagoSuscripcionPaciente);
            if (appPacienteTarjeta.getSuscripcion().getPrecio() < 1) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "No puede Suscribirse con el plan Inicial o Monto 0");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
            }
            if (appPagoSuscripcionPaciente.getMonto() == null || appPagoSuscripcionPaciente.getMonto() == 0)
                appPagoSuscripcionPaciente.setMonto(appPacienteTarjeta.getSuscripcion().getPrecio());
            ReceiptModel pagoTarjeta = iFacturacionService.savePayment(appPacienteTarjeta, appPagoSuscripcionPaciente);
            // logger.error("Model savePayment: ", pagoTarjeta.toString());
            if (pagoTarjeta.getDecision().equals("ACCEPT")) {
                suscripcion = iFacturacionService.saveFacturaSuscripcion(appPacienteTarjeta, appPagoSuscripcionPaciente);
                return ResponseEntity.ok(suscripcion);
            }
            else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "Error pago con targeta");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
            }
        } catch (PayException e) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(data);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*@PostMapping("/pagoTarjeta/paciente")
    public ResponseEntity<?> savePagoTarjeta(@RequestBody AppPagoTarjetaPaciente appPagoTarjetaPaciente) throws PayException, Exception {
        try {
            Object result = iFacturacionService.savePayment(appPagoTarjetaPaciente);
            return ResponseEntity.ok(result);
        } catch (PayException e) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(data);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/

    @PostMapping("/suscripcion/paciente/add/test")
    public ResponseEntity<?> saveFactucacionTest(@RequestBody AppPagoSuscripcionPaciente appPagoSuscripcionPaciente) throws JsonProcessingException {
        try {
            Object result = iFacturacionService.saveFacturaTest(appPagoSuscripcionPaciente);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                HashMap<String, Object> data = new HashMap<>();
                data.put("message", "Error al suscribir el paciente");
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(data);
            }
        } catch (PayException e) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(data);
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/email")
    public ResponseEntity<Object> sendEmail() throws Exception {
        try {
            iFacturacionService.sendEmail();
            return ResponseEntity.ok("{\"status\": \"Email prueba sendSuscripcionEmail enviado\"}");
        } catch (Exception e) {
            logger.error("Error al obtener la consulta", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
