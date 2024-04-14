package com.coderhouse.modelos;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Schema(description = "Modelo curso")
@Entity
@Table(name = "cursos")
public class Curso {
	
	@Schema(description = "ID curso", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
	@Id
	@Column(name = "id_curso")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_curso;
	@Schema(description = "Titulo curso", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java")
	@Column(name = "titulo")
	private String titulo;
	@Schema(description = "Descripcion curso", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java para principiantes")
	@Column(name = "descripcion")
	private String descripcion;
	
	@Schema(description = "Lista de alumno")
	@OneToMany(mappedBy = "curso")
	private List<Alumno> alumnos;
	
	public Curso() {
		
	}

	public int getId_curso() {
		return id_curso;
	}

	public void setId_curso(int id_curso) {
		this.id_curso = id_curso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}