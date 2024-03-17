package com.coderhouse;

import com.coderhouse.controlador.JavaDataBaseController;
import com.coderhouse.entidades.Alumno;
import com.coderhouse.entidades.Curso;

public class MainTest {

	public static void main(String[] args) {
		
		JavaDataBaseController controller = new JavaDataBaseController();
		
		controller.getConnection();
		
		Alumno alumno1 = new Alumno();
		
		controller.insertarAlumno(alumno1);
		
		Curso curso1 = new Curso();
		Curso curso2 = new Curso();
		Curso curso3 = new Curso();
		
		curso1.setTitulo("Introduccion a JAVA");
		curso1.setDescripcion("Java esta genial");

		curso2.setTitulo("Spring boot");
		curso2.setDescripcion("Spring boot la rompe");
		
		controller.insertarCurso(curso1);
		controller.insertarCurso(curso2);
		
		controller.mostrarAlumno();
		
		curso3.setTitulo("Curso nuevo");
		curso3.setDescripcion("Curso de prueba para borrar");
		
		controller.insertarCurso(curso3);

		controller.modificarCurso(1, "Introduccion a JAVA", "Java esta genial, aguante Java loco");
		controller.modificarCurso(2, "Spring boot", "Spring boot la rompe papa");
		
		controller.eliminarCurso(3);
		
		controller.mostrarCurso();
		
		controller.closeConnection();
		
	}

}
