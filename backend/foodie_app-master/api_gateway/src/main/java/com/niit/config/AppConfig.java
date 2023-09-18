/*
*Author : Ankit Ambekar
*Date : 
Created with : IntelliJ IDEA Community Edition
*/
package com.niit.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableEurekaClient
public class AppConfig {
    @Bean
    public RouteLocator route (RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p->p.path("/api/v2/**","/city/**").uri("lb://UserAuthenticationService"))
                .route(p->p.path("/api/v1/**","/restaurant/**").uri("lb://UserRestaurantService"))
                .route(p->p.path("/image/**").uri("lb://image"))
                .build();
    }
}
