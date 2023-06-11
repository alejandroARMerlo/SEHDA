package ar.edu.uno.sehda;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPassword {

     public static void main(String[] args) {

         var password= "1234";

         System.out.println("Pasword: " + password);
         System.out.println("Pasword encriptado: " + encriptarPassword(password));
    }

    public static String encriptarPassword(String password){

        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
