package org.allivia.api.alliviaapi.services.impl;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class SendMailServiceImpl {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment environment;
    private Path fileStorageLocation = Paths.get("src/main/resources/static/").toAbsolutePath().normalize();

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        System.out.println("Mail sendSimpleEmail...");
        mailSender.send(message);
    }

    public void sendRegisterEmail(String toEmail,
                                String body,
                                String subject) throws MessagingException {
        /*MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        Context context = new Context();
        String htmlContent = templateEngine.process("index", context);

        message.setFrom("admin@allivia.app");
        message.setTo(toEmail);
        message.setText(htmlContent,true);
        message.setSubject(subject);

        System.out.println("Mail sendHtmlEmail...");
        mailSender.send(mimeMessage);*/
        //Context context = new Context();
        //String htmlContent = templateEngine.process("index", context);
        //System.out.println(context.toString());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", "./config/");
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        velocityEngine.init(props);
        Template template=velocityEngine.getTemplate("registro.html");
        VelocityContext context = new VelocityContext();
        context.put("email", toEmail);
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        String html=writer.toString();

        Path app = fileStorageLocation.resolve("images/app.png");
        Path logo = fileStorageLocation.resolve("images/logo.png");
        Path logoAllivia = fileStorageLocation.resolve("images/logoAllivia.png");
        Path facebook2x = fileStorageLocation.resolve("images/facebook2x.png");
        Path instagram2x = fileStorageLocation.resolve("images/instagram2x.png");
        Path linkedin2x = fileStorageLocation.resolve("images/linkedin2x.png");

        // System.out.println("ClassPath: " + logo.getPath());

        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(toEmail);
        message.setText(html,true);
        message.setSubject(subject);
        message.addInline("app", app.toFile());
        message.addInline("logo", logo.toFile());
        message.addInline("logoAllivia", logoAllivia.toFile());
        message.addInline("facebook2x", facebook2x.toFile());
        message.addInline("instagram2x", instagram2x.toFile());
        message.addInline("linkedin2x", linkedin2x.toFile());
        System.out.println("Mail sendHtmlEmail...");
        mailSender.send(mimeMessage);
    }

    public void sendSuscripcionEmail(String toEmail,
                                    String factura,
                                    String nombre,
                                    String plan,
                                    String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", "./config/");
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        velocityEngine.init(props);
        Template template=velocityEngine.getTemplate("suscripcion.html");
        VelocityContext context = new VelocityContext();
        context.put("nombre", nombre);
        context.put("plan", plan);
        context.put("factura", factura);
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        String html=writer.toString();

        Path logoAllivia = fileStorageLocation.resolve("images/logoAllivia.png");
        Path logo = fileStorageLocation.resolve("images/logo.png");
        Path laboratorio = fileStorageLocation.resolve("images/suscripcion/laboratorio.png");
        Path cita = fileStorageLocation.resolve("images/suscripcion/cita.png");
        Path objetivos = fileStorageLocation.resolve("images/suscripcion/objetivos.png");
        Path progreso = fileStorageLocation.resolve("images/suscripcion/progreso.png");
        Path facebook2x = fileStorageLocation.resolve("images/suscripcion/facebook2x.png");
        Path instagram2x = fileStorageLocation.resolve("images/suscripcion/instagram2x.png");
        Path linkedin2x = fileStorageLocation.resolve("images/suscripcion/linkedin2x.png");
        Path white_down = fileStorageLocation.resolve("images/suscripcion/white_down.png");

        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(toEmail);
        message.setText(html,true);
        message.setSubject(subject);
        message.addInline("logoAllivia", logoAllivia.toFile());
        message.addInline("logo", logo.toFile());
        message.addInline("laboratorio", laboratorio.toFile());
        message.addInline("cita", cita.toFile());
        message.addInline("objetivos", objetivos.toFile());
        message.addInline("progreso", progreso.toFile());
        message.addInline("facebook2x", facebook2x.toFile());
        message.addInline("instagram2x", instagram2x.toFile());
        message.addInline("linkedin2x", linkedin2x.toFile());
        message.addInline("white_down", white_down.toFile());
        System.out.println("Mail sendHtmlEmail...");
        mailSender.send(mimeMessage);
    }

    public void sendConfirmacionEmail(String toEmail,
                                      String factura,
                                      String nombrePaciente,
                                      String nombreDoctor,
                                      String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", "./config/");
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        velocityEngine.init(props);
        Template template=velocityEngine.getTemplate("confirmacionConsulta.html");
        VelocityContext context = new VelocityContext();
        context.put("nombrePaciente", nombrePaciente);
        context.put("nombreDoctor", nombreDoctor);
        context.put("factura", factura);
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        String html=writer.toString();

        Path logoAllivia = fileStorageLocation.resolve("images/logoAllivia.png");
        Path logo = fileStorageLocation.resolve("images/logo.png");
        Path facebook2x = fileStorageLocation.resolve("images/suscripcion/facebook2x.png");
        Path instagram2x = fileStorageLocation.resolve("images/suscripcion/instagram2x.png");
        Path linkedin2x = fileStorageLocation.resolve("images/suscripcion/linkedin2x.png");

        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(toEmail);
        message.setText(html,true);
        message.setSubject(subject);
        message.addInline("logoAllivia", logoAllivia.toFile());
        message.addInline("logo", logo.toFile());
        message.addInline("facebook2x", facebook2x.toFile());
        message.addInline("instagram2x", instagram2x.toFile());
        message.addInline("linkedin2x", linkedin2x.toFile());
        System.out.println("Mail sendHtmlEmail...");
        mailSender.send(mimeMessage);
    }

    public void sendFarmaciaEmail(String toEmail,
                                  String telefono,
                                  String nombrePaciente,
                                  String subject,
                                  String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);

        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", "./config/");
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        velocityEngine.init(props);
        Template template=velocityEngine.getTemplate("farmaciaMedicamentos.html");
        VelocityContext context = new VelocityContext();
        context.put("nombrePaciente", nombrePaciente);
        context.put("body", body);
        context.put("telefono", telefono);
        // context.put("medicamentosPDF", factura);
        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        String html=writer.toString();

        Path logoAllivia = fileStorageLocation.resolve("images/logoAllivia.png");
        Path logo = fileStorageLocation.resolve("images/logo.png");
        Path facebook2x = fileStorageLocation.resolve("images/suscripcion/facebook2x.png");
        Path instagram2x = fileStorageLocation.resolve("images/suscripcion/instagram2x.png");
        Path linkedin2x = fileStorageLocation.resolve("images/suscripcion/linkedin2x.png");

        message.setFrom(environment.getProperty("spring.mail.username"));
        message.setTo(toEmail);
        message.setText(html,true);
        message.setSubject(subject);
        message.addInline("logoAllivia", logoAllivia.toFile());
        message.addInline("logo", logo.toFile());
        message.addInline("facebook2x", facebook2x.toFile());
        message.addInline("instagram2x", instagram2x.toFile());
        message.addInline("linkedin2x", linkedin2x.toFile());
        System.out.println("Mail sendHtmlEmail...");
        mailSender.send(mimeMessage);
    }

    public String sendEmailWithAttachment(String toEmail,
                                          String body,
                                          String subject,
                                          String attachment) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(environment.getProperty("spring.mail.username"));
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(new File(attachment));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        return "Mail sendEmailWithAttachment...";
    }
}
