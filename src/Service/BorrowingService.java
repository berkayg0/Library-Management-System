/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.Book;
import Models.Overdue;
import Models.User;
import dao.BookResult;
import dao.BorrowingDAO;
import db.DB;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class BorrowingService {
    
    BorrowingDAO borrowingDAO = new BorrowingDAO();
    
    public BookResult borrowBook(ArrayList<User> selectedUsers, Book book, Date due_date){
        
        if(book.getQuantity() <= 0){
            return new BookResult(false, "The book is currently unavailable.", null);
        }
        if(selectedUsers.size() > book.getQuantity()){
            return new BookResult(false, "Not enough copies available for the selected users.", null);
        }
        
        for(User user : selectedUsers){
            
            if(borrowingDAO.hasBook(user.getID(), book.getID())){
                return new BookResult(false, user.getUsername() + " already has this book.", null);
            }
        }
        
        Connection con = null;
        
        try{
            con = DB.getConnection();
            con.setAutoCommit(false);
            
            borrowingDAO.decreaseBookQuantity(book.getID(), selectedUsers.size(), con);
            
            for(User user : selectedUsers){
                BookResult result = borrowingDAO.borrowBook(user.getID(), book.getID(), due_date, con);
                
                if(!result.getSuccess()){
                    throw new Exception(user.getUsername() + " " + result.getMessage());
                }
            }
            
            con.commit();
            book.setQuantity(book.getQuantity()-selectedUsers.size());
            return new BookResult(true, "Book borrowed successfully.", null);
            
        } catch(Exception e){
            try{ 
                con.rollback(); 
            } catch(SQLException es){ 
                es.printStackTrace(); 
            }
            return new BookResult(false, "Database Error", null);
        }
        finally{
            if(con != null){ 
                try{
                    con.setAutoCommit(true);
                    con.close();
                } catch(SQLException e){
                    e.printStackTrace();
                }  
            }
        }
    }

    public ArrayList<Book> getCurrentBooks(int userId){              
        return borrowingDAO.getCurrentBooks(userId);
    }
    
    public Date getDueDate(int userId, int bookId){
        return borrowingDAO.getDueDate(userId, bookId);
    }
    
    public boolean markAsReturn(int userId, int bookId){
        
        Connection con = null;
        boolean success = false;
        
        try{
            con = DB.getConnection();
            con.setAutoCommit(false);
            
            boolean isMarked = borrowingDAO.markAsReturn(userId, bookId, con);
            
            if(isMarked){
                borrowingDAO.incrementBookQuantity(bookId, con);               
                con.commit();     
                success = true;
            }
            else{
                con.rollback();
                success = false;
            }        
        } catch(Exception e){
            if (con != null){
                try { 
                    con.rollback(); 
                } catch (SQLException es) {
                }
            } 
            e.printStackTrace();
            success = false;           
        } finally{
            if(con != null){
                try {
                    con.setAutoCommit(true); 
                    con.close(); 
                } catch (SQLException e) { 
                    e.printStackTrace(); 
                }
            }
        }

        return success;
    }
    
    public ArrayList<Overdue> getAllOverdueBooks(){       
        return borrowingDAO.getOverdueBooks(null);        
    }
    
    public ArrayList<Overdue> getUserOverdueBooks(int userId){       
        return borrowingDAO.getOverdueBooks(userId);        
    }
    
}
