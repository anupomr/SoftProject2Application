package app.sebobooks.utility;
/*
 * Purpose: this is a model class
 * Author:  Shila Das
 * Date: March 16, 2019
 * Version: 1.7
 * */
public class Book {
    public  String bookId;
    public  String title;
    public  String description;
    public  float price;
    public int thumbnail;

    public Book() {
    }

    public Book(String bookId, String title, String description, float price, int thumbnail) {

        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.thumbnail = thumbnail;
    }
}
