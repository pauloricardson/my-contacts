package br.mycontacts.database;


import java.sql.Connection;
import java.sql.DriverManager;

public class Conection {
    private static final String URL =
            "jdbc:mysql://localhost:3306/agenda";

    private static final String USER = "root";
    private static final String PASSWORD = "6032";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            System.out.println("Erro na conexão");
            e.printStackTrace();
            return null;
        }
    }
}