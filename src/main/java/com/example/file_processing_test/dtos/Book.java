package com.example.file_processing_test.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book {
    private long bookId;
    private String author;
    private String title;
    private String genre;
    private long price;
    private String publish_date;
    private String description;

    public Book() {
    }

    public Book(long bookId, String author, String title, String genre, long price, String publish_date, String description) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.publish_date = publish_date;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", publish_date='" + publish_date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
