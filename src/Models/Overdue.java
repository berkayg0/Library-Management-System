/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Overdue {
    private String bookTitle;
    private String username;
    private Date due_date;

    public Overdue(String bookTitle, String username, Date due_date) {
        this.bookTitle = bookTitle;
        this.username = username;
        this.due_date = due_date;
    }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getUserName() { return username; }
    public void setUserName(String username) { this.username = username; }

    public Date getDueDate() { return due_date; }
    public void setDueDate(Date due_date) { this.due_date = due_date; }
    
}
