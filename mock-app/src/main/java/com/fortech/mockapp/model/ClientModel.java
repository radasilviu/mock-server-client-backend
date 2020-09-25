package com.fortech.mockapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class ClientModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="company_id", nullable = false)
    private CompanyModel company;

    @JsonManagedReference
    @OneToMany(mappedBy="client")
    private Set<AddressModel> addresses;

    public ClientModel() {}

    public ClientModel(String name, CompanyModel company) {
        this.name = name;
        this.company = company;
    }

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

    public CompanyModel getCompany() {
        return company;
    }

    public void setCompany(CompanyModel company) {
        this.company = company;
    }
}
