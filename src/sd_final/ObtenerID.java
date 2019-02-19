/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
class ObtenerID {/*
    es la que se encarga de obtener id en base al 
    previushash asignado para posterior guardar el id de bloque en las transacciones
    */

    public int obtenerid(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4, String previushash) {
        int id = 0;
        Statement OIDst1 = null, OIDst2 = null, OIDst3 = null, OIDst4 = null;
        ResultSet OIDrs1 = null, OIDrs2 = null, OIDrs3 = null, OIDrs4 = null;
        String sql = "SELECT id_bloque FROM bloques WHERE previushash='" + previushash + "'";
        Integer[] ArrayID = new Integer[5];
        for (int i = 1; i <= 4; i++) {
            ArrayID[i] = 0;
        }
        try {
            OIDst1 = CBD1.createStatement();
            OIDrs1 = OIDst1.executeQuery(sql);
            while (OIDrs1.next()) {
                ArrayID[1] = OIDrs1.getInt(1);
            }
            OIDst2 = CBD2.createStatement();
            OIDrs2 = OIDst2.executeQuery(sql);
            while (OIDrs2.next()) {
                ArrayID[2] = OIDrs2.getInt(1);
            }
            OIDst3 = CBD3.createStatement();
            OIDrs3 = OIDst3.executeQuery(sql);
            while (OIDrs3.next()) {
                
                ArrayID[3] = OIDrs3.getInt(1);
            }
            OIDst4 = CBD4.createStatement();
            OIDrs4 = OIDst4.executeQuery(sql);
            while (OIDrs4.next()) {
                ArrayID[4] = OIDrs4.getInt(1);
            }
            for (int i = 1; i <= 4; i++) {
                if (ArrayID[i] != 0) {
                    id = ArrayID[i];
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.print("Error en la conexion" + e);
        }
        return id;
    }
}

