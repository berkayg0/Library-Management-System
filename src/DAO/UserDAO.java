/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.User;
import Utils.Hash;
import db.DB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author PC
 */
public class UserDAO {
    
    public User getByUsername(String username){
              
        String query = "SELECT * FROM users WHERE username=?";
        
        try(Connection con = (Connection) DB.getConnection();
            PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setString(1, username);
            
            ResultSet result = pst.executeQuery();
            
            if(result.next()){
                User user = new User(result.getInt("id"),
                                result.getString("first_name"),
                                result.getString("last_name"),
                                result.getString("username"),
                                result.getString("user_email"),
                                result.getString("phone"),
                                result.getString("user_password"),
                                result.getString("user_role"),
                                result.getString("created_at"),
                                result.getString("totalBooks"),
                                result.getString("last_login"),
                                result.getString("status"),
                                result.getBoolean("change_password"));
                return user;
            }           
        } catch(Exception e){
            e.printStackTrace();          
        }
        
        return null;
    }
    
    public User registerUser(String first_name, String last_name, String username, String user_email, String phone, String password){
        
        String query = "INSERT INTO users (first_name, last_name, username, user_email, phone, user_password) VALUES (?, ?, ?, ?, ?, ?)";
            
         try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(query);){
                
            pst.setString(1, first_name);
            pst.setString(2, last_name);
            pst.setString(3, username);
            pst.setString(4, user_email);
            pst.setString(5, phone);
            pst.setString(6, password);
                
            int row = pst.executeUpdate();
                
            if(row > 0){
                return getByUsername(username);
            }
                
        } catch(Exception e){
            e.printStackTrace();
        }   
        return null;
    }
    
    public boolean updateLastLogin(int userId){
        
        String update = "UPDATE users SET last_login = NOW() WHERE id = ?";

        try (Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(update);) {

            pst.setInt(1, userId);

            int rows = pst.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }        
    }
    
    public ArrayList<User> getAllRegisteredUsers(){
        
        ArrayList<User> registeredUsers = new ArrayList<>();
        try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE status = 'registered'");){
            ResultSet result = pst.executeQuery();
            
            while(result.next()){
                User registeredUser = new User(result.getInt("id"),
                                                result.getString("first_name"),
                                                result.getString("last_name"),
                                                result.getString("username"),
                                                result.getString("user_email"),
                                                result.getString("phone"),
                                                result.getString("user_password"),
                                                result.getString("user_role"),
                                                result.getString("created_at"),
                                                result.getString("totalBooks"),
                                                result.getString("last_login"),
                                                result.getString("status"),
                                                result.getBoolean("change_password"));     
                    registeredUsers.add(registeredUser);              
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return registeredUsers;
        
    }
    
    public ArrayList<User> getAllPendingUsers(){
        
        ArrayList<User> pendingUsers = new ArrayList<>();
        try(            Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE status = 'pending'");
                ){
            ResultSet result = pst.executeQuery();
            
            while(result.next()){
                    User pendingUser = new User(result.getInt("id"),
                                                result.getString("first_name"),
                                                result.getString("last_name"),
                                                result.getString("username"),
                                                result.getString("user_email"),
                                                result.getString("phone"),
                                                result.getString("user_password"),
                                                result.getString("user_role"),
                                                result.getString("created_at"),
                                                result.getString("totalBooks"),
                                                result.getString("last_login"),
                                                result.getString("status"),
                                                result.getBoolean("change_password"));     
                    pendingUsers.add(pendingUser);     
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return pendingUsers;
        
    }  
    
    public UserResult updateUser(String first_name, String last_name, String username, String user_email, String user_role, String phone, String status, int userId){
        
        String query = "UPDATE users SET first_name=?, last_name=?, username=?, user_email=?, phone=?, user_role=?, status=? WHERE id=?";
        
        try(Connection con = DB.getConnection(); 
            PreparedStatement pst = con.prepareStatement(query); ){               
            
            pst.setString(1, first_name);
            pst.setString(2, last_name);
            pst.setString(3, username);
            pst.setString(4, user_email);  
            pst.setString(5, phone);
            pst.setString(6, user_role);
            pst.setString(7, status);
            pst.setInt(8, userId);
            
            int result = pst.executeUpdate();
            
            if(result > 0){
                return new UserResult(true, "User has been successfully updated.", null);
            }
            else{
                return new UserResult(false, "User could not be updated.", null);
            }
            
        } catch(Exception e){
            e.printStackTrace();
            return new UserResult(false, "Error: " + e.getMessage(), null);           
        }        
    }
    
    public UserResult deleteUser(int userId){
        
        String sqlDeleteBorrowings = "DELETE FROM borrowings WHERE user_id=?";
        String sqlDeleteUser = "DELETE FROM users WHERE id=?";
        
        try(Connection con = DB.getConnection()){
            
            con.setAutoCommit(false);
            
            try (PreparedStatement psb = con.prepareStatement(sqlDeleteBorrowings)) {
                        psb.setInt(1, userId);
                        psb.executeUpdate();
                }
            
            try(PreparedStatement pst = con.prepareStatement(sqlDeleteUser); ){               

                pst.setInt(1, userId);

                int result = pst.executeUpdate();

                if(result > 0){
                    con.commit();
                    return new UserResult(true, "The user has been successfully deleted.", null);
                }
                else{
                    con.rollback();
                    return new UserResult(false, "User could not be deleted.", null);
                }
            } catch(Exception e){
                con.rollback();
                e.printStackTrace();
                return new UserResult(false, "Error: " + e.getMessage(), null);
            }            
        } catch(Exception e){
            e.printStackTrace();
            return new UserResult(false, "Error: " + e.getMessage(), null);           
        }
        

        
    }
    
    public void approveUser(int userId){
        String update = "UPDATE users SET status = 'registered' WHERE id=?";
        try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(update)){
            pst.setInt(1, userId);
            pst.executeUpdate();           
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public UserResult resetPassword(int userId, String password, boolean flag){
        
        String hashedPassword = Hash.hashPassword(password.trim()); 
        String update = "UPDATE users SET user_password =?, change_password=? WHERE id=?";

        try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(update)){
            pst.setString(1, hashedPassword);
            pst.setInt(2, flag ? 1 : 0);
            pst.setInt(3, userId);
            int result = pst.executeUpdate();    
            
            if(result > 0){               
                return new UserResult(true, "The password has been successfully updated.", null);
            }
            else{
                return new UserResult(false, "Failed to update the password", null);
            }
            
        } catch(Exception e){
            e.printStackTrace();
            return new UserResult(false, "Error: " + e.getMessage(), null);
        }        
        
    }
  
    
}
