package com.pedrovs.book;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "book_table")

public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String bookName;
    private String author;
    private String bookType;

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Book(String bookName, String author, String bookType) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.bookType = bookType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookType() {
        return bookType;
    }
}
