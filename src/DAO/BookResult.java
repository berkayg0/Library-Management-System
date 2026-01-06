/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Models.Book;

/**
 *
 * @author PC
 */
public class BookResult {
    private boolean success;
    private String message;
    private Book book;
    
    public BookResult(boolean success, String message, Book book){
        this.success = success;
        this.message = message;
        this.book = book;
    }
    
    public boolean getSuccess() { return this.success; }
    public String getMessage() { return this.message; }
    public Book getBook() { return this.book; }    
    
}
