package com.pfriedrich.scoop.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class ThymeleafConfig {

	@Bean
	public ServletContextTemplateResolver templateResolver(){
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("LEGACYHTML5");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(false);
		resolver.setOrder(1);
		
		return resolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addTemplateResolver(templateResolver());		
		
		return engine;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		
		return resolver;
	}
}
