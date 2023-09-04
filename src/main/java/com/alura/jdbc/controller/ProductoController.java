package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.DAO.ProductoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;
import java.util.Map;

public class ProductoController {
	private ProductoDAO productodao;

	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productodao = new ProductoDAO(factory.recuperaConexion());
	}

	public int modificar(String name, String description, Integer stock, Integer id) {
		return productodao.modificar(name, description, stock, id);
	}
	public int eliminar(Integer id){
		return productodao.eliminar(id);
	}
	public List<Producto> listar(){
		return productodao.listar();
	}
	public void guardar(Producto producto, Integer categoriaId){
		producto.setCategoriaId(categoriaId);
		productodao.guardar(producto);
	}
	public List<Producto> listar(Categoria categoria){
		return productodao.listar(categoria);
	}



		/*
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
	public List<Producto> listar(){
		// primero pasamos a cargar la conexion con la base de datos
		return productodao.listar();
		// crearemos los statement que son los q nos permiten crear consultas con la bases de datos



	}
	//
	//modificar el metodo guardar
	//
    /*
     public void guardar(Map<String, String> producto) throws SQLException {
		String nombre=producto.get("NAME");
		String description=producto.get("DESCRIPTION");
		Integer stock=Integer.valueOf(producto.get("STOCK"));
		Integer maxStock=50;
     */
	//ahora pasaremos de usar el map a usar una clase dentro de java como el caso
	// de usar producto creado directamente en java por lo que se tendran
	// que cambiar varias partes del codigo como lo andabamos manejando
	/*
	public void guardar(Producto producto){
		productodao.guardar(producto);
	}

	private static void ejecutarRegistro(Producto producto, PreparedStatement statement) throws SQLException {



		statement.setString(1, producto.getName());
		statement.setString(2, producto.getDescription());
		statement.setInt(3, producto.getStock());

		statement.execute();

		final ResultSet resultSet= statement.getGeneratedKeys();
		try (resultSet){
			while (resultSet.next()){
				producto.setId(resultSet.getInt(1));
				System.out.println(
						String.format("Fue insertado el producto id %s", producto));
			}
		}

	}
	*/
}
