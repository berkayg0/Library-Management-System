/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class Borrowing {
    private int id;
    private int user_id;
    private int book_id;
    private Date borrow_date;
    private Date due_date;
    private Date return_date;
    
    public Borrowing(int id, int user_id, int book_id, Date borrow_date, Date due_date, Date return_date){
        this.id = id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.due_date = due_date;
        this.return_date = return_date;    
    }
}
