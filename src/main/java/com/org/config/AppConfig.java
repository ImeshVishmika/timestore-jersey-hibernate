package com.org.config;

import org.glassfish.jersey.server.ResourceConfig;

public class AppConfig extends ResourceConfig {

    public AppConfig(){
        packages("com.org.controller.user", "com.org.controller.admin");
    }

}
