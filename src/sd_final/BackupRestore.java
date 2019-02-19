/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ACER
 */
class BackupRestore {
    //Parametro 1 -> BD alterada, Parametro 2 -> BD buena

    public static void restore(Connection c1, Connection c2) {
        int MAXidBloque = 0, MAXidTransaccion = 0;
        try {
//Obtiene la cantidad de bloques
            Statement st1 = c1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
            
            while (rs1.next()) {
                MAXidBloque = rs1.getInt(1);
            }
            System.out.println(MAXidBloque);
//Obtiene la cantidad de transacciones
            rs1 = st1.executeQuery("SELECT MAX(id_transaccion) FROM transacciones");
            while (rs1.next()) {
                MAXidTransaccion = rs1.getInt(1);
            }
            Statement s2 = c2.createStatement();
            PreparedStatement s1 = null;
//Recupera el bloque
            ResultSet rs = s2.executeQuery("SELECT * FROM bloques");
            while (rs.next()) {
                if (MAXidBloque == 0) {
                    s1 = c1.prepareStatement("insert into bloques(id_bloque, hash_merkle, timestamp, previushash, nonce, hash_bloque, cant_transacciones) values('" + rs.getInt(1) + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getString(4) + "','" + rs.getInt(5) + "','" + rs.getString(6) + "','" + rs.getInt(7) + "')");
                } else {
                    s1 = c1.prepareStatement("UPDATE bloques SET hash_merkle='" + rs.getString(2) + "', timestamp='" + rs.getString(3) + "', previushash='" + rs.getString(4) + "', nonce='" + rs.getInt(5) + "', hash_bloque='" + rs.getString(6) + "', cant_transacciones='" + rs.getInt(7) + "' WHERE id_bloque=" + rs.getInt(1));
                }
                s1.executeUpdate();
            }
//Obtiene la cantidad de bloques nuevamente
            st1 = c1.createStatement();
            rs1 = st1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
            while (rs1.next()) {
                MAXidBloque = rs1.getInt(1);
            }
            System.out.println(MAXidBloque);
//Recupera la transaccion
            rs = s2.executeQuery("SELECT * FROM transacciones");
            while (rs.next()) {
                if (MAXidTransaccion == 0 && MAXidBloque == 0) {
                    s1 = c1.prepareStatement("insert into transacciones(id_transaccion, address_origen, address_destino, hash_transaccion) values('" + rs.getInt(1) + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getString(5) + "')");
//inserta sin id_bloque
                }
                if (MAXidTransaccion == 0 && MAXidBloque > 0) {
                    s1 = c1.prepareStatement("insert into transacciones(id_transaccion, address_origen, address_destino, id_bloque, hash_transaccion) values('" + rs.getInt(1) + "','" + rs.getString(2) + "','" + rs.getString(3) + "','" + rs.getInt(4) + "','" + rs.getString(5) + "')");
//inserta con id_bloque
                }
                if (MAXidTransaccion > 0 && MAXidBloque == 0) {
                    
                    s1 = c1.prepareStatement("UPDATE transacciones SET address_origen='" + rs.getString(2) + "', address_destino='" + rs.getString(3) + "', hash_transaccion='" + rs.getString(5) + "' WHERE id_transaccion=" + rs.getInt(1));
//actualiza si id_bloque
                }
                if (MAXidTransaccion > 0 && MAXidBloque > 0) {
                    s1 = c1.prepareStatement("UPDATE transacciones SET address_origen='" + rs.getString(2) + "', address_destino='" + rs.getString(3) + "', id_bloque=" + rs.getInt(4) + ", hash_transaccion='" + rs.getString(5) + "' WHERE id_transaccion=" + rs.getInt(1));
//actualiza con id_bloque
                }
                s1.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BackupRestore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
