package com.coderhouse;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coderhouse.entidades.Alumno;
import com.coderhouse.entidades.Curso;
import com.coderhouse.repositorios.AlumnoRepository;
import com.coderhouse.repositorios.CursoRepository;
//Spring
@SpringBootApplication
public class Workshop1Application implements CommandLineRunner {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	public static void main(String[] args){
		SpringApplication.run(Workshop1Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		mostrarMenu();
	}
	
	public void mostrarMenu() {
		try {
			Scanner entrada = new Scanner(System.in);
			int opcion = -1;
			do {
				try {
					System.out.println("Menu:\n"
							+ "1. Lista a todos los alumnos\n"
							+ "2. Agregar alumno\n"
							+ "3. Buscar alumno por DNI\n"
							+ "4. Modificar alumno por DNI\n"
							+ "5. Eliminar alumno por DNI\n"
							+ "6. Lista de todos los cursos\n"
							+ "7. Agregar cursos\n"
							+ "8. Buscar cursos por ID\n"
							+ "0. Salir\n");
					System.out.println("Ingresar opcion:");
					if (entrada.hasNextInt()) {
						opcion = entrada.nextInt();
						entrada.nextLine();
					} else {
						System.out.println("Entrada invalida. Ingrese un numero del menu");
						entrada.nextLine();
						continue;
					}
					switch(opcion) {
					case 1:
						listarTodosLosAlumnos();
						break;
					case 2:
						agregarAlumno();
						break;
					case 3:
						buscarAlumnoPorDNI();
						break;
					case 4:
						modificarAlumnoPorDni();
						break;
					case 5:
						eliminarAlumnoPorDNI();
						break;
					case 6:
						listarTodosLosCursos();
						break;
					case 7:
						agregarCursos();
						break;
					case 8:
						buscarCursoPorId();
						break;
					case 0:
						System.out.println("Saliendo del programa ...");
						break;
						default:
							System.out.println("Opcion invalida. Ingrese una opcion valida");
							break;
					}
				} catch(InputMismatchException e) {
					System.err.println("Error: Ingrese un numero valido");
					entrada.nextLine();
					opcion = -1;
				}
			} while(opcion != 0);
			entrada.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listarTodosLosAlumnos() {
		List<Alumno> listaAlumnos = alumnoRepository.findAll();
		if(listaAlumnos.isEmpty()) {
			System.out.println("No existen alumnos en la lista");
		} else {
			System.out.println("Lista de alumnos:");
			for (Alumno alumno : listaAlumnos) {
				System.out.println("Alumno con DNI nro"
									+ alumno.getDni()
									+ " se llama " + alumno.getNombre()
									+ " " + alumno.getApellido()
									+ " y asiste al curso de "
									+ alumno.getCurso().getTitulo());
			}
		}
		
	}

	public void agregarAlumno() {
		List<Curso> cursos = cursoRepository.findAll();
		if(cursos.isEmpty()) {
			System.out.println("No existen cursos para mostrar. Debe agregar al menos un curso");
		}
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		Alumno alumno = new Alumno();
		System.out.println("Ingrese el DNI del alumno:");
		alumno.setDni(entrada.nextInt());
		entrada.nextLine();
		System.out.println("Ingrese el nombre:");
		alumno.setNombre(entrada.nextLine());
		System.out.println("Ingrese el Apellido:");
		alumno.setApellido(entrada.nextLine());
		System.out.println("Cursos disponibles:");
		for (Curso curso : cursos) {
			System.out.println(" " + curso.getId_curso() + ". "+ curso.getTitulo());
		}
		int cursoId;
		Curso cursoSeleccionado = null;
		boolean cursoValido = false;
		while(!cursoValido) {
			try {
				System.out.println("Seleccione el ID del curso");
				cursoId = entrada.nextInt();
				cursoSeleccionado = cursoRepository.findById(cursoId).orElse(null);
				if(cursoSeleccionado != null ) {
					cursoValido = true;
				} else {
					System.out.println("El ID del curso seleccionado no es valido");
				}
			} catch(InputMismatchException e) {
				System.err.println("Error: Ingrese un numero valido");
				entrada.nextLine();
			}
		}
		alumno.setCurso(cursoSeleccionado);
		alumno.setLegajo((int) (Math.random() * 10000));
		alumnoRepository.save(alumno);
		System.out.println("Alumno guardado: " + alumno.getNombre()
							+ " " + alumno.getApellido()
							+ " y se inscribio en el curso "
							+ alumno.getCurso().getTitulo());
	}

	public void buscarAlumnoPorDNI() {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el DNI del alumno a buscar:");
		int dni = entrada.nextInt();
		Alumno alumno = alumnoRepository.findById(dni).orElse(null);
		if(alumno != null) {
			System.out.println("El alumno seleccionado es: " + alumno.getNombre()
								+ " " + alumno.getApellido()
								+ " y se inscribio en el curso "
								+ alumno.getCurso().getTitulo());
		} else {
			System.out.println("El alumno con DNI " + alumno.getDni() + " no fue encontrado");
		}
	}

	public void modificarAlumnoPorDni() {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el DNI del alumno a editar:");
		int dni = entrada.nextInt();
		Alumno alumno = alumnoRepository.findById(dni).orElse(null);
		List<Curso> cursos = cursoRepository.findAll();
		if(cursos.isEmpty()) {
			System.out.println("No existen cursos para mostrar. Debe agregar al menos un curso");
		}
		if(alumno != null) {
			System.out.println("El alumno encontrado es: " + alumno.getNombre()
								+ " " + alumno.getApellido()
								+ " y se inscribio al curso de "
								+ alumno.getCurso().getTitulo());
			System.out.println("Ingrese el nuevo nombre:");
			String nuevoNombre = entrada.next();
			alumno.setNombre(nuevoNombre);
			System.out.println("Ingrese el nuevo apellido:");
			String nuevoApellido = entrada.next();
			alumno.setApellido(nuevoApellido);
			System.out.println("Cursos disponibles:");
			for (Curso curso : cursos) {
				System.out.println(" " + curso.getId_curso() + ". "+ curso.getTitulo());
			}
			int cursoId;
			Curso cursoSeleccionado = null;
			boolean cursoValido = false;
			while(!cursoValido) {
				try {
					System.out.println("Seleccione el ID del curso");
					cursoId = entrada.nextInt();
					cursoSeleccionado = cursoRepository.findById(cursoId).orElse(null);
					if(cursoSeleccionado != null ) {
						cursoValido = true;
					} else {
						System.out.println("El ID del curso seleccionado no es valido");
					}
				} catch(InputMismatchException e) {
					System.err.println("Error: Ingrese un numero valido");
					entrada.nextLine();
				}
			}
			alumno.setCurso(cursoSeleccionado);
			alumnoRepository.save(alumno);
			System.out.println("Alumno modificado correctamente");
		} else {
			System.out.println("El alumno con DNI " + alumno.getDni() + " no fue encontrado");
		}
	}
	
	public void eliminarAlumnoPorDNI() {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el DNI del alumno a editar:");
		int dni = entrada.nextInt();
		Alumno alumno = alumnoRepository.findById(dni).orElse(null);
		if(alumno != null) {
			alumnoRepository.delete(alumno);
			System.out.println("El alumno fue eliminado exitosamente");
		} else {
			System.out.println("El alumno con DNI " + alumno.getDni() + " no fue encontrado");
		}
	}
	
	public void listarTodosLosCursos() {
		List<Curso> listaCursos = cursoRepository.findAll();
		if(listaCursos.isEmpty()) {
			System.out.println("No existen cursos en la lista");
		} else {
			System.out.println("Lista de cursos:");
			for (Curso curso : listaCursos) {
				System.out.println(curso.getId_curso() + ". Curso de "
									+ curso.getTitulo()
									+ ": " + curso.getDescripcion());
			}
		}
	}
	
	public void agregarCursos() {
		Curso curso = new Curso();
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el titulo del curso:");
		curso.setTitulo(entrada.nextLine());
		System.out.println("Ingrese la descripcion del curso:");
		curso.setDescripcion(entrada.nextLine());
		cursoRepository.save(curso);
		System.out.println("El curso " + curso.getTitulo() + " fue guardado exitosamente");
	}
	
	public void buscarCursoPorId() {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("Ingrese el ID del curso a buscar:");
		int id = entrada.nextInt();
		Curso curso = cursoRepository.findById(id).orElse(null);
		if(curso != null) {
			System.out.println("El curso seleccionado es: " + curso.getTitulo()
								+ " " + curso.getDescripcion());
			List<Alumno> listaAlumnos = curso.getAlumnos();
			System.out.println("Alumnos:");
			for (Alumno alumno : listaAlumnos) {
				System.out.println("- " + alumno.getNombre() + " " + alumno.getApellido());
			}
		} else {
			System.out.println("El curso con ID " + curso.getId_curso() + " no fue encontrado");
		}
	}
	
}
