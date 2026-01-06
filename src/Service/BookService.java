/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Models.Book;
import dao.BookDAO;
import dao.BookResult;
import java.util.ArrayList;

/**
 *
 * @author PC
 */
public class BookService {
    
    private BookDAO bookDAO = new BookDAO();
    
    public ArrayList<Book> getAllBooks(){
        return bookDAO.getAllBooks();
    }
    
    public BookResult checkAndUpdateQuantity(String title, int amount){
        
        if(amount < 0){
            return new BookResult(false, "Stock quantity cannot be negative.", null);
        }
        
        return bookDAO.checkAndUpdateQuantity(title, amount);
    }
    
    public BookResult addBook(String title, String author, String category, int quantity, int page_count, int year){
        
        if(quantity < 0){
            return new BookResult(false, "Stock quantity cannot be negative.", null);
        }
        
        return bookDAO.addBook(title, author, category, quantity, page_count, year);
    }
    
    public BookResult updateBook(String title, String author, String category, int quantity, int page_count, int year, int bookId){
        return bookDAO.updateBook(title, author, category, quantity, page_count, year, bookId);
    }
    
    public BookResult deleteBook(int bookId){
        return bookDAO.deleteBook(bookId);
    }
    
}
