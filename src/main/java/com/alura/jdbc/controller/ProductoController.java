package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;
import java.util.Map;

public class ProductoController {

	public int modificar(String name, String descripcion,Integer stock,Integer id) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection con = factory.recuperaConexion();

		PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET");

		statement.execute("UPDATE PRODUCTO SET NAME=?"+
				",DESCRIPTION=?"+
				",STOCK=?"+
				" WHERE ID =?");
		statement.setString(1,name);
		statement.setString(2,descripcion);
		statement.setInt(3,stock);
		statement.setInt(4,id);

		statement.execute();
		int updateCount = statement.getUpdateCount();

		con.close();

		return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		// TODO
		Connection con = new ConnectionFactory().recuperaConexion();
		PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID= ?");
		statement.setInt(1,id);
		statement.execute();
		//la siguiente funcion nos dara un retorno de un valor entero de las filas q fueron modificadas
		return statement.getUpdateCount();
	}

	//para cargar los datos o listarlos usamos Map q nos listara
	public List<Map<String, String>> listar() throws SQLException {
		// primero pasamos a cargar la conexion con la base de datos
		Connection con = new ConnectionFactory().recuperaConexion();
		// crearemos los statement que son los q nos permiten crear consultas con la bases de datos
		PreparedStatement statement = con.prepareStatement("SELECT ID, NAME, DESCRIPTION, STOCK FROM PRODUCTO");
		// aca usamos execute para poder ejecutar la consulta de mostrar lo que hay en la tabla para poder visualizarla
		statement.execute();
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
		//preparamos el statement
		// y pasamos los ??? como los argumentos que se van a obtner para name, description y el stock
		PreparedStatement statement= con.prepareStatement("INSERT INTO PRODUCTO (name, description, stock)"
				+"VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
		statement.setString(1,producto.get("NAME"));
		statement.setString(2,producto.get("DESCRIPTION"));
		statement.setInt(3,Integer.valueOf(producto.get("STOCK")));

		statement.execute();

		ResultSet resultSet= statement.getGeneratedKeys();
		while (resultSet.next()){
			System.out.println(
					String.format("Fue insertado el producto id %d", resultSet.getInt(1)));
		}

	}

}
