package com.fortech.mockapp.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id")
    protected String id;
    @Column(name="title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "price")
    private Integer price;
    @Column(name = "category")
    private String category;

    public Book(String title, String author, String category, int price) {
        this.title=title;
        this.author=author;
        this.category=category;
        this.price=price;
    }

    public Book() {
    }
}
