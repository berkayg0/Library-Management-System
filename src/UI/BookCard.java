/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import Interfaces.BookCardInterface;
import Models.Book;
import Models.User;
import Service.BorrowingService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

/**
 *
 * @author PC
 */
public class BookCard extends JPanel{
    

    private User currentUser;
    private Book book;
    private JFrame parentFrame;
    private boolean dueDate_visible = false;
    private BookCardInterface bcInterface;
    private Runnable onUpdateCallback;

    
    public BookCard(User user, Book book, BookCardInterface bcInterface, Color c){
        this.currentUser = user;
        this.book = book;
        this.bcInterface = bcInterface;
        
        this.setLayout(new BorderLayout());
        
        this.setBackground(c);
        
        this.setMinimumSize(new Dimension(120, 90));
        this.setMaximumSize(new Dimension(120, 90));
        this.setPreferredSize(new Dimension(120, 90));
        
        JLabel bookIcon = new JLabel();
        bookIcon.setHorizontalAlignment(SwingConstants.CENTER);
        bookIcon.setIcon(new ImageIcon(getClass().getResource("/resources/images/book.png")));
        
        JLabel bookName = new JLabel(book.getTitle(), SwingConstants.CENTER);

        this.add(bookIcon, BorderLayout.CENTER);
        this.add(bookName, BorderLayout.SOUTH);
        
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(bcInterface != null){
                    bcInterface.onClick(currentUser, book);
                }
            }
        });        
    } 
    
        public void addRemoveButton() {

        JPopupMenu popup = new JPopupMenu();
        JMenuItem maskAsReturn = new JMenuItem("Mark as Returned");
        popup.add(maskAsReturn);

        maskAsReturn.addActionListener(e -> {
            
            boolean success = new BorrowingService().markAsReturn(this.currentUser.getID(), this.book.getID());
            
            if(success){     
                JPanel panel = (JPanel) this.getParent();
                if(panel != null){
                    panel.remove(this);
                    panel.revalidate();
                    panel.repaint();
                }
                if (onUpdateCallback != null) {
                    onUpdateCallback.run();
                }            
            }
        });

        this.addMouseListener(new MouseAdapter() {
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger())
                    popup.show(e.getComponent(), e.getX(), e.getY());
            }
            @Override public void mousePressed(MouseEvent e) { showPopup(e); }
            @Override public void mouseReleased(MouseEvent e) { showPopup(e); }
        });
    }
    
    public void setDueDateVisible(boolean visible){
        this.dueDate_visible = visible;
    }
    
    public void setOnUpdateCallback(Runnable callback) {
        this.onUpdateCallback = callback;
    }
    
}
