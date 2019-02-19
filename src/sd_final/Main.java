/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sd_final;

import BLOCKS.CalcularHash;
import BLOCKS.ConexionBlockchain;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author ACER
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here

        ConexionBlockchain BD1 = new ConexionBlockchain();
        ConexionBlockchain BD2 = new ConexionBlockchain();
        ConexionBlockchain BD3 = new ConexionBlockchain();
        ConexionBlockchain BD4 = new ConexionBlockchain();
        Connection CBD1 = BD1.conectarBD1();
        Connection CBD2 = BD2.conectarBD2();
        Connection CBD3 = BD3.conectarBD3();
        Connection CBD4 = BD4.conectarBD4();
        ValidarBlockchain validar = new ValidarBlockchain();
        boolean[] ArrayBD = new boolean[5];
        Connection[] ArrayCO = new Connection[5];
        ArrayCO[1] = CBD1;
        ArrayCO[2] = CBD2;
        ArrayCO[3] = CBD3;
        ArrayCO[4] = CBD4;
        
        int band = 0;
        do {
            band = 0;
            ArrayBD[1] = validar.ValidarBD1(CBD1, CBD2, CBD3, CBD4);
            ArrayBD[2] = validar.ValidarBD2(CBD1, CBD2, CBD3, CBD4);
            ArrayBD[3] = validar.ValidarBD3(CBD1, CBD2, CBD3, CBD4);
            ArrayBD[4] = validar.ValidarBD4(CBD1, CBD2, CBD3, CBD4);
            System.out.println("------------------ validacion de bases de datos en trasacciones");
            System.out.println(ArrayBD[1]);
            System.out.println(ArrayBD[2]);
            System.out.println(ArrayBD[3]);
            System.out.println(ArrayBD[4]);
            if (ArrayBD[1] == false && ArrayBD[2] == false && ArrayBD[3] == false && ArrayBD[4] == false) {
//Obtiene todos los hashes de las transacciones y calcula el hash_merkle
                ListarTransaccionesBlockNULL TN = new ListarTransaccionesBlockNULL();
                CalcularHash calcularhash = new CalcularHash();
//Obtiene el # de transacciones para el bloque
                int num_transacciones = TN.ListaTransaccionesBloqueNULL(CBD1, CBD2, CBD3, CBD4).size();
                System.out.println("-----------lista transacciones---");
                System.out.println(num_transacciones);
                String hash_merkle = calcularhash.sha256(String.join("", TN.ListaTransaccionesBloqueNULL(CBD1, CBD2, CBD3, CBD4)));
//Obtiene la facha y hora atual del sistema
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String timestamp = time.toString();
                System.out.println("tiempo de creacion: "+timestamp);
//Obbtiene el previus hash
                Previushash PH = new Previushash();
                String previushash = PH.Previushash(CBD1, CBD2, CBD3, CBD4);
                
                System.out.println("______-previius");
                System.out.println(previushash);
//Obtiene un numero aleatorio(nonce)
                int nonce = (int) (Math.random() * 999999 + 6);
                System.out.println("------------NONCE");
                System.out.println(nonce);
                if (num_transacciones != 0) {//si existen 1 o mas nuevas transacciones, entra al proceso para crear el blque
                    if (previushash != null) {
                        //Concatena hash_merkle,previushash,timestamp,nonce y calcula el hash de todo

                        String hash_bloque = calcularhash.sha256(hash_merkle.concat(timestamp).concat(previushash).concat(Integer.toString(nonce)));
                        System.out.println("HashMerkle: " + hash_merkle);
                        System.out.println("TimeStamp: " + timestamp);
                        System.out.println("PreviusHash: " + previushash);
                        System.out.println("Nonce: " + nonce);
                        System.out.println("HashBloque: " + hash_bloque);
                        System.out.println("#Transacciones: " + num_transacciones);
                        GuardarBloque GB = new GuardarBloque();
//Guarda el bloque
                        String g = GB.guardarbloque(CBD1, CBD2, CBD3, CBD4, hash_merkle, timestamp, previushash, nonce, hash_bloque, num_transacciones);
                        System.out.println("--------Registro de bloque");
                        System.out.println(g);
                        if (g==("Registro exitoso")) {
//Obtiene el id del bloque que se guardó
                            ObtenerID OID = new ObtenerID();
                            int id = OID.obtenerid(CBD1, CBD2, CBD3, CBD4, previushash);
                            System.out.println("--------obtener ID");
                            System.out.println(id);
//Guarda el id del bloque en las transacciones
                            GuardarIdBloque GIB = new GuardarIdBloque();
                            GIB.guardaridbloque(CBD1, CBD2, CBD3, CBD4, id, TN.ListaTransaccionesBloqueNULL(CBD1, CBD2, CBD3, CBD4));
                            System.out.println("________Guardar Bloque ID");
                            System.out.println(g);
                            System.out.println("Id registrado: " + id);
                        }
                    } else {
                        System.out.println("Error al crear el bloque, error previushash");
                    }
                } else {
                    System.out.println("Error al crear el bloque, no existen nuevas transacciones");
                }
            } else {
                BackupRestore BR = new BackupRestore();
                if (ArrayBD[1] == true) {
                    System.out.println("Error en la validación de BD1, se recupera la cadena de bloques ");
                    for (int i = 1; i <= 4; i++) {
                        if (ArrayBD[i] == false) {
                            BR.restore(CBD1, ArrayCO[i]);
                            band = 1;
                            break;
                        }
                    }
                }
                if (ArrayBD[2] == true) {
                    System.out.println("Error en la validación de BD2, se recupera la cadena de bloques ");
                    for (int i = 1; i <= 4; i++) {
                        if (ArrayBD[i] == false) {
                            BR.restore(CBD2, ArrayCO[i]);
                            band = 1;
                            
                            break;
                        }
                    }
                }
                if (ArrayBD[3] == true) {
                    System.out.println("Error en la validación de BD3, se recupera la cadena de bloques ");
                    for (int i = 1; i <= 4; i++) {
                        if (ArrayBD[i] == false) {
                            BR.restore(CBD3, ArrayCO[i]);
                            band = 1;
                            break;
                        }
                    }
                }
                if (ArrayBD[4] == true) {
                    System.out.println("Error en la validación de BD4, se recupera la cadena de bloques ");
                    for (int i = 1; i <= 4; i++) {
                        if (ArrayBD[i] == false) {
                            BR.restore(CBD4, ArrayCO[i]);
                            band = 1;
                            break;
                        }
                    }
                }
            }
        } while (band == 1);
        BD1.DesconectarBD1();
        BD2.DesconectarBD2();
        BD3.DesconectarBD3();
        BD4.DesconectarBD4();
    }
}
