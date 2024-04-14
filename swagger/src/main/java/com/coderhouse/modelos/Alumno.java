package com.coderhouse.modelos;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Modelo alumno")
@Entity
@Table(name = "alumnos")
public class Alumno {
	
	@Schema(description = "DNI alumno", requiredMode = Schema.RequiredMode.REQUIRED, example = "3332453")
	@Id
	@Column(name = "dni")
	private int dni;
	@Schema(description = "Nombre alumno", requiredMode = Schema.RequiredMode.REQUIRED, example = "Maxi")
	@Column(name = "nombre")
	private String nombre;
	@Schema(description = "Apllido alumno", requiredMode = Schema.RequiredMode.REQUIRED, example = "Santucho")
	@Column(name = "apellido")
	private String apellido;
	@Schema(description = "Legajo alumno", requiredMode = Schema.RequiredMode.REQUIRED, example = "173")
	@Column(name = "legajo")
	private int legajo;
	
	@Schema(description = "Curso del alumno")
	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
	
	public Alumno() {
		
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(legajo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return legajo == other.legajo;
	}

}
