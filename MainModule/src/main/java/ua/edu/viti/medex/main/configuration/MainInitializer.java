package ua.edu.viti.medex.main.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MainInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext mainApplicationContext = new AnnotationConfigWebApplicationContext();
		servletContext.addListener(new ContextLoaderListener(mainApplicationContext));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("main", new DispatcherServlet(mainApplicationContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}
