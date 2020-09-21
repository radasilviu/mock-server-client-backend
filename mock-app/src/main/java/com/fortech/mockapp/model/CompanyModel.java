package com.fortech.mockapp.model;

import javax.persistence.*;

@Entity
@Table(name = "companies")
public class CompanyModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public CompanyModel(String name) {
        this.name = name;
    }

    public CompanyModel() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
