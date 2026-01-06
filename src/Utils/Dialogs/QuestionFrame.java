/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.Dialogs;

import Utils.TitleBar;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author PC
 */
public class QuestionFrame extends JDialog{
    
    private boolean answer = false;
    
    public QuestionFrame(JFrame parentFrame, String message){
        super(parentFrame, "Confirm", true); // modal = true
 
        this.setUndecorated(true);       
        this.getContentPane().setBackground(new Color(220, 220, 220));
        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setSize(300, 150);
        this.setLocationRelativeTo(parentFrame);
        this.setLayout(new BorderLayout());
        
        JPanel titleBar = new JPanel();

        

        TitleBar tBar = new TitleBar(this);
        
        tBar.setBackButtonVisible(false);
        titleBar.setLayout(new BorderLayout());
        titleBar.removeAll();
        titleBar.add(tBar, BorderLayout.CENTER);
        titleBar.revalidate();
        titleBar.repaint();    
        
        this.add(titleBar, BorderLayout.NORTH);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        this.add(messageLabel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(220, 220, 220));
        
        buttons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        JButton yesButton = new JButton("Yes");
        yesButton.setBackground(new Color(204, 255, 204));
        
        JButton noButton = new JButton("No");
        noButton.setBackground(new Color(255, 204, 204));

        yesButton.addActionListener(e -> {
            answer = true;
            dispose();
        });

        noButton.addActionListener(e -> {
            answer = false;
            dispose();
        });

        buttons.add(yesButton);
        buttons.add(noButton);
        
        
        this.add(buttons, BorderLayout.SOUTH);       

    }
    
    public boolean getAnswer() {
        return answer;
    }
    
}
