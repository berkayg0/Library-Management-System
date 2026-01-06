/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Models.User;

/**
 *
 * @author PC
 */
public interface UserCardInterface {
    
    void onUserClick(User user);
    void onUserAccept(User user);
    void onUserDecline(User user);    
    
}
