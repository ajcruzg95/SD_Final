/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import BLOCKS.ConexionBlockchain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ACER
 */
public class Logica_Ver {
    private  String cantidad []=new String[4];
    private ConexionBlockchain BD1 = new ConexionBlockchain();//conexion con la cadena de bloques
    private ConexionBlockchain BD2 = new ConexionBlockchain();
    private ConexionBlockchain BD3 = new ConexionBlockchain();//conexion con la cadena de bloques
    private ConexionBlockchain BD4 = new ConexionBlockchain();
    private Connection CBD1 = BD1.conectarBD1();
    private Connection CBD2 = BD2.conectarBD2();
    private Connection CBD3 = BD1.conectarBD3();
    private Connection CBD4 = BD2.conectarBD4();

    public void Cantidad(){
        String sql="select codigo from Persona";
        String sql2="select id_transaccion from Transacciones";
        try {
            Statement st1=CBD1.createStatement();
            ResultSet rs1=st1.executeQuery(sql);
            Statement st2=CBD1.createStatement();
            ResultSet rs2=st2.executeQuery(sql2);
            int cantidad_votantes=0;
            int cantidad_total=0;
            while(rs1.next()){
                cantidad_votantes=rs1.getInt(1);
            }
            while(rs2.next()){
                cantidad_total=rs2.getInt(1);
            }
            
            
            int cantidadtotal=cantidad_total-cantidad_votantes;
            cantidad[0]="cantidad total: "+cantidad_votantes+" cantidad votantes: "+cantidad_total;
            
        } catch (Exception e) {
            System.out.println(e);
            
        }
        

    }
    public void Lista1 (){
        String sql="select count(address_destino) from Transacciones where address_destino='Lista 1'";
        try {
            Statement st1=CBD1.createStatement();
            ResultSet rs1=st1.executeQuery(sql);
            int list1=0;
            while(rs1.next()){
               list1=rs1.getInt(1);
            }
            
            cantidad[1]=""+list1;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Lista2 (){
        String sql="select count(address_destino) from Transacciones where address_destino='Lista 2'";
        try {
            Statement st1=CBD1.createStatement();
            ResultSet rs1=st1.executeQuery(sql);
            int list2=0;
            while(rs1.next()){
                list2=rs1.getInt(1);
            }
            cantidad[2]=""+list2;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Nulo (){
        String sql="select count(address_destino) from Transacciones where address_destino='Nulo'";
        try {
            Statement st1=CBD1.createStatement();
            ResultSet rs1=st1.executeQuery(sql);
            int list1=0;
            while(rs1.next()){
                list1=rs1.getInt(1);
            }
            cantidad[3]=""+list1;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public String[] getCantidad(){
        Cantidad();
        Lista1();
        Lista2();
        Nulo();
        return cantidad;
        
    }
    
    
    
    
}
