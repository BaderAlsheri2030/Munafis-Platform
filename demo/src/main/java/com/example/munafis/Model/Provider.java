package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Provider{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    private String companyName;
    @Column(columnDefinition = "varchar(50) not null unique")
    private String businessNumber;
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    @Column(columnDefinition = "varchar(50) not null")
    private String field;


    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Service> services;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Product> products;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Offer> offers;


}