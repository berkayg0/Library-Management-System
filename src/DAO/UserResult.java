/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.User;

/**
 *
 * @author PC
 */
public class UserResult {
    
    private boolean success;
    private String message;
    private User user;
    
    public UserResult(boolean success, String message, User user){
        this.success = success;
        this.message = message;
        this.user = user;
    }
 
    public boolean getSuccess() { return this.success; }
    public String getMessage() { return this.message; }
    public User getUser() { return this.user; }
}
