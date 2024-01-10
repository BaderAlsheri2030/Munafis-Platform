package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Orderr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(225) not null")
    private String status;
    @Column(columnDefinition = "double")
    private double totalPrice;






    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "order")
    private Set<Service> services;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true ,mappedBy = "order")
    private Set<ProductsDetails> productsDetails;

    @ManyToOne
    @JoinColumn(name = "company_id",referencedColumnName = "id")
    @JsonIgnore
    private Company company;



}
