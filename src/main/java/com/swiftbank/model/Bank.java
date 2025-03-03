package com.swiftbank.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private int bankId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    // Constructeurs
    public Bank() {}

    public Bank(String name) {
        this.name = name;
    }

    // Getter

    public String getName() { return name; }

}