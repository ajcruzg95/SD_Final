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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
class ListarTransaccionesBlockNULL {

    public List ListaTransaccionesBloqueNULL(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) {
        Statement LTst1 = null, LTst2 = null, LTst3 = null, LTst4 = null;
        ResultSet LTrs1 = null, LTrs2 = null, LTrs3 = null, LTrs4 = null;
        String sql = "SELECT hash_transaccion FROM transacciones WHERE id_bloque is null";
        List lst1 = new ArrayList();
        List lst2 = new ArrayList();
        List lst3 = new ArrayList();
        List lst4 = new ArrayList();
        List lstfinal = new ArrayList();
        try {
            LTst1 = CBD1.createStatement();
            LTrs1 = LTst1.executeQuery(sql);
            while (LTrs1.next()) {
                lst1.add(LTrs1.getString(1));
            }
            LTst2 = CBD2.createStatement();
            LTrs2 = LTst2.executeQuery(sql);
            while (LTrs2.next()) {
                lst2.add(LTrs2.getString(1));
            }
            LTst3 = CBD3.createStatement();
            LTrs3 = LTst3.executeQuery(sql);
            while (LTrs3.next()) {
                lst3.add(LTrs3.getString(1));
            }
            LTst4 = CBD4.createStatement();
            LTrs4 = LTst4.executeQuery(sql);
            while (LTrs4.next()) {
                lst4.add(LTrs4.getString(1));
                
            }
        } catch (SQLException e) {
            System.out.print("Error en la conexion" + e);
        }
        if (lst1.equals(lst2) && lst1.equals(lst3) && lst1.equals(lst4)) {
            return lst1;
        }
        return lst1;
    }
}
