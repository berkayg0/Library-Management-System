/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author PC
 */
public class Book {
    
    private int id;
    private String title;
    private String author;
    private String category;
    private int quantity;
    private int page_count;
    private int year;
    
    
    public Book(int id, String title, String author, String category, int quantity, int page_count, int year){
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
        this.page_count = page_count;
        this.year = year;
    }
    
    public int getID(){ return this.id; };
    public String getTitle(){ return this.title; };
    public String getAuthor(){ return this.author; };
    public String getCategory(){ return this.category; };
    public int getQuantity(){ return this.quantity; };
    public int getPageCount(){ return this.page_count; };
    public int getYear(){ return this.year; };
    
    public void setQuantity(int quantity){this.quantity = quantity;}
    
    
}
