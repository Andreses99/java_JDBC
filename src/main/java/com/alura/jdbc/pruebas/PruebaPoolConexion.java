package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class PruebaPoolConexion {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory conectionfactory=new ConnectionFactory();

        for (int i=0; i<20;i++){
            conectionfactory.recuperaConexion();
            System.out.println("abriendo conexion N-"+(i+1));

        }
    }

}
