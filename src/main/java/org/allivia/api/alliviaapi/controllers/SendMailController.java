package org.allivia.api.alliviaapi.controllers;

import org.allivia.api.alliviaapi.entities.AppMailEntity;
import org.allivia.api.alliviaapi.services.impl.MailServiceImpl;
import org.allivia.api.alliviaapi.services.impl.SendMailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/sendMail")
public class SendMailController extends BaseControllerImpl<AppMailEntity, MailServiceImpl> {
    @Autowired
    private SendMailServiceImpl mailService;

    @Autowired
    private MailServiceImpl emailServiceImpl;

    // @RequestMapping(value="/", method = RequestMethod.POST)
    @Override
    public ResponseEntity<?> save(@RequestBody AppMailEntity email) {
        try {
            mailService.sendSimpleEmail(email.getPara(), email.getMensaje(), email.getAsunto());
            // mailService.sendSuscripcionEmail(email.getPara(), "https://izi-content.s3-us-west-1.amazonaws.com/facturas/iZi_Pruebas_factura-6992c75a-26cd-49cb-8686-e0f5e0d83d85.pdf", "Gary Guzmán", "Plan Suscripcion", email.getAsunto());
            return ResponseEntity.status(HttpStatus.OK).body(emailServiceImpl.save(email));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"Error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    /*public ResponseEntity<?> sendMail(@RequestBody Email email) {
        try {
            String response = mailService.sendSimpleEmail("gary.2810.dav@gmail.com","This is the Email Body","This is the Email Subject");
            return ResponseEntity.status(HttpStatus.OK).body("{\"SendMail\":"+response+"}");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\"Error. Por favor intente más tarde.\"}");
        }
    }*/

    /*@GetMapping("api/v1/sendMailAttachment")
    public ResponseEntity<?> sendMailAttachment() throws MessagingException {
        try {
            String response = mailService.sendEmailWithAttachment("iofullstack.dev@gmail.com",
                    "This is Email Body with Attachment...",
                    "This email has attachment",
                    "/home/gary/Imágenes/moduloRelay.gif");
            return ResponseEntity.status(HttpStatus.OK).body("{\"SendMail\":"+response+"}");
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\"Error. Por favor intente más tarde.\"}");
        }
    }*/
}
