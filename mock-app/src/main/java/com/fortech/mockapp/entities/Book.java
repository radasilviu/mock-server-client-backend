package com.fortech.mockapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(unique=true, name="title")
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
