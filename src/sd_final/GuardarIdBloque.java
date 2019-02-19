/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ACER
 */
class GuardarIdBloque {

    public String guardaridbloque(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4, int id_bloque, List lista) {
        PreparedStatement GIBpst1 = null, GIBpst2 = null, GIBpst3 = null, GIBpst4 = null;
//ArrayList<String> array = new ArrayList<>();
//System.out.println(lista.get(i));
        String respuesta = null;
        try {
            for (int i = 0; i < lista.size(); i++) {
                String sql = "UPDATE transacciones SET id_bloque = ? WHERE hash_transaccion='" + lista.get(i) + "'";
                GIBpst1 = CBD1.prepareStatement(sql);
                GIBpst1.setInt(1, id_bloque);
                int rs1 = GIBpst1.executeUpdate();
                if (rs1 > 0) {
                    respuesta = "Registro exitoso";
                }
                GIBpst2 = CBD2.prepareStatement(sql);
                GIBpst2.setInt(1, id_bloque);
                int rs2 = GIBpst2.executeUpdate();
                if (rs2 > 0) {
                    respuesta = "Registro exitoso";
                }
                GIBpst3 = CBD3.prepareStatement(sql);
                GIBpst3.setInt(1, id_bloque);
                int rs3 = GIBpst3.executeUpdate();
                if (rs3 > 0) {
                    respuesta = "Registro exitoso";
                }
                GIBpst4 = CBD4.prepareStatement(sql);
                GIBpst4.setInt(1, id_bloque);
                int rs4 = GIBpst4.executeUpdate();
                if (rs4 > 0) {
                    respuesta = "Registro exitoso";
                }
            }
        } catch (SQLException e) {
            System.out.print("Error en la conexion" + e);
        }
        
        return respuesta;
    }
}
