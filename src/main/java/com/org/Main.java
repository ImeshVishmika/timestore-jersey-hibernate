package com.org;

import com.org.config.AppConfig;
import com.org.util.HibernateUtil;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.File;


public class Main {
    public static void main(String[] args) {

        try {

            HibernateUtil.getSessionFactory().openSession();

            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);
            tomcat.getConnector();

            // User context at /timestore
            Context userContext = tomcat.addWebapp("/timestore",new File("src/main/webapp/views/User").getAbsolutePath());
            tomcat.addServlet("/timestore","JerseyServlet",new ServletContainer(new AppConfig()));
            userContext.addServletMappingDecoded("/api/*","JerseyServlet");

            // Admin context at /timestore/admin
            Context adminContext = tomcat.addWebapp("/timestore/admin",new File("src/main/webapp/views/Admin").getAbsolutePath());
            tomcat.addServlet("/timestore/admin","AdminJerseyServlet",new ServletContainer(new AppConfig()));
            adminContext.addServletMappingDecoded("/api/*","AdminJerseyServlet");

            tomcat.start();
            tomcat.getServer().await();

        }catch (Exception e){
           e.printStackTrace();
        }
    }
}