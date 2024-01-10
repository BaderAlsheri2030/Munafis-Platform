package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
public class Company  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(50) not null")
    private String companyName;
    @Column(columnDefinition = "varchar(50) not null unique")
    private String businessNumber;
    @Column(columnDefinition = "varchar(50) not null")
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonIgnore
    private User user;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
    private Set<Orderr> orders;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "company")
    private Set<Rfp> rfps;

}