package com.coderhouse.controlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

import com.coderhouse.modelos.Curso;
import com.coderhouse.servicios.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Gestión de Cursos", description = "Endpoints para gestionar cursos")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Operation(summary = "Obtener lista de cursos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de cursos obtenida", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Curso>> listarCurso() {
		try {
			List<Curso> cursos = cursoService.listarCurso();
			return new ResponseEntity<>(cursos, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Mostrar cursos por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Curso encontrado correctamente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content) })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> mostrarCursoPorId(@PathVariable("id") int id) {
		try {
			Curso curso = cursoService.mostrarCursoPorId(id);
			if(curso != null) {
				return new ResponseEntity<>(curso, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Agregar un nuevo curso")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Curso agregado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PostMapping(value = "/agregar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> agregarAlumno(@RequestBody Curso curso) {
		cursoService.agregarCurso(curso);
		return new ResponseEntity<>(curso, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Eliminar un curso existente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Curso eliminado correctamente", content = @Content),
			@ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content) })
	@DeleteMapping(value = "/{id}/eliminar", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> eliminarCursoPorId(@PathVariable("id") int id) {
		try {
			cursoService.eliminarCursoPorId(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Editar un curso segun su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Curso editado correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)) }),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content) })
	@PutMapping(value = "/{id}/editar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> editarCursoPorId(@PathVariable("id") int id, @RequestBody Curso curso) {
		try {
			Curso cursoEditado = cursoService.editarCursoPorId(id, curso);
			return new ResponseEntity<>(cursoEditado, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
