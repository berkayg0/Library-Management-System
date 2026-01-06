/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author PC
 */
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String user_email;
    private String phone;
    private String user_role;
    private String created_at;
    private String totalBooks;
    private String last_login;
    private String status;
    private String password;
    private boolean changePassword;
    
    public User(int id, String first_name, String last_name, String username, String user_email, String phone, String password, String user_role, String created_at, String totalBooks, String last_login, String status, boolean changePassword){
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.user_email = user_email;
        this.phone = phone;
        this.user_role = user_role;
        this.created_at = created_at;
        this.totalBooks = totalBooks;
        this.last_login = last_login;
        this.status = status;
        this.password = password;
        this.changePassword = changePassword;
    }
    
    public int getID(){ return this.id; };
    public String getFirstName(){ return this.first_name; };
    public String getLastName(){ return this.last_name; };
    public String getUsername(){ return this.username; };
    public String getUserEmaıl(){ return this.user_email; };
    public String getPhone(){ return this.phone; };
    public String getPassword(){ return this.password; };
    public String getUserRole(){ return this.user_role; };
    public String getCreatedAt(){ return this.created_at; };
    public String getTotalBooks(){ return this.totalBooks; };
    public String getLastLogin(){ return this.last_login; };
    public String getStatus(){ return this.status; };
    public boolean getChangePassword(){ return this.changePassword; };
    
    public void setChangePassword(boolean bl){ this.changePassword = bl; }
}
