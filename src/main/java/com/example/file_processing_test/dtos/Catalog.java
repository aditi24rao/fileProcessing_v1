package com.example.file_processing_test.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "catalog")
public class Catalog {
    List<Book> book;

    public Catalog() {
    }

    public Catalog(List<Book> book) {
        this.book = book;
    }

    @Override
    public String   toString() {
        return "Catalog{" +
                "book=" + book +
                '}';
    }

    @XmlElement(name = "book")
    public List<Book> getBook() {
        return book;
    }

    public void setBook(List<Book> book) {
        this.book = book;
    }
}
