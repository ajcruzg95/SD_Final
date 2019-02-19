/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
class GuardarBloque {

    public String guardarbloque(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4, String hash_merkle, String timestamp, String previushash, int nonce, String hash_bloque, int cant_transacciones) {
        PreparedStatement GBpst1 = null, GBpst2 = null, GBpst3 = null, GBpst4 = null;
//ResultSet GBrs1 = null, GBrs2 = null, GBrs3 = null, GBrs4 = null;
        String sql = "insert into bloques(hash_merkle,timestamp,previushash,nonce,hash_bloque,cant_transacciones) values(?,?,?,?,?,?)";
        String respuesta = null;

        try {
            GBpst1 = CBD1.prepareStatement(sql);
            GBpst1.setString(1, hash_merkle);
            GBpst1.setString(2, timestamp);
            GBpst1.setString(3, previushash);
            GBpst1.setInt(4, nonce);
            GBpst1.setString(5, hash_bloque);
            GBpst1.setInt(6, cant_transacciones);
            int rs1 = GBpst1.executeUpdate();
            if (rs1 > 0) {
                respuesta = "Registro exitoso";
            }
            GBpst2 = CBD2.prepareStatement(sql);
            GBpst2.setString(1, hash_merkle);
            GBpst2.setString(2, timestamp);
            GBpst2.setString(3, previushash);
            GBpst2.setInt(4, nonce);
            GBpst2.setString(5, hash_bloque);
            GBpst2.setInt(6, cant_transacciones);
            int rs2 = GBpst2.executeUpdate();
            if (rs2 > 0) {
                respuesta = "Registro exitoso";
            }
            GBpst3 = CBD3.prepareStatement(sql);
            GBpst3.setString(1, hash_merkle);
            GBpst3.setString(2, timestamp);
            GBpst3.setString(3, previushash);
            GBpst3.setInt(4, nonce);
            GBpst3.setString(5, hash_bloque);
            GBpst3.setInt(6, cant_transacciones);
            int rs3 = GBpst3.executeUpdate();
            if (rs3 > 0) {
                respuesta = "Registro exitoso";
            }
            GBpst4 = CBD4.prepareStatement(sql);
            GBpst4.setString(1, hash_merkle);
            GBpst4.setString(2, timestamp);
            GBpst4.setString(3, previushash);
            GBpst4.setInt(4, nonce);
            GBpst4.setString(5, hash_bloque);
            GBpst4.setInt(6, cant_transacciones);
            int rs4 = GBpst4.executeUpdate();
            if (rs4 > 0) {
                respuesta = "Registro exitoso";
            }
        } catch (SQLException e) {
            System.out.print("Error en la conexion" + e);
        }
        return respuesta;
    }

}
