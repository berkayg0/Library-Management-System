
import javax.swing.JFrame;
import library.management.system.AllBooks;
import library.management.system.AllUsers;
import library.management.system.AssignToUser;
import library.management.system.BookInfo;
import library.management.system.Dashboard;
import library.management.system.InformationMessage;
import library.management.system.Login;
import library.management.system.Register;
import library.management.system.ResetPassword;
import library.management.system.StartScreen;
import library.management.system.UserInfo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {               
                StartScreen startScreen = new StartScreen();          
                startScreen.setLocationRelativeTo(null);
                startScreen.setResizable(false);
                startScreen.setVisible(true);
            }
        });
    }
}