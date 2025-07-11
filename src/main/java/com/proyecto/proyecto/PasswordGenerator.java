package com.proyecto.proyecto;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordGenerator {

    public static void main(String[] args) {   
        String contraseña = "PapaNoel0="; 
        String ContraseñaCifrada = BCrypt.hashpw(contraseña, BCrypt.gensalt());
        System.out.println("Contraseña cifrada: " + ContraseñaCifrada);
    }
}