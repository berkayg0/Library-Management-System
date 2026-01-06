/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Interfaces.UserCardInterface;
import Models.User;
import Service.UserService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author PC
 */
public class UserCard extends JPanel{
    
    private User user;
    private JLabel declineButton, acceptButton;
    JCheckBox selectBox;
    private JPanel parentPanel;

    public UserCard(User user, JPanel parentPanel, UserCardInterface ucInterface){
        this.user = user;
        this.parentPanel = parentPanel;

        
        this.setLayout(new BorderLayout());
        
        setComponents();
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ucInterface.onUserClick(user);
            }
        });        
        
        acceptButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                ucInterface.onUserAccept(user);
            }
        });
        
        declineButton.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                ucInterface.onUserDecline(user);
            }
        });      
        
        
    }
    
    public void setComponents(){
        
  
        this.setBackground(new Color(220, 220, 220));
        this.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));  
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel userIcon = new JLabel();             
        userIcon.setIcon(new ImageIcon(getClass().getResource("/resources/images/user.png")));
        userIcon.setBorder(new EmptyBorder(0, 20, 0, 0));
        
        String name = user.getFirstName();
        
        if(user.getLastName() != null && !user.getLastName().isEmpty()){
            name += " " + user.getLastName().substring(0,1) + ".";
        }
        
        JLabel firstAndLastName = new JLabel(name, SwingConstants.CENTER);
        firstAndLastName.setBorder(new EmptyBorder(0, 0, 0, 60));
        
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(220, 220, 220));
        rightPanel.setLayout(new BorderLayout());
        
        acceptButton = new JLabel();

        acceptButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/tick.png")));
        acceptButton.setBorder(new EmptyBorder(0, 0, 0, 10));
        rightPanel.add(acceptButton, BorderLayout.WEST); 

        declineButton = new JLabel();
        
        declineButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/x.png")));
        declineButton.setBorder(new EmptyBorder(0, 0, 0, 10));
        rightPanel.add(declineButton, BorderLayout.CENTER);
        
        selectBox = new JCheckBox();
        selectBox.setBackground(new Color(220, 220, 220));
        rightPanel.add(selectBox, BorderLayout.EAST);
        
        this.add(userIcon, BorderLayout.WEST);
        this.add(firstAndLastName, BorderLayout.CENTER);
        this.add(rightPanel, BorderLayout.EAST);      
    }
    
    public void setButtonsVisible(boolean visible){
        declineButton.setVisible(visible);
        acceptButton.setVisible(visible);
    }
    
    public void setCheckBoxVisible(boolean visible){
        if(selectBox != null){
            selectBox.setVisible(visible);
        }        
    }
    
    public boolean isSelected(){
        return selectBox.isSelected();
    }
    
    public User getUser(){
        return this.user;
    }

    
  
    
    
}
