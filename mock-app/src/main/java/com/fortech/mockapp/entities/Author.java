package com.fortech.mockapp.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Author{
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
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
