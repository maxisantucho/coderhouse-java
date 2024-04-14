package com.coderhouse.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	
	@Bean
	OpenAPI customOpenAPI () {
		return new OpenAPI()
				.info(new Info()
						.title("API-RESTful | Java | CoderHouse")
						.version("1.0.0")
						.description("API REST proporciona endpoints para obtener alumnos"
								+ " y sus cursos con operaciones CRUD(Create, Read, Update, Delete),"
								+ " es decir: listar, agregar, mostrar y editar tanto alumnos y cursos."
								+ " La API la documentamos utilizando Swagger que nos facilita "
								+ "la comprencion de los endpoints y sus usos.")
						.contact(new Contact()
								.name("Maxi Santucho")
								.email("maxisantucho@hotmail.com")
								.url("https://www.instagram.com/maxi__santucho/"))
						.license(new License()
								.name("Licencia")
								.url("https://github.com/maxisantucho")))
						.servers(List.of(new Server()
								.url("http://localhost:8080")
								.description("Servidor local")));
	}

}
