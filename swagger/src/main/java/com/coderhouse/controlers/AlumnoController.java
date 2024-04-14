package com.coderhouse.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.modelos.Alumno;
import com.coderhouse.servicios.AlumnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/alumnos")
@Tag(name = "Gestion alumnos", description = "Endpoints para controlar alumnos")
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoSercive;

	@Operation(summary = "Obtener lista de alumnos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La lista de alumnos fue obenida correctamente",
					content = {
							@Content(mediaType = "application/json",schema = @Schema(implementation = Alumno.class))
					}),
			@ApiResponse(responseCode = "500", description = "Error interno servidor", content = @Content)
	})
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Alumno>> listarAlumnos() {
		try {
			List<Alumno> alumno = alumnoSercive.listarAlumno();
			return new ResponseEntity<>(alumno, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Obtener alumno por DNI")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Alumno Encontrado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))}),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
	})
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Alumno> mostrarAlumnoPorDNI(@PathVariable("id") int dni) {
		try {
			Alumno alumno = alumnoSercive.mostrarAlumnoPorDni(dni);
			if(alumno != null) {
				return new ResponseEntity<>(alumno, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Agregar un nuevo alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Alumno agregado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))}),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
	})
	@PostMapping(value = "/agregar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Alumno> agregarAlumno(@RequestBody Alumno alumno) {
		alumnoSercive.agregarAlumno(alumno);
		return new ResponseEntity<>(alumno, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Editar un alumno segun su DNI")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Alumno editado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Alumno.class))}),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
	})
	@PutMapping(value = "/{id}/editar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Alumno> editarAlumnoPorDni(@PathVariable("id") int dni, @RequestBody Alumno alumno) {
		Alumno alumnoEditado = alumnoSercive.editarAlumnoPorDni(dni, alumno);
		if(alumnoEditado != null) {
			return new ResponseEntity<>(alumno, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@Operation(summary = "Eliminar un alumno")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Alumno eliminado", content = @Content),
			@ApiResponse(responseCode = "404", description = "Alumno no encontrado", content = @Content)
	})
	@DeleteMapping(value = "/{id}/eliminar")
	public ResponseEntity<Void> eliminarAlumnoPorDNI(@PathVariable("id") int dni) {
		boolean eliminado = alumnoSercive.eliminarAlumnoPorDni(dni);
		if(eliminado) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
