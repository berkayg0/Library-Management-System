/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.security.MessageDigest;

/**
 *
 * @author PC
 */
public class Hash {
    
        public static String hashPassword(String password){
        try{
          MessageDigest md = MessageDigest.getInstance("SHA3-256");
          byte[] hash = md.digest(password.getBytes("UTF-8"));
          StringBuilder sb = new StringBuilder();
           for(byte b : hash){
                sb.append(String.format("%02x", b));
            }
          return sb.toString();
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
