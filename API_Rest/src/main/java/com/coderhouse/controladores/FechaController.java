package com.coderhouse.controladores;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fecha")
public class FechaController {
	
	//DESAFIO CODERHOUSE
	
	private int contador = 0;
	
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String obtenerFechaYHora() {
		contador++;
		LocalDateTime fechaYHora = LocalDateTime.now();
		String mensaje = "Fecha y Hora Actual: "
						+ fechaYHora.toString()
						+ "\nNumero de veces que fue invocado: "
						+ contador;
		return mensaje;
	}
	
}
