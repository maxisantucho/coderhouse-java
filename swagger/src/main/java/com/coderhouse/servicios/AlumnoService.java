package com.coderhouse.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.coderhouse.modelos.Alumno;
import com.coderhouse.repositorios.AlumnoRepository;

@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	public List<Alumno> listarAlumno() {
		return alumnoRepository.findAll();
	}
	
	public Alumno mostrarAlumnoPorDni(int dni) {
		return alumnoRepository.findById(dni).orElse(null);
	}
	
	public Alumno agregarAlumno(Alumno alumno) {
		return alumnoRepository.save(alumno);
	}
	
	public Alumno editarAlumnoPorDni(int dni, Alumno alumno) {
		try {
			if(alumnoRepository.existsById(dni)) {
			alumno.setDni(dni);
			return alumnoRepository.save(alumno);
			}
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
		return null;
	}
	
	public boolean eliminarAlumnoPorDni(int dni) {
		try {
			alumnoRepository.deleteById(dni);
			return true;
		} catch(EmptyResultDataAccessException e) {
			return false;
		}
	}
	
}