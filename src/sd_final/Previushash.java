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
class Previushash {

    public String Previushash(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) throws SQLException {
        Statement PBst1 = null, PBst2 = null, PBst3 = null, PBst4 = null;
        ResultSet PBrs1 = null, PBrs2 = null, PBrs3 = null, PBrs4 = null;
        String sql = "select hash_bloque FROM bloques order by id_bloque desc limit 1";
        String PB1 = null, PB2 = null, PB3 = null, PB4 = null;
        String[] ArrayPH = new String[5];
        try {
            PBst1 = CBD1.createStatement();
            PBrs1 = PBst1.executeQuery(sql);
            while (PBrs1.next()) {
                ArrayPH[1] = PBrs1.getString(1);
            }
            PBst2 = CBD2.createStatement();
            PBrs2 = PBst2.executeQuery(sql);
            
            while (PBrs2.next()) {
                ArrayPH[2] = PBrs2.getString(1);
            }
            PBst3 = CBD3.createStatement();
            PBrs3 = PBst3.executeQuery(sql);
            while (PBrs3.next()) {
                ArrayPH[3] = PBrs3.getString(1);
            }
            PBst4 = CBD4.createStatement();
            PBrs4 = PBst4.executeQuery(sql);
            while (PBrs4.next()) {
                ArrayPH[4] = PBrs4.getString(1);
            }
        } catch (SQLException e) {
            System.out.print("Error en la conexion" + e);
        }
        for (int i = 1; i <= 4; i++) {
            if (ArrayPH[i] != null) {
                return ArrayPH[i];
            }
        }
        return "0";
    }
}


