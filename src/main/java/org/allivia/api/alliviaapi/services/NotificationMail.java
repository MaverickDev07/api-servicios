package org.allivia.api.alliviaapi.services;


import java.io.StringWriter;
import java.util.Properties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class NotificationMail {
    @Autowired
    private JavaMailSender mailSender;
    @Value( "${spring.mail.username}")
    private String mailUsername;

    public void sendMessage(String to, String subject)  {
        VelocityEngine velocityEngine = new VelocityEngine();

        Properties props = new Properties();
        props.put("file.resource.loader.path", "./config/");
        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");

        velocityEngine.init(props);
        Template template=velocityEngine.getTemplate("mail.html");
        VelocityContext context = new VelocityContext();

        StringWriter writer = new StringWriter();
        template.merge( context, writer );
        String text=writer.toString();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(mailUsername);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}