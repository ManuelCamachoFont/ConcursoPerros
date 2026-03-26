package es.studium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Modelo {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/concursocanino";
	String usuario = "concurso";
	String clave = "Studium2025#";
	String sentenciaSQL = "";

	Connection connection = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	// Conexion

	public Connection conectar() {

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, usuario, clave);
			return connection;
		} catch (ClassNotFoundException cnfe) {

			System.out.println("Error de driver");
			return null;
		} catch (SQLException se) {

			System.out.println("Error de conexión");
			return null;
		}
	}

	public boolean desconectar(Connection conexion) {
		try {
			if (conexion != null) {

				conexion.close();
			}
			return true;
		} catch (SQLException se) {
			return false;
		}
	}

	// Funciones Perro

	public boolean puedeCrearPerro(int idDueno) throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT COUNT(*) FROM perros WHERE idDuenoFK = ?";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idDueno);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) < 3;
			}
			return false;
		} finally {
			desconectar(connection);
		}
	}

	public List<String> obtenerPerros() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT * FROM perros ORDER BY idPerro";
		List<String> listaPerros = new ArrayList<>();

		try

		{
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				listaPerros.add(rs.getInt("idPerro") + " | " + rs.getString("nombrePerro") + " | "
						+ rs.getString("razaPerro") + " | " + rs.getString("tamanoPerro") + " | "
						+ rs.getString("colorPerro") + " | " + rs.getInt("idDuenoFK"));
			}
			return listaPerros;
		}

		finally {

			desconectar(connection);

		}

	}

	public void crearPerro(String nombre, String raza, String tamano, String color, String rutaImagen, String dueno)
			throws ClassNotFoundException, SQLException, FileNotFoundException {

		String sentenciaSQL = "INSERT INTO perros (idPerro, nombrePerro, razaPerro, tamanoPerro, colorPerro, imagenPerro, idDuenoFK) VALUES (null, ?, ?, ?, ?, ?, ?)";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombre);
			ps.setString(2, raza);
			ps.setString(3, tamano);
			ps.setString(4, color);
			if (rutaImagen != null && !rutaImagen.isEmpty()) {
				File file = new File(rutaImagen);
				FileInputStream fis = new FileInputStream(file);
				ps.setBinaryStream(5, fis, (int) file.length());
			} else {
				ps.setNull(5, java.sql.Types.BLOB);
			}
			ps.setString(6, dueno);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}

	}

	public void modificarPerro(String nombre, String raza, String tamano, String color, String rutaImagen, int dueno,
			int idPerro) throws ClassNotFoundException, SQLException, FileNotFoundException {

		String sentenciaSQL = "UPDATE perros SET nombrePerro = ?, razaPerro = ?, tamanoPerro = ?, colorPerro = ?, imagenPerro = ?, idDuenoFK = ? WHERE idPerro = ?";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombre);
			ps.setString(2, raza);
			ps.setString(3, tamano);
			ps.setString(4, color);
			if (rutaImagen != null && !rutaImagen.isEmpty()) {
				File file = new File(rutaImagen);
				FileInputStream fis = new FileInputStream(file);
				ps.setBinaryStream(5, fis, (int) file.length());
			} else {
				ps.setNull(5, java.sql.Types.BLOB);
			}
			ps.setInt(6, dueno);
			ps.setInt(7, idPerro);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}

	}

	public void borrarP(int idPerro) throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "DELETE FROM perros WHERE idPerro = ?";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idPerro);
			ps.executeUpdate();

		} finally {
			desconectar(connection);

		}
	}

	public void puntuarPerro(int idPerro, float puntuacion) throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "UPDATE perros SET puntuacionPerro = ? WHERE idPerro = ?";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setFloat(1, puntuacion);
			ps.setInt(2, idPerro);
			ps.executeUpdate();
		} finally {
			desconectar(connection);
		}
	}

	public byte[] obtenerImagenPerro(int idPerro) throws SQLException {
		String sentenciaSQL = "SELECT imagenPerro FROM perros WHERE idPerro = ?";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idPerro);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getBytes("imagenPerro");
			}
			return null;
		} finally {
			desconectar(connection);
		}
	}

	// Funciones Dueño

	public boolean puedeCrearDueno() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT COUNT(*) FROM duenos";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) < 5;
			}
			return false;
		} finally {
			desconectar(connection);
		}
	}

	public List<String> obtenerDuenos() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT * FROM duenos";
		List<String> listaDuenos = new ArrayList<>();

		try

		{
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				listaDuenos.add(rs.getInt("idDueno") + " | " + rs.getString("nombreDueno") + " | "
						+ rs.getString("apellidosDueno"));
			}
			return listaDuenos;
		}

		finally {

			desconectar(connection);

		}

	}

	public String buscarD(int idDueno) throws ClassNotFoundException, SQLException {

		String resultado = null;
		String sentenciaSQL = "SELECT * FROM duenos WHERE idDueno = ?";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idDueno);
			rs = ps.executeQuery();

			if (rs.next()) {
				resultado = (rs.getInt("idDueno") + " | " + rs.getString("nombreDueno") + " | "
						+ rs.getString("apellidosDueno"));
			}

		} finally {
			desconectar(connection);
		}
		return resultado;

	}

	public void crearDueno(String nombre, String apellidos) throws ClassNotFoundException, SQLException {

		String sentenciaSQL = "INSERT INTO duenos (idDueno, nombreDueno, apellidosDueno) VALUES (null, ?, ?)";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}

	}

	public void modificarDueno(String nombre, String apellidos, int dueno) throws ClassNotFoundException, SQLException {

		String sentenciaSQL = "UPDATE duenos SET nombreDueno = ?, apellidosDueno = ? WHERE idDueno = ?";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setInt(3, dueno);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}

	}

	public void borrarD(int idDueno) throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "DELETE FROM duenos WHERE idDueno = ?";
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idDueno);
			ps.executeUpdate();

		} finally {
			desconectar(connection);

		}
	}

	// Funciones Jueces

	public List<String> obtenerJueces() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT * FROM jueces";
		List<String> listaJueces = new ArrayList<>();

		try

		{
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				listaJueces.add(rs.getInt("idJuez") + " | " + rs.getString("cargoJuez"));
			}
			return listaJueces;
		}

		finally {

			desconectar(connection);

		}

	}

	public String buscarJ(int idJuez) throws ClassNotFoundException, SQLException {

		String resultado = null;
		String sentenciaSQL = "SELECT * FROM jueces WHERE idJuez = ?";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setInt(1, idJuez);
			rs = ps.executeQuery();

			if (rs.next()) {
				resultado = (rs.getInt("idJuez") + " | " + rs.getString("nombreJuez") + " | "
						+ rs.getString("apellidosJuez"));
			}

		} finally {
			desconectar(connection);
		}
		return resultado;

	}

	public void modificarJuez(String nombre, String apellidos, int juez) throws ClassNotFoundException, SQLException {

		String sentenciaSQL = "UPDATE jueces SET nombreJuez = ?, apellidosJuez = ? WHERE idJuez = ?";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setInt(3, juez);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}

	}

	// Concurso
	public List<String> obtenerResultados() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "SELECT p.nombrePerro, p.puntuacionPerro, d.nombreDueno, d.apellidosDueno FROM perros p INNER JOIN duenos d ON p.idDuenoFK = d.idDueno ORDER BY p.puntuacionPerro DESC";
		List<String> listaResultados = new ArrayList<>();
		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				listaResultados.add(rs.getString("nombrePerro") + " | " + rs.getFloat("puntuacionPerro") + " | "
						+ rs.getString("nombreDueno") + " | " + rs.getString("apellidosDueno"));
			}
			return listaResultados;
		} finally {
			desconectar(connection);
		}
	}
	
	

	// BD
	public void reiniciarConcurso() throws ClassNotFoundException, SQLException {
		try {
			conectar();
			ps = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
			ps.executeUpdate();
			ps = connection.prepareStatement("TRUNCATE TABLE perros");
			ps.executeUpdate();
			ps = connection.prepareStatement("TRUNCATE TABLE duenos");
			ps.executeUpdate();
			ps = connection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
			ps.executeUpdate();
		} finally {
			desconectar(connection);
		}
	}

	public void reiniciarPuntuaciones() throws ClassNotFoundException, SQLException {
		String sentenciaSQL = "UPDATE perros SET puntuacionPerro = NULL";

		try {
			conectar();
			ps = connection.prepareStatement(sentenciaSQL);
			ps.executeUpdate();

		} finally {
			desconectar(connection);
		}
	}


}
