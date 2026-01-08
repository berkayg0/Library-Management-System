/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package library.management.system;

import Interfaces.BookCardInterface;
import Models.Book;
import Models.User;
import Service.BorrowingService;
import Service.UserService;
import UI.BookCard;
import Utils.Dialogs.QuestionFrame;
import Utils.TitleBar;
import dao.UserDAO;
import dao.UserResult;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author PC
 */
public class UserInfo extends javax.swing.JFrame implements BookCardInterface{
    
    private JFrame previousPage;
    private User currentUser;
    private AllUsers allUsers;
    private UserService userService = new UserService();
    private Runnable onLoadPendingUsersCallback;
    private Runnable onLoadRegisteredUsersCallback;

    /**
     * Creates new form UserInfo
     */
    public UserInfo() {
        initComponents();     
    }
    
    public UserInfo(User user) {

        this.currentUser = user;
        this.setUndecorated(true);
        initComponents();
                

        userBooksPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));

        userBooksScrollPane.getVerticalScrollBar().setUnitIncrement(30);
        userBooksScrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        userBooksScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        userIcon.setIcon(new ImageIcon(getClass().getResource("/resources/images/userInfo.png")));
        emailButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        phoneButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        userRoleButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        statusButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        usernameButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        firstNameButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        lastNameButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit.png")));
        
       
        setTitleBar();      
        setFields(); 
        loadBooks();
    }
    
    
        
    public void setTitleBar(){
                
        this.getContentPane().setBackground(new Color(220, 220, 220));
        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        TitleBar tBar = new TitleBar(this);
        
        tBar.setBackButtonVisible(false);
        titleBar.setLayout(new BorderLayout());
        titleBar.removeAll();
        titleBar.add(tBar, BorderLayout.CENTER);
        titleBar.revalidate();
        titleBar.repaint();          
    }
    
    public void loadBooks(){
        
        SwingUtilities.invokeLater(() -> {
            
            ArrayList<Book> currentBooks = new BorrowingService().getCurrentBooks(this.currentUser.getID());

            userBooksPanel.removeAll();
            
            int getTotalBookCount = userService.getTotalBooks(this.currentUser.getID());
            
            this.currentUser.setTotalBooks(String.valueOf(getTotalBookCount));
            
            totalBooks_field.setText(this.currentUser.getTotalBooks());
            
            if (currentBooks.isEmpty()) {
                userBooksPanel.revalidate();
                userBooksPanel.repaint();
                return;
            }
            
            int finalHeight = 0;
            
            if(currentBooks.size() < 3){
                userBooksPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
                
                userBooksPanel.setBorder(null);
                
                finalHeight = 90 + (30*2);

            }
            else{
                userBooksPanel.setLayout(new GridLayout(0, 2, 30, 30));
                
                int totalWidth = (2*120) + 30;
                int remainingSpace = userBooksScrollPane.getViewport().getWidth() - totalWidth;

                int divideMargin = (remainingSpace > 0) ? remainingSpace / 2 : 10;

                userBooksPanel.setBorder(BorderFactory.createEmptyBorder(30, divideMargin, 30, divideMargin));

                int rows = (int) Math.ceil(currentBooks.size() / 2.0);
                finalHeight = (rows * 90) + ((rows-1)*30) + 100; 
                             
            }
                    
           for(Book b : currentBooks){
                BookCard bookCard = new BookCard(this.currentUser, b, this, null);
                bookCard.addRemoveButton();
                bookCard.setOnUpdateCallback(() -> loadBooks());
                bookCard.setDueDateVisible(true);
                userBooksPanel.add(bookCard);                
            }   

            userBooksPanel.setPreferredSize(new Dimension(userBooksScrollPane.getViewport().getWidth() - 20, finalHeight));


            userBooksPanel.revalidate();
            userBooksPanel.repaint();            
        });
        
    }
    
    public void setFields(){
        
        buttonPanel.setBackground(new Color(220, 220, 220));
        
        username_field.setText(this.currentUser.getUsername());
        firstName_field.setText((this.currentUser.getFirstName()));
        lastName_field.setText(this.currentUser.getLastName());
        email_field.setText(this.currentUser.getUserEmaıl());
        phone_field.setText(this.currentUser.getPhone());
        userRole_field.setText(this.currentUser.getUserRole());
        totalBooks_field.setText(this.currentUser.getTotalBooks());
        createdAt_field.setText(this.currentUser.getCreatedAt());
        status_field.setText(this.currentUser.getStatus());

        
        username_field.setDisabledTextColor(Color.black);
        firstName_field.setDisabledTextColor(Color.black);
        lastName_field.setDisabledTextColor(Color.black);
        email_field.setDisabledTextColor(Color.black);
        phone_field.setDisabledTextColor(Color.black);
        userRole_field.setDisabledTextColor(Color.black);
        totalBooks_field.setDisabledTextColor(Color.black);
        createdAt_field.setDisabledTextColor(Color.black);
        status_field.setDisabledTextColor(Color.black);
 
        
        username_field.setDisabledTextColor(Color.black);
        firstName_field.setDisabledTextColor(Color.black);
        lastName_field.setDisabledTextColor(Color.black);
        email_field.setDisabledTextColor(Color.black);
        phone_field.setDisabledTextColor(Color.black);
        userRole_field.setDisabledTextColor(Color.black);
        totalBooks_field.setDisabledTextColor(Color.black);
        createdAt_field.setDisabledTextColor(Color.black);
        status_field.setDisabledTextColor(Color.black);
  
    }

    public void setOnLoadPendingUsersCallback(Runnable callback) {
        this.onLoadPendingUsersCallback = callback;
    }
    public void setOnLoadRegisteredUsersCallback(Runnable callback) {
        this.onLoadRegisteredUsersCallback = callback;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleBar = new javax.swing.JPanel();
        userIconPanel = new javax.swing.JPanel();
        userIcon = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        email_field = new javax.swing.JTextField();
        phone_field = new javax.swing.JTextField();
        totalBooks_field = new javax.swing.JTextField();
        createdAt_field = new javax.swing.JTextField();
        userRole_field = new javax.swing.JTextField();
        status_field = new javax.swing.JTextField();
        emailButton = new javax.swing.JLabel();
        phoneButton = new javax.swing.JLabel();
        userRoleButton = new javax.swing.JLabel();
        statusButton = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        username_field = new javax.swing.JTextField();
        firstName_field = new javax.swing.JTextField();
        lastName_field = new javax.swing.JTextField();
        usernameButton = new javax.swing.JLabel();
        firstNameButton = new javax.swing.JLabel();
        lastNameButton = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        saveUser = new javax.swing.JButton();
        deleteUser = new javax.swing.JButton();
        userBooksScrollPane = new javax.swing.JScrollPane();
        userBooksPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        resetPasswordLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout titleBarLayout = new javax.swing.GroupLayout(titleBar);
        titleBar.setLayout(titleBarLayout);
        titleBarLayout.setHorizontalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        titleBarLayout.setVerticalGroup(
            titleBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        userIconPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout userIconPanelLayout = new javax.swing.GroupLayout(userIconPanel);
        userIconPanel.setLayout(userIconPanelLayout);
        userIconPanelLayout.setHorizontalGroup(
            userIconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userIconPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(userIcon)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        userIconPanelLayout.setVerticalGroup(
            userIconPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userIconPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(userIcon)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel8.setText("email");

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel10.setText("phone");

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel12.setText("user role");

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel14.setText("total books");

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel16.setText("created at");

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel18.setText("status");

        email_field.setEditable(false);
        email_field.setText("alesdelq@gmail.com");
        email_field.setBorder(null);
        email_field.setEnabled(false);

        phone_field.setEditable(false);
        phone_field.setText("05526785419");
        phone_field.setBorder(null);
        phone_field.setEnabled(false);

        totalBooks_field.setEditable(false);
        totalBooks_field.setText("12");
        totalBooks_field.setBorder(null);
        totalBooks_field.setEnabled(false);
        totalBooks_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalBooks_fieldActionPerformed(evt);
            }
        });

        createdAt_field.setEditable(false);
        createdAt_field.setText("29/11/2025");
        createdAt_field.setBorder(null);
        createdAt_field.setEnabled(false);

        userRole_field.setEditable(false);
        userRole_field.setText("Member");
        userRole_field.setBorder(null);
        userRole_field.setEnabled(false);

        status_field.setEditable(false);
        status_field.setText("registered");
        status_field.setBorder(null);
        status_field.setEnabled(false);

        emailButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emailButtonMouseClicked(evt);
            }
        });

        phoneButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                phoneButtonMouseClicked(evt);
            }
        });

        userRoleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userRoleButtonMouseClicked(evt);
            }
        });

        statusButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statusButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(status_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(statusButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createdAt_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(totalBooks_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(userRole_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userRoleButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(phone_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(phoneButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(email_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(emailButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(email_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(phone_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(phoneButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(userRoleButton)
                        .addComponent(userRole_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(totalBooks_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(createdAt_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(status_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(statusButton))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel2.setText("username");

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel4.setText("first name");

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel6.setText("last name");

        username_field.setEditable(false);
        username_field.setText("alexss_0");
        username_field.setBorder(null);
        username_field.setEnabled(false);
        username_field.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                username_fieldActionPerformed(evt);
            }
        });

        firstName_field.setEditable(false);
        firstName_field.setText("Alex");
        firstName_field.setBorder(null);
        firstName_field.setEnabled(false);

        lastName_field.setEditable(false);
        lastName_field.setText("DeLarge");
        lastName_field.setBorder(null);
        lastName_field.setEnabled(false);

        usernameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usernameButtonMouseClicked(evt);
            }
        });

        firstNameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                firstNameButtonMouseClicked(evt);
            }
        });

        lastNameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lastNameButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(username_field))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(firstName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usernameButton)
                    .addComponent(firstNameButton)
                    .addComponent(lastNameButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(username_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(firstName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(firstNameButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(lastName_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lastNameButton))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        saveUser.setBackground(new java.awt.Color(204, 255, 204));
        saveUser.setText("Save");
        saveUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveUserActionPerformed(evt);
            }
        });

        deleteUser.setBackground(new java.awt.Color(255, 204, 204));
        deleteUser.setText("Delete User");
        deleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(saveUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveUser)
                    .addComponent(deleteUser))
                .addGap(10, 10, 10))
        );

        userBooksScrollPane.setMaximumSize(new java.awt.Dimension(343, 356));
        userBooksScrollPane.setMinimumSize(new java.awt.Dimension(343, 356));

        userBooksPanel.setMinimumSize(new java.awt.Dimension(341, 0));
        userBooksPanel.setPreferredSize(new java.awt.Dimension(341, 356));

        javax.swing.GroupLayout userBooksPanelLayout = new javax.swing.GroupLayout(userBooksPanel);
        userBooksPanel.setLayout(userBooksPanelLayout);
        userBooksPanelLayout.setHorizontalGroup(
            userBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
        );
        userBooksPanelLayout.setVerticalGroup(
            userBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );

        userBooksScrollPane.setViewportView(userBooksPanel);

        jPanel3.setBackground(new java.awt.Color(220, 220, 220));

        resetPasswordLabel.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        resetPasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resetPasswordLabel.setText("Reset Password");
        resetPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetPasswordLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resetPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resetPasswordLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jLabel3.setBackground(new java.awt.Color(220, 220, 220));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 20)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("User Books");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userIconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userBooksScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userIconPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(userBooksScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void totalBooks_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalBooks_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalBooks_fieldActionPerformed

    private void username_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_username_fieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_username_fieldActionPerformed

    private void usernameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usernameButtonMouseClicked
        username_field.setEnabled(true);
        username_field.setEditable(true);
    }//GEN-LAST:event_usernameButtonMouseClicked

    private void firstNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_firstNameButtonMouseClicked
        firstName_field.setEnabled(true);
        firstName_field.setEditable(true);
    }//GEN-LAST:event_firstNameButtonMouseClicked

    private void lastNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lastNameButtonMouseClicked
        lastName_field.setEnabled(true);
        lastName_field.setEditable(true);
    }//GEN-LAST:event_lastNameButtonMouseClicked

    private void emailButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailButtonMouseClicked
        email_field.setEnabled(true);
        email_field.setEditable(true);
    }//GEN-LAST:event_emailButtonMouseClicked

    private void phoneButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_phoneButtonMouseClicked
        phone_field.setEnabled(true);
        phone_field.setEditable(true);
    }//GEN-LAST:event_phoneButtonMouseClicked

    private void userRoleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userRoleButtonMouseClicked
        userRole_field.setEnabled(true);
        userRole_field.setEditable(true);
    }//GEN-LAST:event_userRoleButtonMouseClicked

    private void saveUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveUserActionPerformed

       UserResult result = userService.updateUser(firstName_field.getText(), lastName_field.getText(), username_field.getText(), email_field.getText(), userRole_field.getText(), phone_field.getText(), status_field.getText(), this.currentUser.getID());
        
       new InformationMessage(result.getMessage()).setVisible(true);
       
        if (onLoadRegisteredUsersCallback != null && onLoadPendingUsersCallback != null) {
            onLoadRegisteredUsersCallback.run();
            onLoadPendingUsersCallback.run();
        }
    }//GEN-LAST:event_saveUserActionPerformed

    private void deleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserActionPerformed
    
    QuestionFrame qFrame = new QuestionFrame(this, "Are you sure you want to delete the user?");

    qFrame.setVisible(true);  

    boolean answer = qFrame.getAnswer(); 

    if(answer) {
        UserResult result = userService.deleteUser(this.currentUser.getID());
        
        new InformationMessage(result.getMessage()).setVisible(true);
        
        if (onLoadRegisteredUsersCallback != null && onLoadPendingUsersCallback != null) {
            onLoadRegisteredUsersCallback.run();
            onLoadPendingUsersCallback.run();
        }
        
    } else {
            qFrame.dispose();
        } 
    }//GEN-LAST:event_deleteUserActionPerformed

    private void resetPasswordLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetPasswordLabelMouseClicked
        ResetPassword resetPassword = new ResetPassword(this.currentUser);
        resetPassword.userRoleFlag = true;
        resetPassword.locationFlag = false;
        resetPassword.setLocationRelativeTo(null);
        resetPassword.setVisible(true);
    }//GEN-LAST:event_resetPasswordLabelMouseClicked

    private void statusButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusButtonMouseClicked
        status_field.setEnabled(true);
        status_field.setEditable(true);
    }//GEN-LAST:event_statusButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInfo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JTextField createdAt_field;
    private javax.swing.JButton deleteUser;
    private javax.swing.JLabel emailButton;
    private javax.swing.JTextField email_field;
    private javax.swing.JLabel firstNameButton;
    private javax.swing.JTextField firstName_field;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lastNameButton;
    private javax.swing.JTextField lastName_field;
    private javax.swing.JLabel phoneButton;
    private javax.swing.JTextField phone_field;
    private javax.swing.JLabel resetPasswordLabel;
    private javax.swing.JButton saveUser;
    private javax.swing.JLabel statusButton;
    private javax.swing.JTextField status_field;
    private javax.swing.JPanel titleBar;
    private javax.swing.JTextField totalBooks_field;
    private javax.swing.JPanel userBooksPanel;
    private javax.swing.JScrollPane userBooksScrollPane;
    private javax.swing.JLabel userIcon;
    private javax.swing.JPanel userIconPanel;
    private javax.swing.JLabel userRoleButton;
    private javax.swing.JTextField userRole_field;
    private javax.swing.JLabel usernameButton;
    private javax.swing.JTextField username_field;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onClick(User user, Book book) {
        BookInfo bookInfo = new BookInfo(currentUser, book, this);
        bookInfo.setVisible(true);
        bookInfo.setDueDate(true);
        bookInfo.setLocationRelativeTo(null);
    }
}
