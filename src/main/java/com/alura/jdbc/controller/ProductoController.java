package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;
import java.util.Map;

public class ProductoController {

	public void modificar(String name, String descripcion, Integer id, Integer stock) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statment = con.createStatement();

	}

	public int eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM PRODUCTO WHERE ID= "+id);
		//la siguiente funcion nos dara un retorno de un valor entero de las filas q fueron modificadas
		return statement.getUpdateCount();
	}

	//para cargar los datos o listarlos usamos Map q nos listara
	public List<Map<String, String>> listar() throws SQLException {
		// primero pasamos a cargar la conexion con la base de datos
		Connection con = new ConnectionFactory().recuperaConexion();
		// crearemos los statement que son los q nos permiten crear consultas con la bases de datos
		Statement statement = con.createStatement();
		// aca usamos execute para poder ejecutar la consulta de mostrar lo que hay en la tabla para poder visualizarla
		statement.execute("SELECT ID, NAME, DESCRIPTION, STOCK FROM PRODUCTO");
		ResultSet resultSet = statement.getResultSet();

		List<Map<String, String>> resultado=new ArrayList<>();
		// iteramos para agregar los resultados que nos da el .next al Map osea cada fila de datos de nuestra base de datos
		while (resultSet.next()){
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NAME", resultSet.getString("NAME"));
			fila.put("DESCRIPTION", resultSet.getString("DESCRIPTION"));
			fila.put("STOCK", String.valueOf(resultSet.getInt("STOCK")));
			resultado.add(fila);



		}
		con.close();
		return resultado;
	}
	//
	//modificar el metodo guardar
	//
    public void guardar(Map<String, String> producto) throws SQLException {

		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement= con.createStatement();
		statement.execute("INSERT INTO PRODUCTO (name, description, stock)"
						+"VALUES('"+ producto.get("NAME")+"','"
				+producto.get("DESCRIPTION")+"',"
				+producto.get("STOCK")+")",
				Statement.RETURN_GENERATED_KEYS);

		ResultSet resultSet= statement.getGeneratedKeys();
		while (resultSet.next()){
			System.out.println(
					String.format("Fue insertado el producto id %d", resultSet.getInt(1)));
		}

	}

}
