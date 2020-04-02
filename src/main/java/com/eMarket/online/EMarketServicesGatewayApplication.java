package com.eMarket.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class EMarketServicesGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EMarketServicesGatewayApplication.class, args);
	}

	// Configuration Statique des Routes Services Externes
	@Bean
	RouteLocator staticConfigurationRoutes(RouteLocatorBuilder builder) {
		Builder routeLocatorBuilder = builder.routes()
				.route(r->r.path("/emarketProducts/**").uri("lb://EMARKET-ONLINE").id("online-service"))
				.route(r->r.path("/emarketCategories/**").uri("lb://EMARKET-ONLINE").id("online-service"))
				.route(r->r.path("/emarketSubCategories/**").uri("lb://EMARKET-ONLINE").id("online-service"))
				.route(r->r.path("/login/**").uri("lb://EMARKET-USERS").id("users-service"))
				.route(r->r.path("/user/**").uri("lb://EMARKET-USERS").id("users-service"))
				.route(r->r.path("/emarketUsers/**").uri("lb://EMARKET-USERS").id("users-service"))
				.route(r->r.path("/emarketRoles/**").uri("lb://EMARKET-USERS").id("users-service"));
		return routeLocatorBuilder.build();
	}
	
	// Configuration Dynamique des Routes Services Externes
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicConfigurationRoutes(ReactiveDiscoveryClient reactiveDiscoveryClient,
			DiscoveryLocatorProperties discoveryLocatorProperties) {
		return new DiscoveryClientRouteDefinitionLocator(reactiveDiscoveryClient, discoveryLocatorProperties);
	}
}
