/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Book;
import Models.Overdue;
import db.DB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class BorrowingDAO {
    
     public BookResult borrowBook(int userId, int bookId, Date due_date, Connection con){

        String query = "INSERT INTO borrowings (user_id, book_id, borrow_date, due_date, return_date) VALUES (?, ?, ?, ?, ?)";
        
        try(PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setInt(1, userId);
            pst.setInt(2, bookId);
            pst.setDate(3, Date.valueOf(LocalDate.now()));
            pst.setDate(4, due_date);
            pst.setNull(5, java.sql.Types.DATE);
            
            int result = pst.executeUpdate();
            
            if(result > 0){
                return new BookResult(true, "Book borrowed successfully.", null);
            }
            else{
                return new BookResult(false, "Book could not be added.", null);
            }    
        } catch(Exception e){
            e.printStackTrace();
            return new BookResult(false, "Error: " + e.getMessage(), null);
        }
    }   
     
     
    public boolean hasBook(int userId, int bookId) {
        
        String query = "SELECT COUNT(*) FROM borrowings WHERE user_id=? AND book_id=? AND return_date IS NULL";

        try (Connection con = DB.getConnection(); 
            PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);
            pst.setInt(2, bookId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
         }
        return false;
       }
     
    public ArrayList<Book> getCurrentBooks(int userId){
        
        ArrayList<Book> currentBooks = new ArrayList<>();
        
        String query = "SELECT books.id, books.title, books.author, books.category, books.quantity, books.page_count, books.year FROM borrowings JOIN books ON borrowings.book_id = books.id WHERE borrowings.user_id=? AND borrowings.return_date IS NULL";
        
        try(Connection con = DB.getConnection();
         PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setInt(1, userId);
            
            try(ResultSet result = pst.executeQuery()){                          
                while(result.next()){
                    Book book = new Book(result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("category"), result.getInt("quantity"), result.getInt("page_count"), result.getInt("year"));
                    currentBooks.add(book);
                }
            }

            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return currentBooks;
        
    }
    
    public Date getDueDate(int userId, int bookId){
        
        String query = "SELECT due_date FROM borrowings WHERE user_id=? AND book_id=? AND return_date IS NULL";
        
        try(Connection con = DB.getConnection();
         PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setInt(1, userId);
            pst.setInt(2, bookId);
            
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                
                return rs.getDate("due_date"); 
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
        
    }
    
    public boolean markAsReturn(int userId, int bookId, Connection con) throws SQLException{
        
        String query = "UPDATE borrowings SET return_date=CURRENT_DATE WHERE user_id=? AND book_id=? AND return_date IS NULL";
        
        try(PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setInt(1, userId);
            pst.setInt(2, bookId);
            
            int row = pst.executeUpdate();
            
            return row > 0;
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
        
    }
    
    
    public void incrementBookQuantity(int bookId, Connection con) throws SQLException{
        String query = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, bookId);
            pst.executeUpdate();
        }       
    }
    
    public void decreaseBookQuantity(int bookId, int amount, Connection con) throws SQLException{
        String query = "UPDATE books SET quantity = quantity - ? WHERE id = ?";
        try(PreparedStatement pst = con.prepareStatement(query)){
            pst.setInt(1, amount);
            pst.setInt(2, bookId);
            pst.executeUpdate();
        }
        
    }
    
    
    public void updateBookQuantity(int bookId, int quantity, Connection con) throws SQLException{
        String query = "UPDATE books SET quantity=? WHERE id=?";
        
        try(PreparedStatement pst = con.prepareStatement(query);){
            
            pst.setInt(1, quantity);
            pst.setInt(2, bookId);
            
            int row = pst.executeUpdate();
            
            if(row == 0 ){
                throw new SQLException("Failed to update book stock.");
            }

        }  
    }
    
    public ArrayList<Overdue> getOverdueBooks(Integer userId){
        ArrayList<Overdue> overdueList = new ArrayList<>();
        
        String query = "SELECT books.title, users.username, borrowings.due_date FROM borrowings JOIN books ON borrowings.book_id = books.id JOIN users ON borrowings.user_id = users.id WHERE borrowings.due_date < CURRENT_DATE AND borrowings.return_date IS NULL";
        
        if(userId != null){
            query += " AND borrowings.user_id=?";
        }
        
        try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(query);){
            
            if(userId != null){
                pst.setInt(1, userId);
            }
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()){
                overdueList.add(new Overdue(rs.getString("title"), rs.getString("username"), rs.getDate("due_date")));
            }
            
        } catch(Exception e){
            e.printStackTrace();
        }
        return overdueList;
    }
}
