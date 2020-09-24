package com.fortech.mockapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class CompanyModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "industry")
    private String industry;

    @JsonManagedReference
    @OneToMany(mappedBy="company")
    private Set<ClientModel> clients;

    public CompanyModel(String name, String industry) {
        this.name = name;
        this.industry = industry;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Set<ClientModel> getClients() {
        return clients;
    }

    public void setClients(Set<ClientModel> clients) {
        this.clients = clients;
    }
}
