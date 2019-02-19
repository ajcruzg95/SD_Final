/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class Conexion {
    public String bd="blockchain";
	public String horario="?useTimezone=true&serverTimezone=UTC";
	public String url="jdbc:mysql://127.0.0.1/"+bd;
	public String user="root";
	public String pass="";
	private static Conexion instancia;
	
	private Conexion() {
		
	}
	public Connection conectar() {
		
		Connection link=null;
		try {
			//mysql 8.03Class.forName("com.mysql.cj.jdbc.Driver");
                        Class.forName("org.gjt.mm.mysql.Driver");
			link=DriverManager.getConnection(this.url, this.user, this.pass);
			System.out.println("conecto");
		} catch (Exception e) {
			                 JOptionPane.showMessageDialog(null, e);
			// TODO: handle exception
		}
		return link;
	}
	
	public static Conexion iniciar() {
		if(instancia==null) {
			instancia=new Conexion();
		}
		return instancia;
	}
    
    
}
