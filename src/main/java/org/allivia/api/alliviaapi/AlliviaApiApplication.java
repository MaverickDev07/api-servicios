package org.allivia.api.alliviaapi;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import freemarker.template.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.allivia.api.alliviaapi.entities.AppFichadocumentosEntity;
/*import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;*/
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.voximplant.apiclient.ClientException;
import com.voximplant.apiclient.VoximplantAPIClient;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

//extends SpringBootServletInitializer
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Allivia API", version = "2.0", description = "Allivia Services"))
@PropertySources({
		@PropertySource("file:./config/application.properties"),
})
@EnableConfigurationProperties({ AppFichadocumentosEntity.class })
public class AlliviaApiApplication implements CommandLineRunner {
	@Value("${spring.mail.host}")
	private String mailHost;
	@Value("${spring.mail.port}")
	private int mailPort;
	@Value("${spring.mail.username}")
	private String mailUsername;
	@Value("${spring.mail.password}")
	private String mailPassword;
	@Value("${spring.mail.protocol}")
	private String mailProtocol;
	@Value("${executor.service.thread.pool}")
	private int executorServiceThreadPool;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private Configuration config;

	public static void main(String[] args) { SpringApplication.run(AlliviaApiApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Run Console");
	}

	@Bean()
	public VoximplantAPIClient voximplantAPIClient() throws IOException, ClientException {
		Resource resource = resourceLoader.getResource("file:./config/voximplant_account_service.json");
		return new VoximplantAPIClient(resource.getFile().getAbsolutePath());
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(mailPort);

		mailSender.setUsername(mailUsername);
		mailSender.setPassword(mailPassword);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", mailProtocol);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(executorServiceThreadPool);
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AlliviaApiApplication.class);
	}*/
}
