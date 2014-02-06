package javaone.servlet;

import javaone.scripting.AsciidoctorJSContext;
import javaone.scripting.DustJSContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * TODO
 *
 * @author Viktor Gamov (viktor.gamov@faratasystems.com)
 * @since 1/30/14
 */
@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Application is starting...");
        ServletContext ctx = servletContextEvent.getServletContext();

        AsciidoctorJSContext adoctor = new AsciidoctorJSContext();
        adoctor.init(this.getClass().getClassLoader(), "nashorn");
        ctx.setAttribute("asciidoctor", adoctor);

        DustJSContext dustjs = new DustJSContext();
        dustjs.init(this.getClass().getClassLoader(), "nashorn");
        ctx.setAttribute("dustjs", dustjs);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Application is shutting down...");
    }
}
