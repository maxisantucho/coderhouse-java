package com.coderhouse.controladores;


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

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoSercive;
	
	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Alumno>> listarAlumnos() {
		try {
			List<Alumno> alumno = alumnoSercive.listarAlumno();
			return new ResponseEntity<>(alumno, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
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
	
	@PostMapping(value = "/agregar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Alumno> agregarAlumno(@RequestBody Alumno alumno) {
		alumnoSercive.agregarAlumno(alumno);
		return new ResponseEntity<>(alumno, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}/editar", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Alumno> editarAlumnoPorDni(@PathVariable("id") int dni, @RequestBody Alumno alumno) {
		Alumno alumnoEditado = alumnoSercive.editarAlumnoPorDni(dni, alumno);
		if(alumnoEditado != null) {
			return new ResponseEntity<>(alumno, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
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
