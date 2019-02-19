/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;




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
public class LogicaPersona {
    
    ConexionBlockchain BD1 = new ConexionBlockchain();//conexion con la cadena de bloques
    ConexionBlockchain BD2 = new ConexionBlockchain();
    ConexionBlockchain BD3 = new ConexionBlockchain();//conexion con la cadena de bloques
    ConexionBlockchain BD4 = new ConexionBlockchain();
    Connection CBD1 = BD1.conectarBD1();
    Connection CBD2 = BD2.conectarBD2();
    Connection CBD3 = BD1.conectarBD3();
    Connection CBD4 = BD2.conectarBD4();

    public void InsertPersona(Persona p) {
        String sql = "Insert Into Persona (DNI,Apellido,Nombre,Correo,address_empadronado) "
                + "values (?,?,?,?,?)";
        
        try {
            PreparedStatement pst1 = CBD1.prepareStatement(sql);
            PreparedStatement pst2 = CBD2.prepareStatement(sql);
            PreparedStatement pst3 = CBD3.prepareStatement(sql);
            PreparedStatement pst4 = CBD4.prepareStatement(sql);

            pst1.setString(1, p.getDni());
            pst1.setString(2, p.getApellidos());
            pst1.setString(3, p.getNombre());
            pst1.setString(4, p.getCorreo());
            pst1.setString(5, p.crearHash(p));
            pst1.execute();
            
            pst2.setString(1, p.getDni());
            pst2.setString(2, p.getApellidos());
            pst2.setString(3, p.getNombre());
            pst2.setString(4, p.getCorreo());
            pst2.setString(5, p.crearHash(p));
            pst2.execute();
            
            pst3.setString(1, p.getDni());
            pst3.setString(2, p.getApellidos());
            pst3.setString(3, p.getNombre());
            pst3.setString(4, p.getCorreo());
            pst3.setString(5, p.crearHash(p));
            pst3.execute();
            
            pst4.setString(1, p.getDni());
            pst4.setString(2, p.getApellidos());
            pst4.setString(3, p.getNombre());
            pst4.setString(4, p.getCorreo());
            pst4.setString(5, p.crearHash(p));
            pst4.execute();

        } catch (Exception e) {
            System.out.println(e);

        }
    }
    public boolean buscar(Persona p){
        boolean validado=false;
        String [] empa=new String[4];
        try{
            String sql = "select Correo,DNI,address_empadronado From Persona ";
            Statement st1=CBD1.createStatement();
            ResultSet rs1=st1.executeQuery(sql);
            Statement st2=CBD2.createStatement();
            ResultSet rs2=st2.executeQuery(sql);
            Statement st3=CBD3.createStatement();
            ResultSet rs3=st3.executeQuery(sql);
            Statement st4=CBD4.createStatement();
            ResultSet rs4=st4.executeQuery(sql);
            
            
            while (rs1.next()) {
                
                if(p.getCorreo().equals(rs1.getString("Correo")) && p.getDni().equals(rs1.getString("DNI"))){
                    validado=true;
                    p.setHash_empadronado(rs1.getString("address_empadronado"));
                    empa[0]=p.getHash_empadronado();
                }
            }
            while (rs2.next()) {
                if(p.getCorreo().equals(rs2.getString("Correo")) && p.getDni().equals(rs2.getString("DNI"))){
                    validado=true;
                    p.setHash_empadronado(rs2.getString("address_empadronado"));
                    empa[1]=p.getHash_empadronado();
                }
            }
            while (rs3.next()) {
                if(p.getCorreo().equals(rs3.getString("Correo")) && p.getDni().equals(rs3.getString("DNI"))){
                    validado=true;
                    p.setHash_empadronado(rs3.getString("address_empadronado"));
                    empa[2]=p.getHash_empadronado();
                }
            }
            while (rs4.next()) {
                if(p.getCorreo().equals(rs4.getString("Correo")) && p.getDni().equals(rs4.getString("DNI"))){
                    validado=true;
                    p.setHash_empadronado(rs4.getString("address_empadronado"));
                    empa[3]=p.getHash_empadronado();
                }
            }
            if(empa[0].equals(empa[1]) && empa[1].equals(empa[2]) && empa[2].equals(empa[3]))
                validado=true;
            else{
                validado=false;
            }
        }
        catch(Exception e){
            System.out.print(e);
            return false;
        }
        return validado;
    }
    
}
