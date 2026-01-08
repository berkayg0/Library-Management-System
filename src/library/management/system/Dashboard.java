/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package library.management.system;

import Interfaces.BookCardInterface;
import Models.Book;
import Models.Overdue;
import Models.User;
import Service.BorrowingService;
import Service.UserService;
import UI.BookCard;
import Utils.TitleBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author PC
 */
public class Dashboard extends javax.swing.JFrame implements BookCardInterface{
    
    private UserService userService = new UserService();
    JFrame previousPage;
    private User currentUser;
    private AddBook addBookPage;
    private AllBooks allBooks;
    private AllUsers allUsers;

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
    }
    
    public Dashboard(User user) {
      
        this.currentUser = user;
        this.previousPage = previousPage;
        this.setUndecorated(true);

        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(user.getUserRole().equals("Member")){
            addBook.setVisible(false);
            allUsersBtn.setVisible(false);           
        }
        
        profile_icon.setIcon(new ImageIcon(getClass().getResource("/resources/images/user.png")));
        viewBooks.setIcon(new ImageIcon(getClass().getResource("/resources/images/books.png")));
        addBook.setIcon(new ImageIcon(getClass().getResource("/resources/images/plus.png")));
        allUsersBtn.setIcon(new ImageIcon(getClass().getResource("/resources/images/users.png")));

        
        currentBooksPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        currentBooksPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 0));
        
        currentBooksScrollPane.setBorder(BorderFactory.createEmptyBorder());

        loadBooks();
        
        updateTotalBooks();
                
        setLabels();


        this.getContentPane().setBackground(new Color(220, 220, 220));
        this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        setTitleBar();
        
        handleOverdueNotification();
        

        this.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
                if (addBookPage != null) {
                    addBookPage.dispose();
                }
                if (allBooks != null) {
                    allBooks.dispose();
                }
                if (allUsers != null) {
                    allUsers.dispose();
                }            
            }
            public void windowClosed(WindowEvent e) {
                if (addBookPage != null) {
                    addBookPage.dispose();
                }
                if (allBooks != null) {
                    allBooks.dispose();
                }
                if (allUsers != null) {
                    allUsers.dispose();
                }              
            }
        });
        
        this.addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                loadBooks();
                updateTotalBooks();
                setLabels();
            }
        });
    }
    
    public void updateTotalBooks(){     
        try {

        int totalBooks = userService.getTotalBooks(this.currentUser.getID());

        this.currentUser.setTotalBooks(String.valueOf(totalBooks)); 

        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadBooks(){
        ArrayList<Book> currentBooks = new BorrowingService().getCurrentBooks(this.currentUser.getID());
        
        int rows = (int) Math.ceil(currentBooks.size() / 3.0);
        int totalHeight = rows * 120;
        
        currentBooksScrollPane.getVerticalScrollBar().setUnitIncrement(30);
                
        currentBooksPanel.setMinimumSize(new Dimension(currentBooksScrollPane.getViewport().getWidth() - 2, 0));
        currentBooksPanel.setMaximumSize(new Dimension(currentBooksScrollPane.getViewport().getWidth() - 2, Integer.MAX_VALUE));
        currentBooksPanel.setPreferredSize(new Dimension(currentBooksScrollPane.getViewport().getWidth() - 2, totalHeight));

        currentBooksPanel.removeAll();
        for(Book b : currentBooks){           
            BookCard bookCard = new BookCard(this.currentUser, b, this, null);
            bookCard.setDueDateVisible(true); 
            currentBooksPanel.add(bookCard);                
            }   

        currentBooksPanel.revalidate();
        currentBooksPanel.repaint();
    }
    
    public void setLabels(){
        DateTimeFormatter createdAtInput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter createdAtOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime cDate = LocalDateTime.parse(this.currentUser.getCreatedAt(), createdAtInput);
        String cFormatted = cDate.format(createdAtOutput);

        DateTimeFormatter lastLoginInput = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter lastLoginOutput = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime lDate = LocalDateTime.parse(this.currentUser.getLastLogin(), lastLoginInput);
        String lFormatted = lDate.format(lastLoginOutput);
        
        name.setText(this.currentUser.getFirstName() + " " + this.currentUser.getLastName().charAt(0) + ".");
        role.setText(this.currentUser.getUserRole());
        totalBooks.setText(this.currentUser.getTotalBooks());


        createdAt.setText(cFormatted);
        lastLogin.setText(lFormatted);
    }
    
    public void setTitleBar(){
        TitleBar tBar = new TitleBar(this);

        tBar.setBackButtonAction(e -> goBack());
        tBar.setBackButtonVisible(false);
        titleBar.setLayout(new BorderLayout());
        titleBar.removeAll();
        titleBar.add(tBar, BorderLayout.CENTER);
        titleBar.revalidate();
        titleBar.repaint();
    }
    
    public void goBack(){
        if (this.previousPage != null) {
            this.previousPage.setVisible(true);
            this.dispose();
        }
    }
    
    public void handleOverdueNotification(){
        
        ArrayList<Overdue> overdueList = new ArrayList<>();
        
        if(this.currentUser.getUserRole().equals("Admin")){
            overdueList = new BorrowingService().getAllOverdueBooks();
        }
        if(this.currentUser.getUserRole().equals("Member")){
            overdueList = new BorrowingService().getUserOverdueBooks(this.currentUser.getID());
        }
        
        if (!overdueList.isEmpty()) {
        StringBuilder sb = new StringBuilder("<html>");    
            if(this.currentUser.getUserRole().equals("Admin")){
                
        
                sb.append("Overdue books detected: <br><br>");

                for (Overdue dto : overdueList) {
                    sb.append(dto.getBookTitle()).append(" - ").append(dto.getUserName()).append("<br><br>");
                }
                sb.append("</html>");  
            }
            if(this.currentUser.getUserRole().equals("Member")){

                sb.append("You have overdue books! <br><br>");

                for (Overdue dto : overdueList) {
                    sb.append(dto.getBookTitle()).append("<br><br>");
                }
                sb.append("</html>");
            }

        
        InformationMessage msg = new InformationMessage(sb.toString());
        msg.setVisible(true);
        msg.setAlwaysOnTop(true);
        msg.setLocationRelativeTo(null);
    }
    }
    
  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        logout_button = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        profile_icon = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        role = new javax.swing.JLabel();
        button_panel = new javax.swing.JPanel();
        addBook = new javax.swing.JButton();
        viewBooks = new javax.swing.JButton();
        allUsersBtn = new javax.swing.JButton();
        dashboard_panel = new javax.swing.JPanel();
        dasboard_label = new javax.swing.JLabel();
        currentBooks_label = new javax.swing.JLabel();
        titleBar = new javax.swing.JPanel();
        info_panel = new javax.swing.JPanel();
        createdAt_label = new javax.swing.JLabel();
        createdAt = new javax.swing.JLabel();
        totalBooks_label = new javax.swing.JLabel();
        totalBooks = new javax.swing.JLabel();
        lastLogin_label = new javax.swing.JLabel();
        lastLogin = new javax.swing.JLabel();
        currentBooksScrollPane = new javax.swing.JScrollPane();
        currentBooksPanel = new javax.swing.JPanel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(747, 686));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(167, 120));
        jPanel1.setMinimumSize(new java.awt.Dimension(167, 120));

        logout_button.setBackground(new java.awt.Color(255, 204, 204));
        logout_button.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        logout_button.setText("LOGOUT");
        logout_button.setFocusPainted(false);
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(logout_button)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout_button)
                .addGap(11, 11, 11))
        );

        profile_icon.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        profile_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile_icon.setIconTextGap(1);

        name.setFont(new java.awt.Font("Ebrima", 1, 14)); // NOI18N
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText("Ali Bayram");

        role.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        role.setText("Admin");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(role, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profile_icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(role))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        button_panel.setBackground(new java.awt.Color(220, 220, 220));
        button_panel.setMaximumSize(new java.awt.Dimension(167, 232));
        button_panel.setMinimumSize(new java.awt.Dimension(167, 232));
        button_panel.setPreferredSize(new java.awt.Dimension(167, 232));

        addBook.setBackground(new java.awt.Color(190, 190, 190));
        addBook.setText("Add book");
        addBook.setFocusPainted(false);
        addBook.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookActionPerformed(evt);
            }
        });

        viewBooks.setBackground(new java.awt.Color(190, 190, 190));
        viewBooks.setText("View Books");
        viewBooks.setFocusPainted(false);
        viewBooks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewBooksActionPerformed(evt);
            }
        });

        allUsersBtn.setBackground(new java.awt.Color(190, 190, 190));
        allUsersBtn.setText("Users");
        allUsersBtn.setFocusPainted(false);
        allUsersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allUsersBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout button_panelLayout = new javax.swing.GroupLayout(button_panel);
        button_panel.setLayout(button_panelLayout);
        button_panelLayout.setHorizontalGroup(
            button_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addBook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(viewBooks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(allUsersBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        button_panelLayout.setVerticalGroup(
            button_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_panelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(viewBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addBook, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(allUsersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        dashboard_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dashboard_panel.setMaximumSize(new java.awt.Dimension(437, 134));
        dashboard_panel.setMinimumSize(new java.awt.Dimension(437, 134));

        dasboard_label.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        dasboard_label.setText("DASHBOARD");

        javax.swing.GroupLayout dashboard_panelLayout = new javax.swing.GroupLayout(dashboard_panel);
        dashboard_panel.setLayout(dashboard_panelLayout);
        dashboard_panelLayout.setHorizontalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_panelLayout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(dasboard_label)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dashboard_panelLayout.setVerticalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dasboard_label)
                .addGap(54, 54, 54))
        );

        currentBooks_label.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        currentBooks_label.setText("Current Books");

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

        info_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        info_panel.setMaximumSize(new java.awt.Dimension(130, 229));
        info_panel.setMinimumSize(new java.awt.Dimension(130, 229));

        createdAt_label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        createdAt_label.setText("Created At");

        createdAt.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        createdAt.setText("27/11/2025");

        totalBooks_label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        totalBooks_label.setText("Total Books");

        totalBooks.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        totalBooks.setText("0");

        lastLogin_label.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        lastLogin_label.setText("Last Login");

        lastLogin.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        lastLogin.setText("27/11/2025 21:14");

        javax.swing.GroupLayout info_panelLayout = new javax.swing.GroupLayout(info_panel);
        info_panel.setLayout(info_panelLayout);
        info_panelLayout.setHorizontalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(info_panelLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(totalBooks))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, info_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lastLogin))
                    .addGroup(info_panelLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastLogin_label)
                            .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(totalBooks_label)
                                .addGroup(info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(createdAt)
                                    .addComponent(createdAt_label))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        info_panelLayout.setVerticalGroup(
            info_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(info_panelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(createdAt_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createdAt)
                .addGap(20, 20, 20)
                .addComponent(totalBooks_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalBooks)
                .addGap(20, 20, 20)
                .addComponent(lastLogin_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        currentBooksScrollPane.setMaximumSize(new java.awt.Dimension(501, 384));
        currentBooksScrollPane.setMinimumSize(new java.awt.Dimension(501, 384));
        currentBooksScrollPane.setName(""); // NOI18N
        currentBooksScrollPane.setPreferredSize(new java.awt.Dimension(501, 384));
        currentBooksScrollPane.setViewportView(currentBooksPanel);

        currentBooksPanel.setMaximumSize(null);
        currentBooksPanel.setMinimumSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout currentBooksPanelLayout = new javax.swing.GroupLayout(currentBooksPanel);
        currentBooksPanel.setLayout(currentBooksPanelLayout);
        currentBooksPanelLayout.setHorizontalGroup(
            currentBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );
        currentBooksPanelLayout.setVerticalGroup(
            currentBooksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );

        currentBooksScrollPane.setViewportView(currentBooksPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(info_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(currentBooks_label)
                        .addGap(85, 85, 85))
                    .addComponent(dashboard_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currentBooksScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(titleBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(titleBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dashboard_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(currentBooks_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentBooksScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(button_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(info_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        
        userService.logout(this.currentUser);
        
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);
        startScreen.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void addBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookActionPerformed
        addBookPage = new AddBook();
        addBookPage.setLocationRelativeTo(null);
        addBookPage.setVisible(true);      
    }//GEN-LAST:event_addBookActionPerformed

    private void viewBooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewBooksActionPerformed
        allBooks = new AllBooks(currentUser);
        allBooks.setLocationRelativeTo(null);
        allBooks.setVisible(true);      
    }//GEN-LAST:event_viewBooksActionPerformed

    private void allUsersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allUsersBtnActionPerformed
        allUsers = new AllUsers();
        allUsers.setLocationRelativeTo(null);
        allUsers.setVisible(true);
    }//GEN-LAST:event_allUsersBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBook;
    private javax.swing.JButton allUsersBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JPanel button_panel;
    private javax.swing.JLabel createdAt;
    private javax.swing.JLabel createdAt_label;
    private javax.swing.JPanel currentBooksPanel;
    private javax.swing.JScrollPane currentBooksScrollPane;
    private javax.swing.JLabel currentBooks_label;
    private javax.swing.JLabel dasboard_label;
    private javax.swing.JPanel dashboard_panel;
    private javax.swing.JPanel info_panel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lastLogin;
    private javax.swing.JLabel lastLogin_label;
    private javax.swing.JButton logout_button;
    private javax.swing.JLabel name;
    private javax.swing.JLabel profile_icon;
    private javax.swing.JLabel role;
    private javax.swing.JPanel titleBar;
    private javax.swing.JLabel totalBooks;
    private javax.swing.JLabel totalBooks_label;
    private javax.swing.JButton viewBooks;
    // End of variables declaration//GEN-END:variables

    @Override
    public void onClick(User user, Book book) {
        BookInfo bookInfo = new BookInfo(currentUser, book, this);
        bookInfo.setButtonsVisible(false);
        bookInfo.setVisible(true);
        bookInfo.setDueDate(true);
        bookInfo.setLocationRelativeTo(null);
    }
}
