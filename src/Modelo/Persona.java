/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import BLOCKS.CalcularHash;
import java.security.MessageDigest;

/**
 *
 * @author ACER
 */
public class Persona {
    private int codigo;
    private String dni,apellidos,nombre,correo,hash_empadronado;
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getHash_empadronado() {
        return hash_empadronado;
    }
    public void setHash_empadronado(String h){
        hash_empadronado=h;
    }
    
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append("Persona #").append(dni).append("[Apellido: ").append(apellidos).append(", ").
                append("Nombre: ").append(nombre).append(", ").append("correo: ").append(correo).append(", ").
                append("hash: ").append(hash_empadronado).append("]");
        return builder.toString();
    }
    public String str(){
        return dni+apellidos+nombre+correo;
    }
    public String crearHash(Persona persona){
        CalcularHash calc=new CalcularHash();
        return calc.sha256(str());
    }
        
}

    

