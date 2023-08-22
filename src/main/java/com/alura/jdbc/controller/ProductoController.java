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
		final Connection con = factory.recuperaConexion();
		try(con){
			final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET NAME=?"+
					",DESCRIPTION=?"+
					",STOCK=?"+
					" WHERE ID =?");
			try(statement){

				statement.setString(1,name);
				statement.setString(2,descripcion);
				statement.setInt(3,stock);
				statement.setInt(4,id);

				statement.execute();
				int updateCount = statement.getUpdateCount();


				return updateCount;
			}

		}
		// aca declaramos la conexion como final
		// esto evitara usar el con.close();

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
		final Connection con = new ConnectionFactory().recuperaConexion();
		// crearemos los statement que son los q nos permiten crear consultas con la bases de datos
		try(con){
			final PreparedStatement statement = con.prepareStatement("SELECT ID, NAME, DESCRIPTION, STOCK FROM PRODUCTO");
			// aca usamos execute para poder ejecutar la consulta de mostrar lo que hay en la tabla para poder visualizarla
			try(statement){
				statement.execute();
				ResultSet resultSet = statement.getResultSet();

				List<Map<String, String>> resultado=new ArrayList<>();
				// iteramos para agregar los resultados que nos da el .next al Map osea cada fila de datos de nuestra base de datos
				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("ID", String.valueOf(resultSet.getInt("ID")));
					fila.put("NAME", resultSet.getString("NAME"));
					fila.put("DESCRIPTION", resultSet.getString("DESCRIPTION"));
					fila.put("STOCK", String.valueOf(resultSet.getInt("STOCK")));
					resultado.add(fila);
				}
				return resultado;


			}
			// se remplaza por todo el try con.close();
		}

	}
	//
	//modificar el metodo guardar
	//
    public void guardar(Map<String, String> producto) throws SQLException {
		String nombre=producto.get("NAME");
		String description=producto.get("DESCRIPTION");
		Integer stock=Integer.valueOf(producto.get("STOCK"));
		Integer maxStock=50;

		final Connection con = new ConnectionFactory().recuperaConexion();
		//el uso de de la funcion setAutoCommit es para q si o si se agreguen los datos completos o no se agregue nada
		// es como tomar el control de la jdbc uno mismo a la antigua

		//y llamamos el try para que nos asegure de que la conexion se cerrara esto es de la version 9
		try(con){
			con.setAutoCommit(false);
			//preparamos el statement
			// y pasamos los ??? como los argumentos que se van a obtner para name, description y el stock
			// para cerrar el statment hacemos lo mismo

			final PreparedStatement statement= con.prepareStatement("INSERT INTO PRODUCTO (name, description, stock)"
					+"VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			try(statement){
					do {
						int cant = Math.min(stock, maxStock);
						ejecutarRegistro(statement, nombre, description, cant);
						stock -= maxStock;

					} while (stock > 0);
					con.commit();
				}catch (Exception e) {
					// el rollback permite si hay un error captado en este caso por el try catch entonces se cierra la conexion y se cancela
					con.rollback();
				}
			}
		}

	private static void ejecutarRegistro(PreparedStatement statement, String nombre, String description, Integer stock) throws SQLException {
			if (stock<50){
				throw new RuntimeException("error");
			}


		statement.setString(1, nombre);
		statement.setString(2, description);
		statement.setInt(3, stock);

		statement.execute();

		final ResultSet resultSet= statement.getGeneratedKeys();
		try (resultSet){
			while (resultSet.next()){
				System.out.println(
						String.format("Fue insertado el producto id %d", resultSet.getInt(1)));
			}
		}

	}

}
