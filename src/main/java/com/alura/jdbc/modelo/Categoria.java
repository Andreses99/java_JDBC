package com.alura.jdbc.modelo;
import java.util.ArrayList;
import java.util.List;
public class Categoria {
    private Integer id;
    private String name;
    private List<Producto> productos = new ArrayList<>();

    public Categoria(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void agregar(Producto producto) {
        this.productos.add(producto);
    }

    public List<Producto> getProductos() {
        return this.productos;
    }
    @Override
    public String toString() {
        return this.name;
    }


}
