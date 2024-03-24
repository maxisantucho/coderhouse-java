package com.coderhouse.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.modelos.Curso;
import com.coderhouse.repositorios.CursoRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Curso>> listarCurso() {
		try {
			List<Curso> cursos = cursoRepository.findAll();
			return new ResponseEntity<>(cursos, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> mostrarCursoPorId(@PathVariable("id") int id) {
		try {
			Curso curso = cursoRepository.findById(id).orElse(null);
			if(curso != null) {
				return new ResponseEntity<>(curso, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(curso, HttpStatus.NOT_FOUND);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/agregar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> agregarAlumno(@RequestBody Curso curso) {
		cursoRepository.save(curso);
		return new ResponseEntity<>(curso, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}/eliminar", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> eliminarCursoPorId(@PathVariable("id") int id) {
		try {
			cursoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/{id}/modificar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curso> modificarCursoPorId(@PathVariable("id") int id, @RequestBody Curso nuevo) {
		try {
			Curso curso = cursoRepository.findById(id).orElse(null);
			curso.setTitulo(nuevo.getTitulo());
			curso.setDescripcion(nuevo.getDescripcion());
			cursoRepository.save(curso);
			return new ResponseEntity<>(curso, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
