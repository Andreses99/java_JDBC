package com.alura.jdbc.modelo;

public class Producto {

    private Integer id;
    private String name;
    private String description;
    private Integer stock;
    private Integer categoriaId;

    public Producto(String name, String description, Integer stock) {
        this.name = name;
        this.description = description;
        this.stock = stock;
    }

    public Producto(int id, String name, String description, int stock) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.stock=stock;
    }
    public Producto(int id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id=id;
    }
    public int getCategoriaId() {
        return this.categoriaId;
    }
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                '}';
    }



}
