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
    private Integer id;
    @Column(unique=true)
    private String title;
    private String author;
    private Integer price;
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
