/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author PC
 */
public class TitleBar extends JPanel{

    private Point initialClick;
    private JButton backButton;
    private JButton minimizeButton;
    private JButton closeButton;
    private JPanel rightPanel;
    

    public TitleBar(JFrame frame) {
        this.setBackground(new Color(105,105,105));
        this.setPreferredSize(new Dimension(frame.getWidth(), 40));
        this.setLayout(new BorderLayout());
        
        backButton = new JButton();       
        backButton.setPreferredSize(new Dimension(45, 40));       
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(105,105,105));
        backButton.setFocusPainted(false);
        backButton.setBorder(null);
        
        backButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/arrow.png")));
        
        this.add(backButton, BorderLayout.WEST);
        
        rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BorderLayout());
        
      
        minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(45, 40));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setBackground(new Color(105,105,105));
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBorder(null);
        minimizeButton.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        
        rightPanel.add(minimizeButton);

        closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(45, 40));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(105,105,105));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(null);
        closeButton.addActionListener(e -> frame.dispose());


        rightPanel.add(closeButton, BorderLayout.EAST);
        
        
        this.add(rightPanel, BorderLayout.EAST);
        
        minimizeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }            
            });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                frame.setLocation(thisX + xMoved, thisY + yMoved);
            }
        });
    }
    
        public TitleBar(JDialog dialog) {
        this.setBackground(new Color(105,105,105));
        this.setPreferredSize(new Dimension(dialog.getWidth(), 40));
        this.setLayout(new BorderLayout());
        
        backButton = new JButton();       
        backButton.setPreferredSize(new Dimension(45, 40));       
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(105,105,105));
        backButton.setFocusPainted(false);
        backButton.setBorder(null);

        backButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/arrow.png")));
        
        this.add(backButton, BorderLayout.WEST);
        
        rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BorderLayout());
        
      
        minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(45, 40));
        minimizeButton.setForeground(Color.WHITE);
        minimizeButton.setBackground(new Color(105,105,105));
        minimizeButton.setFocusPainted(false);
        minimizeButton.setBorder(null);
        minimizeButton.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        
        rightPanel.add(minimizeButton);

        closeButton = new JButton("X");
        closeButton.setPreferredSize(new Dimension(45, 40));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(105,105,105));
        closeButton.setFocusPainted(false);
        closeButton.setBorder(null);
        closeButton.addActionListener(e -> dialog.dispose());


        rightPanel.add(closeButton, BorderLayout.EAST);
        
        
        this.add(rightPanel, BorderLayout.EAST);

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = dialog.getLocation().x;
                int thisY = dialog.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                dialog.setLocation(thisX + xMoved, thisY + yMoved);
            }
        });
    }
    
    public void setBackButtonVisible(boolean visible){
        backButton.setVisible(visible);
    }
    
    public void setBackButtonAction(ActionListener action){
        for(ActionListener al : backButton.getActionListeners()){
            backButton.removeActionListener(al);
        }
        backButton.addActionListener(action);
    }
}
      
