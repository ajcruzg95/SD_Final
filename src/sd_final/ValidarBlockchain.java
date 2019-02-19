/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import BLOCKS.CalcularHash;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ACER
 */
class ValidarBlockchain {

    public boolean ValidarBD1(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) {
        String hash_calculado = null, hash_guardado;
        CalcularHash CH = new CalcularHash();
        boolean respuesta = false;
        List lista = new ArrayList();
        List lista2 = new ArrayList();
        Connection[] ArrayCO = new Connection[5];
        ArrayCO[1] = CBD1;
        ArrayCO[2] = CBD2;
        ArrayCO[3] = CBD3;
        ArrayCO[4] = CBD4;
        String hash_merkle_calculado = null, hash_merkle_guardado = null, hash_bloque_calculado = null, hash_bloque_guardado = null;
        try {
//valida el tamaño de la cadena de bloques
            Integer[] TMAX = new Integer[5];
            int c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_transaccion) FROM transacciones");
                    while (rs1.next()) {
                        TMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = TMAX[i];
                }
                TMAX[c] = TMAX[i];
            }
            Integer[] BMAX = new Integer[5];
            c = 0;
            for (int i = 1; i <= 4; i++) {
                try {

                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                    while (rs1.next()) {
                        BMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = BMAX[i];
                }
                BMAX[c] = BMAX[i];
            }
            for (int i = 1; i <= 4; i++) {
                if ((TMAX[1] != TMAX[i]) || (BMAX[1] != BMAX[i])) {
                    respuesta = true;
                    break;
                }
            }
            if (respuesta == false) {
//Valida transacciones
                try {
                    Statement s1 = CBD1.createStatement();
                    ResultSet rs = s1.executeQuery("SELECT id_transaccion, address_origen, address_destino, hash_transaccion FROM transacciones");
                    while (rs.next()) {
                        lista2.add(hash_calculado = CH.sha256(rs.getString(2).concat(rs.getString(3))));
                        hash_guardado = rs.getString(4);
                        if (hash_calculado.equals(hash_guardado)) {
                            respuesta = false;
                            System.out.println("Transacción número " + rs.getString(1) + " correcta");
                            System.out.println("holaa");
                        } else {
                            respuesta = true;
                            System.out.println("Transacción número " + rs.getString(1) + " alterada");
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD1 SIN CONEXIÓN");
                }
            }
            int id = 0;
            try {
                Statement s1 = CBD1.createStatement();
                ResultSet rs = s1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception ex) {

                System.out.println("Blockchain -> BD1 SIN CONEXIÓN");
            }
            if (respuesta == false) {//Si transacciones no han sido alteradas valida el bloque caso contrario no lo hace
//Valida Bloques
                for (int i = 1; i <= id; i++) {
                    try {
                        Statement s1 = CBD1.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_transaccion FROM transacciones WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            lista.add(rs.getString(1));
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD1 SIN CONEXIÓN");
                    }
                    hash_merkle_calculado = CH.sha256(String.join("", lista));
                    try {
                        Statement s1 = CBD1.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_merkle, timestamp, previushash, nonce, hash_bloque FROM bloques WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            hash_merkle_guardado = rs.getString(1);
                            hash_bloque_calculado = CH.sha256(rs.getString(1).concat(rs.getString(2)).concat(rs.getString(3)).concat(rs.getString(4)));
                            hash_bloque_guardado = rs.getString(5);
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD1 SIN CONEXIÓN");
                    }
                    if (hash_merkle_calculado.equals(hash_merkle_guardado)) {
                        respuesta = false;
                        System.out.println("Hash de Merkle número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash de Merkle número " + i + " alterado");
                        break;
                    }
                    if (hash_bloque_calculado.equals(hash_bloque_guardado)) {
                        respuesta = false;
                        System.out.println("Hash del bloque número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash del bloque número " + i + " alterado");
                        break;
                    }
                    lista.clear();

                }
            }
            if (respuesta == false) {
//System.out.println(lista2);
                String[] hash_guardado1 = new String[5];
                for (int i = 1; i <= 4; i++) {
                    int j = 0;
                    try {
                        Statement s1 = ArrayCO[i].createStatement();
                        ResultSet rs = s1.executeQuery("SELECT id_transaccion, hash_transaccion FROM transacciones");
                        while (rs.next()) {
                            hash_guardado1[i] = rs.getString(2);
                            if (lista2.get(j).equals(hash_guardado1[i])) {
                                respuesta = false;
                                System.out.println("Transacción número " + rs.getString(1) + " correcta");
                                System.out.println("hola: "+lista.get(j));
                                System.out.println("hola: "+hash_guardado1[i]);
                            } else {
                                System.out.println("Transacción número " + rs.getString(1) + " alterada");
                                System.out.println("Blockchain -> BD1 ALTERADA");
                                System.out.println("22: "+lista.get(j));
                                System.out.println("22: "+hash_guardado1[i]);
                                return true;
                            }
                            j++;
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Blockchain -> BD1 SIN CONEXIÓN");
            return false;
        }
        System.out.println(respuesta);
        if (respuesta == false) {
            System.out.println("Blockchain -> BD1 CORRECTA");
        } else {
            System.out.println("Blockchain -> BD1 ALTERADA");
        }
        return respuesta;
    }

    public boolean ValidarBD2(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) {
        String hash_calculado = null, hash_guardado;
        CalcularHash CH = new CalcularHash();
        boolean respuesta = false;
        List lista = new ArrayList();

        List lista2 = new ArrayList();
        Connection[] ArrayCO = new Connection[5];
        ArrayCO[1] = CBD1;
        ArrayCO[2] = CBD2;
        ArrayCO[3] = CBD3;
        ArrayCO[4] = CBD4;
        String hash_merkle_calculado = null, hash_merkle_guardado = null, hash_bloque_calculado = null, hash_bloque_guardado = null;
        try {
//valida el tamaño de la cadena de bloques
            Integer[] TMAX = new Integer[5];
            int c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_transaccion) FROM transacciones");
                    while (rs1.next()) {
                        TMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = TMAX[i];
                }
                TMAX[c] = TMAX[i];
            }
            Integer[] BMAX = new Integer[5];
            c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                    while (rs1.next()) {
                        BMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = BMAX[i];
                }
                BMAX[c] = BMAX[i];
            }
            for (int i = 1; i <= 4; i++) {
                if ((TMAX[2] != TMAX[i]) || (BMAX[2] != BMAX[i])) {
                    respuesta = true;
                    break;
                }
            }
            if (respuesta == false) {
                //Valida transacciones

                try {
                    Statement s1 = CBD2.createStatement();
                    ResultSet rs = s1.executeQuery("SELECT id_transaccion, address_origen, address_destino, hash_transaccion FROM transacciones");
                    while (rs.next()) {
                        lista2.add(hash_calculado = CH.sha256(rs.getString(2).concat(rs.getString(3))));
                        hash_guardado = rs.getString(4);
                        if (hash_calculado.equals(hash_guardado)) {
                            respuesta = false;
                            System.out.println("Transacción número " + rs.getString(1) + " correcta");
                        } else {
                            respuesta = true;
                            System.out.println("Transacción número " + rs.getString(1) + " alterada");
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD2 SIN CONEXIÓN");
                }
            }
            int id = 0;
            try {
                Statement s1 = CBD2.createStatement();
                ResultSet rs = s1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception ex) {
                System.out.println("Blockchain -> BD2 SIN CONEXIÓN");
            }
            if (respuesta == false) {//Si transacciones no han sido alteradas valida el bloque caso contrario no lo hace
//Valida Bloques
                for (int i = 1; i <= id; i++) {
                    try {
                        Statement s1 = CBD2.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_transaccion FROM transacciones WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            lista.add(rs.getString(1));
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD2 SIN CONEXIÓN");
                    }
                    hash_merkle_calculado = CH.sha256(String.join("", lista));
                    try {
                        Statement s1 = CBD2.createStatement();

                        ResultSet rs = s1.executeQuery("SELECT hash_merkle, timestamp, previushash, nonce, hash_bloque FROM bloques WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            hash_merkle_guardado = rs.getString(1);
                            hash_bloque_calculado = CH.sha256(rs.getString(1).concat(rs.getString(2)).concat(rs.getString(3)).concat(rs.getString(4)));
                            hash_bloque_guardado = rs.getString(5);
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD2 SIN CONEXIÓN");
                    }
                    if (hash_merkle_calculado.equals(hash_merkle_guardado)) {
                        respuesta = false;
                        System.out.println("Hash de Merkle número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash de Merkle número " + i + " alterado");
                        break;
                    }
                    if (hash_bloque_calculado.equals(hash_bloque_guardado)) {
                        respuesta = false;
                        System.out.println("Hash del bloque número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash del bloque número " + i + " alterado");
                        break;
                    }
                    lista.clear();
                }
            }
            if (respuesta == false) {
//System.out.println(lista2);
                String[] hash_guardado1 = new String[5];
                for (int i = 1; i <= 4; i++) {
                    int j = 0;
                    try {
                        Statement s1 = ArrayCO[i].createStatement();
                        ResultSet rs = s1.executeQuery("SELECT id_transaccion, hash_transaccion FROM transacciones");
                        while (rs.next()) {
                            hash_guardado1[i] = rs.getString(2);
                            if (lista2.get(j).equals(hash_guardado1[i])) {
                                respuesta = false;
                                System.out.println("Transacción número " + rs.getString(1) + " correcta");
                            } else {
                                System.out.println("Transacción número " + rs.getString(1) + " alterada");

                                System.out.println("Blockchain -> BD2 ALTERADA");
                                return true;
                            }
                            j++;
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Blockchain -> BD2 SIN CONEXIÓN");
            return false;
        }
        System.out.println(respuesta);
        if (respuesta == false) {
            System.out.println("Blockchain -> BD2 CORRECTA");
        } else {
            System.out.println("Blockchain -> BD2 ALTERADA");
        }
        return respuesta;
    }

    public boolean ValidarBD3(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) {
        String hash_calculado = null, hash_guardado;
        CalcularHash CH = new CalcularHash();
        boolean respuesta = false;
        List lista = new ArrayList();
        List lista2 = new ArrayList();
        Connection[] ArrayCO = new Connection[5];
        ArrayCO[1] = CBD1;
        ArrayCO[2] = CBD2;
        ArrayCO[3] = CBD3;
        ArrayCO[4] = CBD4;
        String hash_merkle_calculado = null, hash_merkle_guardado = null, hash_bloque_calculado = null, hash_bloque_guardado = null;
        try {
//valida el tamaño de la cadena de bloques
            Integer[] TMAX = new Integer[5];
            int c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_transaccion) FROM transacciones");
                    while (rs1.next()) {
                        TMAX[i] = rs1.getInt(1);
                    }

                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = TMAX[i];
                }
                TMAX[c] = TMAX[i];
            }
            Integer[] BMAX = new Integer[5];
            c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                    while (rs1.next()) {
                        BMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = BMAX[i];
                }
                BMAX[c] = BMAX[i];
            }
            for (int i = 1; i <= 4; i++) {
                if ((TMAX[3] != TMAX[i]) || (BMAX[3] != BMAX[i])) {
                    respuesta = true;
                    break;
                }
            }
            if (respuesta == false) {
//Valida transacciones
                try {
                    Statement s1 = CBD3.createStatement();
                    ResultSet rs = s1.executeQuery("SELECT id_transaccion, address_origen, address_destino, hash_transaccion FROM transacciones");
                    while (rs.next()) {
                        lista2.add(hash_calculado = CH.sha256(rs.getString(2).concat(rs.getString(3))));
                        hash_guardado = rs.getString(4);
                        if (hash_calculado.equals(hash_guardado)) {
                            respuesta = false;
                            System.out.println("Transacción número " + rs.getString(1) + " correcta");
                        } else {
                            respuesta = true;
                            System.out.println("Transacción número " + rs.getString(1) + " alterada");
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD3 SIN CONEXIÓN");
                }

            }
            int id = 0;
            try {
                Statement s1 = CBD3.createStatement();
                ResultSet rs = s1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception ex) {
                System.out.println("Blockchain -> BD3 SIN CONEXIÓN");
            }
            if (respuesta == false) {//Si transacciones no han sido alteradas valida el bloque caso contrario no lo hace
//Valida Bloques
                for (int i = 1; i <= id; i++) {
                    try {
                        Statement s1 = CBD3.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_transaccion FROM transacciones WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            lista.add(rs.getString(1));
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD3 SIN CONEXIÓN");
                    }
                    hash_merkle_calculado = CH.sha256(String.join("", lista));
                    try {
                        Statement s1 = CBD3.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_merkle, timestamp, previushash, nonce, hash_bloque FROM bloques WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            hash_merkle_guardado = rs.getString(1);
                            hash_bloque_calculado = CH.sha256(rs.getString(1).concat(rs.getString(2)).concat(rs.getString(3)).concat(rs.getString(4)));
                            hash_bloque_guardado = rs.getString(5);
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD3 SIN CONEXIÓN");
                    }
                    if (hash_merkle_calculado.equals(hash_merkle_guardado)) {
                        respuesta = false;
                        System.out.println("Hash de Merkle número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash de Merkle número " + i + " alterado");
                        break;
                    }

                    if (hash_bloque_calculado.equals(hash_bloque_guardado)) {
                        respuesta = false;
                        System.out.println("Hash del bloque número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash del bloque número " + i + " alterado");
                        break;
                    }
                    lista.clear();
                }
            }
            if (respuesta == false) {
//System.out.println(lista2);
                String[] hash_guardado1 = new String[5];
                for (int i = 1; i <= 4; i++) {
                    int j = 0;
                    try {
                        Statement s1 = ArrayCO[i].createStatement();
                        ResultSet rs = s1.executeQuery("SELECT id_transaccion, hash_transaccion FROM transacciones");
                        while (rs.next()) {
                            hash_guardado1[i] = rs.getString(2);
                            if (lista2.get(j).equals(hash_guardado1[i])) {
                                respuesta = false;
                                System.out.println("Transacción número " + rs.getString(1) + " correcta");
                            } else {
                                System.out.println("Transacción número " + rs.getString(1) + " alterada");
                                System.out.println("Blockchain -> BD3 ALTERADA");
                                return true;
                            }
                            j++;
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Blockchain -> BD3 SIN CONEXIÓN");
            return false;
        }
        System.out.println(respuesta);
        if (respuesta == false) {
            System.out.println("Blockchain -> BD3 CORRECTA");
        } else {
            System.out.println("Blockchain -> BD3 ALTERADA");
        }

        return respuesta;
    }

    public boolean ValidarBD4(Connection CBD1, Connection CBD2, Connection CBD3, Connection CBD4) {
        String hash_calculado = null, hash_guardado;
        CalcularHash CH = new CalcularHash();
        boolean respuesta = false;
        List lista = new ArrayList();
        List lista2 = new ArrayList();
        Connection[] ArrayCO = new Connection[5];
        ArrayCO[1] = CBD1;
        ArrayCO[2] = CBD2;
        ArrayCO[3] = CBD3;
        ArrayCO[4] = CBD4;
        String hash_merkle_calculado = null, hash_merkle_guardado = null, hash_bloque_calculado = null, hash_bloque_guardado = null;
        try {
//valida el tamaño de la cadena de bloques
            Integer[] TMAX = new Integer[5];
            int c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_transaccion) FROM transacciones");
                    while (rs1.next()) {
                        TMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = TMAX[i];
                }
                TMAX[c] = TMAX[i];
            }
            Integer[] BMAX = new Integer[5];
            c = 0;
            for (int i = 1; i <= 4; i++) {
                try {
                    Statement a = ArrayCO[i].createStatement();
                    ResultSet rs1 = a.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                    while (rs1.next()) {
                        BMAX[i] = rs1.getInt(1);
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    c = BMAX[i];
                }
                BMAX[c] = BMAX[i];

            }
            for (int i = 1; i <= 4; i++) {
                if ((TMAX[4] != TMAX[i]) || (BMAX[4] != BMAX[i])) {
                    respuesta = true;
                    break;
                }
            }
            if (respuesta == false) {
//Valida transacciones
                try {
                    Statement s1 = CBD4.createStatement();
                    ResultSet rs = s1.executeQuery("SELECT id_transaccion, address_origen, address_destino, hash_transaccion FROM transacciones");
                    while (rs.next()) {
                        lista2.add(hash_calculado = CH.sha256(rs.getString(2).concat(rs.getString(3))));
                        hash_guardado = rs.getString(4);
                        if (hash_calculado.equals(hash_guardado)) {
                            respuesta = false;
                            System.out.println("Transacción número " + rs.getString(1) + " correcta");
                        } else {
                            respuesta = true;
                            System.out.println("Transacción número " + rs.getString(1) + " alterada");
                            break;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Blockchain -> BD4 SIN CONEXIÓN");
                }
            }
            int id = 0;
            try {
                Statement s1 = CBD4.createStatement();
                ResultSet rs = s1.executeQuery("SELECT MAX(id_bloque) FROM bloques");
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            } catch (Exception ex) {
                System.out.println("Blockchain -> BD4 SIN CONEXIÓN");
            }
            if (respuesta == false) {//Si transacciones no han sido alteradas valida el bloque caso contrario no lo hace
//Valida Bloques
                for (int i = 1; i <= id; i++) {
                    try {
                        Statement s1 = CBD4.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_transaccion FROM transacciones WHERE id_bloque='" + i + "'");
                        while (rs.next()) {

                            lista.add(rs.getString(1));
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD4 SIN CONEXIÓN");
                    }
                    hash_merkle_calculado = CH.sha256(String.join("", lista));
                    try {
                        Statement s1 = CBD4.createStatement();
                        ResultSet rs = s1.executeQuery("SELECT hash_merkle, timestamp, previushash, nonce, hash_bloque FROM bloques WHERE id_bloque='" + i + "'");
                        while (rs.next()) {
                            hash_merkle_guardado = rs.getString(1);
                            hash_bloque_calculado = CH.sha256(rs.getString(1).concat(rs.getString(2)).concat(rs.getString(3)).concat(rs.getString(4)));
                            hash_bloque_guardado = rs.getString(5);
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD4 SIN CONEXIÓN");
                    }
                    if (hash_merkle_calculado.equals(hash_merkle_guardado)) {
                        respuesta = false;
                        System.out.println("Hash de Merkle número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash de Merkle número " + i + " alterado");
                        break;
                    }
                    if (hash_bloque_calculado.equals(hash_bloque_guardado)) {
                        respuesta = false;
                        System.out.println("Hash del bloque número " + i + " correcto");
                    } else {
                        respuesta = true;
                        System.out.println("Hash del bloque número " + i + " alterado");
                        break;
                    }
                    lista.clear();
                }
            }
            if (respuesta == false) {
//System.out.println(lista2);
                String[] hash_guardado1 = new String[5];
                for (int i = 1; i <= 4; i++) {
                    int j = 0;
                    try {
                        Statement s1 = ArrayCO[i].createStatement();
                        ResultSet rs = s1.executeQuery("SELECT id_transaccion, hash_transaccion FROM transacciones");
                        while (rs.next()) {

                            hash_guardado1[i] = rs.getString(2);
                            if (lista2.get(j).equals(hash_guardado1[i])) {
                                respuesta = false;
                                System.out.println("Transacción número " + rs.getString(1) + " correcta");
                            } else {
                                System.out.println("Transacción número " + rs.getString(1) + " alterada");
                                System.out.println("Blockchain -> BD4 ALTERADA");
                                return true;
                            }
                            j++;
                        }
                    } catch (Exception ex) {
                        System.out.println("Blockchain -> BD" + i + " SIN CONEXIÓN");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Blockchain -> BD4 SIN CONEXIÓN");
            return false;
        }
        System.out.println(respuesta);
        if (respuesta == false) {
            System.out.println("Blockchain -> BD4 CORRECTA");
        } else {
            System.out.println("Blockchain -> BD4 ALTERADA");
        }
        return respuesta;
    }
}
