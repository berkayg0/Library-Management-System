/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Book;
import db.DB;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
public class BookDAO {
      
    public ArrayList<Book> getAllBooks(){
        ArrayList<Book> allBooks = new ArrayList<>();
        try(
            Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM books");
            ResultSet result = pst.executeQuery()
        ){           
            while(result.next()){
                Book book = new Book(result.getInt("id"), result.getString("title"), result.getString("author"), result.getString("category"), result.getInt("quantity"), result.getInt("page_count"), result.getInt("year"));
                allBooks.add(book);
            }                 
        } catch (Exception e) {
            e.printStackTrace();
        }       
        return allBooks;
    }
    
    public BookResult checkAndUpdateQuantity(String title, int amount){
        String query = "SELECT quantity FROM books WHERE title=?";
        
        try(Connection con = DB.getConnection();
            PreparedStatement pst = con.prepareStatement(query);){
            pst.setString(1, title);
            
            ResultSet result = pst.executeQuery();
            if(result.next()){
                String updateQuery = "UPDATE books SET quantity = quantity + ? WHERE title=?";
                try(PreparedStatement pstu = con.prepareStatement(updateQuery);){
                pstu.setInt(1, amount);
                pstu.setString(2, title);
                pstu.executeUpdate();                 
                }  
                return new BookResult(true, "The book has been added to the stock.", null); 
            }
            else{
                return new BookResult(false, "The book is not available.", null);
            }            
            
        } catch(Exception e){
            e.printStackTrace();
            return new BookResult(false, "Error: " + e.getMessage(), null);
        }        
    } 
    
    public BookResult addBook(String title, String author, String category, int quantity, int page_count, int year){
        
            String query = "INSERT INTO books (title, author, category, quantity, page_count, year) VALUES (?, ?, ?, ?, ?, ?)";
            
            try(Connection con = DB.getConnection();
                PreparedStatement pst = con.prepareStatement(query);){
                pst.setString(1, title);
                pst.setString(2, author);
                pst.setString(3, category);
                pst.setInt(4, quantity);
                pst.setInt(5, page_count);
                pst.setInt(6, year);
                
                int row = pst.executeUpdate();
                
                if(row > 0){
                    return new BookResult(true, "The book has been successfully added.", null); 
                }
                else{
                    return new BookResult(false, "The book is not available.", null);
                }
                
            } catch(Exception e){
                e.printStackTrace();
                return new BookResult(false, "Error: " + e.getMessage(), null);               
            }        
    }
    
    public BookResult updateBook(String title, String author, String category, int quantity, int page_count, int year, int bookId){
        
        String query = "UPDATE books SET title=?, author=?, category=?, quantity=?, page_count=?, year=? WHERE id=?";
        
        try(Connection con = DB.getConnection(); 
            PreparedStatement pst = con.prepareStatement(query);){               
            
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, category);
            pst.setInt(4, quantity);  
            pst.setInt(5, page_count);
            pst.setInt(6, year);
            pst.setInt(7, bookId);
            
            int result = pst.executeUpdate();
            
            if(result > 0){
                return new BookResult(true, "The book has been updated.", null); 
            }
            else{
                return new BookResult(false, "The book could not be updated.", null); 
            }
            
        } catch(Exception e){
            e.printStackTrace();
            return new BookResult(false, "Error: " + e.getMessage(), null); 
        }        
    }
    
    public BookResult deleteBook(int bookId){
        
        String sqlDeleteBorrowings = "DELETE FROM borrowings WHERE book_id=?";
        String sqlDeleteBook = "DELETE FROM books WHERE id=?";
        
        try(Connection con = DB.getConnection()){
            con.setAutoCommit(false);
            
            
            try(PreparedStatement psBorrowings = con.prepareStatement(sqlDeleteBorrowings)) {
                psBorrowings.setInt(1, bookId);
                psBorrowings.executeUpdate();
            }
            
            try(PreparedStatement psBook = con.prepareStatement(sqlDeleteBook);){
                
                psBook.setInt(1, bookId);

                int result = psBook.executeUpdate();

                if(result > 0){
                    con.commit();
                    return new BookResult(true, "The book has been successfully deleted.", null);                       
                } 
                else {
                    con.rollback();
                    return new BookResult(false, "The book could not be deleted.", null);
                }
                } catch(Exception e){
                    e.printStackTrace();
                    return new BookResult(false, "Error: " + e.getMessage(), null);
                }                 
            }catch(Exception e) {
                e.printStackTrace();
                return new BookResult(false, "Error: " + e.getMessage(), null);
            }    
    } 
}
