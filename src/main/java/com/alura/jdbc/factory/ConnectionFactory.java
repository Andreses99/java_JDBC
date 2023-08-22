package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private DataSource datasource;
    public ConnectionFactory() {
        var pooleDataSourse = new ComboPooledDataSource();
        pooleDataSourse.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
        pooleDataSourse.setUser("root");
        pooleDataSourse.setPassword("");
        pooleDataSourse.setMaxPoolSize(10);

        this.datasource=pooleDataSourse;
    }

    public Connection recuperaConexion() throws SQLException {
        System.out.println("si funciona");
        return this.datasource.getConnection();

    }
}
