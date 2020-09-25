package com.fortech.mockapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    private String street;

    private String addressNumber;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private ClientModel client;

    public AddressModel() {}

    public AddressModel(String street, String addressNumber, ClientModel client) {
        this.street = street;
        this.addressNumber = addressNumber;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }
}
