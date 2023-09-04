package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }
    public void guardar(Producto producto) {
        //el uso de de la funcion setAutoCommit es para q si o si se agreguen los datos completos o no se agregue nada
        // es como tomar el control de la jdbc uno mismo a la antigua

        //y llamamos el try para que nos asegure de que la conexion se cerrara esto es de la version 9
        try{
            //preparamos el statement
            // y pasamos los ??? como los argumentos que se van a obtner para name, description y el stock
            // para cerrar el statment hacemos lo mismo

             PreparedStatement statement;
             statement=con.prepareStatement("INSERT INTO PRODUCTO (name, description, stock, categoria_id)"
                    +"VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            try(statement){

                statement.setString(1, producto.getName());
                statement.setString(2, producto.getDescription());
                statement.setInt(3, producto.getStock());
                statement.setInt(4,producto.getCategoriaId());

                statement.execute();

                final ResultSet resultSet=statement.getGeneratedKeys();
                try (resultSet){
                    while (resultSet.next()) {
                        producto.setId(resultSet.getInt(1));

                        System.out.println(String.format("Fue insertado el producto: %s", producto));
                    }
                }
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Producto> listar() {
        List<Producto> resultado=new ArrayList<>();
        try{
            final PreparedStatement statement = con.prepareStatement("SELECT ID, NAME, DESCRIPTION, STOCK FROM PRODUCTO");
            // aca usamos execute para poder ejecutar la consulta de mostrar lo que hay en la tabla para poder visualizarla
            try (statement) {
                statement.execute();
               final ResultSet resultSet = statement.getResultSet();

                // iteramos para agregar los resultados que nos da el .next al Map osea cada fila de datos de nuestra base de datos
            try(resultSet) {
                while (resultSet.next()) {
                    resultado.add(new Producto(resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getString("DESCRIPTION"),
                            resultSet.getInt("STOCK")));

                }


            }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
    public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int modificar(String name, String description, Integer stock, Integer id) {
        try {
            final PreparedStatement statement = con.prepareStatement(
                    "UPDATE PRODUCTO SET "
                            + " NAME = ?, "
                            + " DESCRIPTION = ?,"
                            + " STOCK = ?"
                            + " WHERE ID = ?");

            try (statement) {
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, stock);
                statement.setInt(4, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//DESCRIPCION
    public List<Producto> listar(Categoria categoria) {
        List<Producto> resultado=new ArrayList<>();
        try{
            String sql="SELECT ID, NAME, DESCRIPTION, STOCK "+
                    "FROM PRODUCTO WHERE CATEGORIA_ID=?";
            System.out.println(sql);
            final PreparedStatement statement =
                    con.prepareStatement(sql);
            // aca usamos execute para poder ejecutar la consulta de mostrar lo que hay en la tabla para poder visualizarla
            try (statement) {
                // asi hacemos la busqueda del producto por la categoria
                statement.setInt(1,categoria.getId());
                statement.execute();
                final ResultSet resultSet = statement.getResultSet();

                // iteramos para agregar los resultados que nos da el .next al Map osea cada fila de datos de nuestra base de datos
                try(resultSet) {
                    while (resultSet.next()) {
                        resultado.add(new Producto(
                                resultSet.getInt("ID"),
                                resultSet.getString("NAME"),
                                resultSet.getString("DESCRIPTION"),
                                resultSet.getInt("STOCK")));

                    }


                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

}
