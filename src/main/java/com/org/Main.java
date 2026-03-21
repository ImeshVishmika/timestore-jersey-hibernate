package com.org;

import com.org.config.AppConfig;
import com.org.filter.AdminPageAuthFilter;
import com.org.filter.UserPageAuthFilter;
import com.org.util.HibernateUtil;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
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
            Context userContext = tomcat.addWebapp("",new File("webapp/views/User").getAbsolutePath());
            tomcat.addServlet("","JerseyServlet",new ServletContainer(new AppConfig()));
            userContext.addServletMappingDecoded("/api/*","JerseyServlet");

            FilterDef userPageAuthFilterDef = new FilterDef();
            userPageAuthFilterDef.setFilterName("UserPageAuthFilter");
            userPageAuthFilterDef.setFilterClass(UserPageAuthFilter.class.getName());
            userPageAuthFilterDef.setFilter(new UserPageAuthFilter());
            userContext.addFilterDef(userPageAuthFilterDef);

            FilterMap profileFilterMap = new FilterMap();
            profileFilterMap.setFilterName("UserPageAuthFilter");
            profileFilterMap.addURLPattern("/profile.html");
            userContext.addFilterMap(profileFilterMap);

            FilterMap cartFilterMap = new FilterMap();
            cartFilterMap.setFilterName("UserPageAuthFilter");
            cartFilterMap.addURLPattern("/cart.html");
            userContext.addFilterMap(cartFilterMap);

            // Admin context at /timestore/admin
            Context adminContext = tomcat.addWebapp("/admin",new File("webapp/views/Admin").getAbsolutePath());
            tomcat.addServlet("/admin","AdminJerseyServlet",new ServletContainer(new AppConfig()));
            adminContext.addServletMappingDecoded("/api/*","AdminJerseyServlet");

//            FilterDef adminPageAuthFilterDef = new FilterDef();
//            adminPageAuthFilterDef.setFilterName("AdminPageAuthFilter");
//            adminPageAuthFilterDef.setFilterClass(AdminPageAuthFilter.class.getName());
//            adminPageAuthFilterDef.setFilter(new AdminPageAuthFilter());
//            adminContext.addFilterDef(adminPageAuthFilterDef);
//
//            FilterMap adminFilterMap = new FilterMap();
//            adminFilterMap.setFilterName("AdminPageAuthFilter");
//            adminFilterMap.addURLPattern("/*");
//            adminContext.addFilterMap(adminFilterMap);

            tomcat.start();
            tomcat.getServer().await();

        }catch (Exception e){
           e.printStackTrace();
        }
    }
}