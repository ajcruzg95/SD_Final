/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;


import BLOCKS.CalcularHash;
import BLOCKS.ConexionBlockchain;
import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class LogicaVotacion {
    ConexionBlockchain BD1 = new ConexionBlockchain();//conexion con la cadena de bloques
    ConexionBlockchain BD2 = new ConexionBlockchain();
    ConexionBlockchain BD3 = new ConexionBlockchain();//conexion con la cadena de bloques
    ConexionBlockchain BD4 = new ConexionBlockchain();
    Connection CBD1 = BD1.conectarBD1();
    Connection CBD2 = BD2.conectarBD2();
    Connection CBD3 = BD1.conectarBD3();
    Connection CBD4 = BD2.conectarBD4();

    
    
    public void votar(String origen,String destino){
        int band=0;
        String respuesta="";
        String sql = "Insert Into Transacciones (address_origen,address_destino,hash_transaccion) "
                + "values (?,?,?)";
        String hash_transaccion="";
        CalcularHash calcular=new CalcularHash();
        try {
            PreparedStatement pst1 = CBD1.prepareStatement(sql);
            PreparedStatement pst2 = CBD2.prepareStatement(sql);
            PreparedStatement pst3 = CBD3.prepareStatement(sql);
            PreparedStatement pst4 = CBD4.prepareStatement(sql);

            pst1.setString(1, origen);
            pst1.setString(2, destino);
            hash_transaccion=calcular.sha256(hash_transaccion.concat(origen).concat(destino));
            pst1.setString(3, hash_transaccion);
            int rs1=pst1.executeUpdate();
            if(rs1>0){
                respuesta="registro exitoso";
                System.out.println(respuesta);
            }
            pst2.setString(1, origen);
            pst2.setString(2, destino);
            hash_transaccion=calcular.sha256(hash_transaccion.concat(origen).concat(destino));
            pst2.setString(3, hash_transaccion);
            int rs2=pst2.executeUpdate();
            if(rs2>0){
                respuesta="registro exitoso";
                System.out.println(respuesta);
            }
            
            pst3.setString(1, origen);
            pst3.setString(2, destino);
            hash_transaccion=calcular.sha256(hash_transaccion.concat(origen).concat(destino));
            pst3.setString(3, hash_transaccion);
            int rs3=pst3.executeUpdate();
            if(rs3>0){
                respuesta="registro exitoso";
                System.out.println(respuesta);
            }
            
            
            pst4.setString(1, origen);
            pst4.setString(2, destino);
            hash_transaccion=calcular.sha256(hash_transaccion.concat(origen).concat(destino));
            pst4.setString(3, hash_transaccion);
            int rs4=pst4.executeUpdate();
            if(rs4>0){
                respuesta="registro exitoso";
                System.out.println(respuesta);
            }
            

        } catch (Exception e) {
            System.out.println(e);

        }
    }
        public boolean Ingreso_validado(Persona p){
            String origen,empadronado;
            String sql="select address_origen From Transacciones ";
            String sql2="select address_empadronado From Persona";
            try {
                Statement st11=CBD1.createStatement();
                ResultSet rs11=st11.executeQuery(sql);
                Statement st12=CBD1.createStatement();
                ResultSet rs12=st12.executeQuery(sql);
                
                Statement st21=CBD2.createStatement();
                ResultSet rs21=st21.executeQuery(sql);
                Statement st22=CBD2.createStatement();
                ResultSet rs22=st22.executeQuery(sql);
                
                Statement st31=CBD3.createStatement();
                ResultSet rs31=st31.executeQuery(sql);
                Statement st32=CBD3.createStatement();
                ResultSet rs32=st32.executeQuery(sql);
                
                Statement st41=CBD4.createStatement();
                ResultSet rs41=st41.executeQuery(sql);
                Statement st42=CBD4.createStatement();
                ResultSet rs42=st42.executeQuery(sql);
                while(rs11.next()){
                    while(rs12.next()){
                        return true;
                    }
                }
                while(rs21.next()){
                    while(rs22.next()){
                        return true;
                    }
                }
                while(rs31.next()){
                    while(rs32.next()){
                        return true;
                    }
                }
                while(rs41.next()){
                    while(rs42.next()){
                        return true;
                    }
                }
                
            } catch (Exception e) {
                return false;
            }
            return false;
        }
}
    

