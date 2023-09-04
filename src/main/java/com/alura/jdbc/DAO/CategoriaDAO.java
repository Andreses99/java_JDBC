package com.alura.jdbc.DAO;

import com.alura.jdbc.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {

    public List<Categoria> listarConProductos;
    private Connection con;
    public CategoriaDAO(Connection con) {
        this.con=con;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado=new ArrayList<>();
        try {
            String sql="SELECT ID, NAME FROM CATEGORIA";
            System.out.println(sql);
            final PreparedStatement statement= con.prepareStatement(sql);
            try(statement){
                final ResultSet resultSet= statement.executeQuery();
                try(resultSet){
                    while (resultSet.next()){
                        resultado.add(new Categoria(
                                resultSet.getInt("ID"),
                                resultSet.getString("NAME")));
                    }
                };
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultado;
    }
    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            String sql = "SELECT C.ID, C.NAME, P.ID, P.NAME, P.STOCK "
                    + " FROM CATEGORIA C INNER JOIN PRODUCTO P "
                    + " ON C.ID = P.CATEGORIA_ID";

            System.out.println(sql);

            final PreparedStatement statement = con
                    .prepareStatement(sql);

            try (statement) {
                final ResultSet resultSet = statement.executeQuery();

                try (resultSet) {
                    while (resultSet.next()) {
                        int categoriaId = resultSet.getInt("C.ID");
                        String categoriaNombre = resultSet.getString("C.NAME");

                        Categoria categoria = resultado
                                .stream()
                                .filter(cat -> cat.getId().equals(categoriaId))
                                .findAny().orElseGet(() -> {
                                    Categoria cat = new Categoria(
                                            categoriaId, categoriaNombre);
                                    resultado.add(cat);

                                    return cat;
                                });

                        Producto producto = new Producto(
                                resultSet.getInt("P.ID"),
                                resultSet.getString("P.NAME"),
                                resultSet.getInt("P.STOCK"));

                        categoria.agregar(producto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}


























