package com.coderhouse.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.coderhouse.entidades.Alumno;
import com.coderhouse.entidades.Curso;

public class JavaDataBaseController {
	
	private static final String DATA_BASE = "coderhouse";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DATA_BASE;
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private Connection connection;
	
	public Connection getConnection() {
		if(connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("Coneccion exitosa a la base de datos: " + DATA_BASE);
			} catch(SQLException e) {
				System.err.println("Error al establecer la coneccion " + e.getMessage());
			}
		}
		
		return connection;
	}
	
	public void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
				System.out.println("Coneccion cerrada");
			} catch(SQLException e) {
				System.err.println("Error al cerrar la coneccion " + e.getMessage());
			}
		}
	}
	
	// METODOS ALUMNO
	
	public void mostrarAlumno() {
		Statement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT dni, nombre, apellido, legajo, id_curso FROM alumnos";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int dni = resultSet.getInt("dni");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				int legajo = resultSet.getInt("legajo");
				int id_curso = resultSet.getInt("id_curso");
				System.out.println("Alumno con DNI nro " + dni + " es " + nombre + " " + apellido
						+ ", su legajo es el nro: " + legajo + " y su curso es: " + id_curso);
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar el statement o el resultSet: " + e.getMessage());
			}
		}
 	}
	
	public void insertarAlumno(Alumno alumno) {
		PreparedStatement statement = null;
		String query = "INSERT INTO alumnos (dni, apellido, id_curso, legajo, nombre) VALUES (?, ?, ?, ?, ?)";
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, alumno.getDni());
			statement.setString(2, alumno.getApellido());
			statement.setInt(3, alumno.getIdCurso());
			statement.setInt(4, alumno.hashCode());
			statement.setString(5, alumno.getNombre());
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo ingresar el alumno: " + alumno.getNombre()
						+ " " + alumno.getApellido());
			}
			System.out.println(
					"El alumno " + alumno.getNombre() + " "
					+ " " + alumno.getApellido() + " fue insertado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
	public void modificarAlumno(int dni, String nuevoNombre, String nuevoApellido) {
		PreparedStatement statement = null;
		try {
			String query = "UPDATE alumnos SET nombre = ?, apellido = ? WHERE dni = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nuevoNombre);
			statement.setString(2, nuevoApellido);
			statement.setInt(3, dni);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo modificar el alumno con DNI: " + dni);
			}
			System.out.println(
					"El alumno con DNI " + dni + " fue modificado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
	public void eliminarAlumno(int dni) {
		PreparedStatement statement = null;
		try {
			String query = "DELETE FROM alumnos WHERE dni = ?";
			statement = connection.prepareStatement(query);
			statement.setDouble(1, dni);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo eliminar el alumno con DNI: " + dni);
			}
			System.out.println(
					"El alumno con DNI " + dni + " fue eliminado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
	public void mostrarAlumnoPorDNI(int dni) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM alumnos WHERE dni = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, dni);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int nroDni = resultSet.getInt("dni");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				int legajo = resultSet.getInt("legajo");
				System.out.println("Alumno con DNI nro " + nroDni + " es " + nombre + " " + apellido
						+ ", y su legajo es el nro: " + legajo);
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar el statement o el resultSet: " + e.getMessage());
			}
		}
	}

	// METODOS CURSO
	
	public void mostrarCurso() {
		Statement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT * FROM cursos";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id_curso = resultSet.getInt("id_curso");
				String titulo = resultSet.getString("titulo");
				String descripcion = resultSet.getString("descripcion");
				System.out.println("Curso con id: " + id_curso +
						"\nTitulo: " + titulo + ", Descripcion: " + descripcion);
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.out.println("Error al cerrar el statement o el resultSet: " + e.getMessage());
			}
		}
	}
	
	public void insertarCurso(Curso curso) {
		PreparedStatement statement = null;
		String query = "INSERT INTO cursos (titulo, descripcion) VALUES (?, ?)";
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, curso.getTitulo());
			statement.setString(2, curso.getDescripcion());
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo ingresar el curso: " + curso.getTitulo());
			}
			System.out.println(
					"El curso " + curso.getTitulo() + " fue insertado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
	public void modificarCurso(int id_curso, String titulo, String descripcion) {
		PreparedStatement statement = null;
		try {
			String query = "UPDATE cursos SET titulo = ?, descripcion = ? WHERE id_curso = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, titulo);
			statement.setString(2, descripcion);
			statement.setInt(3, id_curso);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo modificar el curso con id: " + id_curso);
			}
			System.out.println(
					"El curso con id " + id_curso + " fue modificado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
	public void eliminarCurso(int id_curso) {
		PreparedStatement statement = null;
		try {
			String query = "DELETE FROM cursos WHERE id_curso = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id_curso);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				throw new SQLException(
						"No se pudo eliminar el curso con id: " + id_curso);
			}
			System.out.println(
					"El curso con id " + id_curso + " fue eliminado correctamente");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				System.err.println("Error al cerrar el statement: " + e.getMessage());
			}
		}
	}
	
}
