/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.User;
import Utils.Hash;
import dao.UserDAO;
import dao.UserResult;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class UserService {
    
    private UserDAO userDAO = new UserDAO();
    
    public UserResult login(String username, String password){
        
        User user = userDAO.getByUsername(username);
        
        if(user == null){
            return new UserResult(false, "User not found.", null);
        }
        else{
           String hashPassword = Hash.hashPassword(password); 
           if(user.getPassword().equals(hashPassword)){
               if(user.getStatus().equals("pending")){
                return new UserResult(false, "Your account is pending approval.", null);
               }
               else{
                return new UserResult(true, "Login successful.", user);
               }               
           }
           else{
               return new UserResult(false, "Incorrect password or username.", null);
           }
        }
       
    }
    
    public UserResult register(String first_name, String last_name, String username, String user_email, String phone, String password){
        
        if(first_name == null || first_name.trim().isEmpty() || 
            last_name == null || last_name.trim().isEmpty() || 
            username == null || username.trim().isEmpty() || 
            user_email == null || user_email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {

            return new UserResult(false, "All fields are required.", null);
        }
        
        if(!user_email.contains("@") || !user_email.contains(".")){
            return new UserResult(false, "Invalid email format. Please enter a valid email.", null);
        }
        
        if(!phone.matches("\\d+")) {
            return new UserResult(false, "Phone number must contain only digits.", null);
        }
        
        if(userDAO.getByUsername(username) != null){
            return new UserResult(false, "Username already exists.", null);
        }
        else{
         String hashedPassword = Hash.hashPassword(password);   
         
         User user = userDAO.registerUser(first_name, last_name, username, user_email, phone, hashedPassword);
         
         if(user != null){
             return new UserResult(true, "Registration successful.", user);
         }
        } 
        return new UserResult(false, "Registration failed.", null);
    }
    
    public boolean logout(User user) {
        return userDAO.updateLastLogin(user.getID());
    }
    
    public ArrayList<User> getRegisteredUsers(){
        return userDAO.getAllRegisteredUsers();
    }
    public ArrayList<User> getPendingUsers(){
        return userDAO.getAllPendingUsers();
    }  
    
    public UserResult updateUser(String first_name, String last_name, String username, String user_email, String user_role, String phone, String status, int userId){
        return userDAO.updateUser(first_name, last_name, username, user_email, user_role, phone, status, userId);
    }
    
    public UserResult deleteUser(int userId){
        return userDAO.deleteUser(userId);
    }
 
    public UserResult resetPassword(int userId, String password, boolean flag){
        return userDAO.resetPassword(userId, password, flag);
    }
    
    public void approveUser(int userId){
        userDAO.approveUser(userId);
    }
    
}
