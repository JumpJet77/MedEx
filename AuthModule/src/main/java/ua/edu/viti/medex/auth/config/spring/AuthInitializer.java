package ua.edu.viti.medex.auth.config.spring;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AuthInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext authApplicationContext = new AnnotationConfigWebApplicationContext();
		authApplicationContext.scan("ua.edu.viti.medex.auth", "ua.edu.viti.medex.main");
		servletContext.addListener(new ContextLoaderListener(authApplicationContext));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("auth", new DispatcherServlet(authApplicationContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
