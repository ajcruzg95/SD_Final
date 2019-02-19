/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLOCKS;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ACER
 */
public class ConexionBlockchain {/*
    es la que se encarga realizar la conexión de toda la cadena de bloques
    */

    private Connection conexBD1 = null;
    private Connection conexBD2 = null;
    private Connection conexBD3 = null;
    private Connection conexBD4 = null;
    private static final String BD1 = "jdbc:mysql://192.168.1.6/Blockchain";
    private static final String BD2 = "jdbc:mysql://192.168.1.6/Blockchain2";
    private static final String BD3 = "jdbc:mysql://192.168.1.6/Blockchain3";
    private static final String BD4 = "jdbc:mysql://192.168.1.6/Blockchain4";
    String[] BD = {BD1, BD2, BD3, BD4};

    public Connection conectarBD1() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexBD1 = (Connection) DriverManager.getConnection(BD1, "root", "");
            if (conexBD1 != null) {
                System.out.println("Conexion establecida con blockchain1" + BD1);
            }

        } catch (Exception e) {
            System.out.println("error en conectar BD1" + e);
        }
        return conexBD1;
    }

    public Connection conectarBD2() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexBD2 = (Connection) DriverManager.getConnection(BD2, "root", "");
            if (conexBD2 != null) {
                System.out.println("Conexion establecida con blockchain2" + BD2);
            }

        } catch (Exception e) {
            System.out.println("error en conectar BD2" + e);
        }
        return conexBD2;
    }

    public Connection conectarBD3() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexBD2 = (Connection) DriverManager.getConnection(BD3, "root", "");
            if (conexBD2 != null) {
                System.out.println("Conexion establecida con blockchain3" + BD3);
            }

        } catch (Exception e) {
            System.out.println("error en conectar BD3" + e);
        }
        return conexBD2;
    }

    public Connection conectarBD4() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexBD2 = (Connection) DriverManager.getConnection(BD4, "root", "");
            if (conexBD2 != null) {
                System.out.println("Conexion establecida con blockchain4" + BD4);
            }

        } catch (Exception e) {
            System.out.println("error en conectar BD4" + e);
        }
        return conexBD2;
    }

    public void DesconectarBD1() {
        conexBD1 = null;
        if (conexBD1 == null) {
            System.out.println("Conexion terminda BlockchainBD1");
        }
        
    }

    public void DesconectarBD2() {
        conexBD2 = null;
        if (conexBD2 == null) {
            System.out.println("Conexion terminda BlockchainBD2");
        }
    }

    public void DesconectarBD3() {
        conexBD3 = null;
        if (conexBD3 == null) {
            System.out.println("Conexion terminda BlockchainBD3");
        }
    }

    public void DesconectarBD4() {
        conexBD4 = null;
        if (conexBD4 == null) {
            System.out.println("Conexion terminda BlockchainBD4");
        }
    }
}


