package com.fortech.mockapp.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private Integer age;
//    @OneToMany(mappedBy="author")
//    private Set<Book> books;

    public Author(String name, Set<Book> writtenBooks) {
        this.name=name;
//        this.books=writtenBooks;
    }

    public Author() {

    }
}
