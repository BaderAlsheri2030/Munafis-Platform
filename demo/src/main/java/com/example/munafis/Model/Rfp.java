package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Rfp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(200) not null")
    private String description;
    @Column(columnDefinition = "varchar(50) not null")
    private String referenceNumber;
    @Column(columnDefinition = "varchar(50) not null")
    private String competitionType;
    //last date
    private LocalDate deadLine;
    private String location;
    //project start date
    private LocalDate startDate;
    @Column(columnDefinition = "int not null")
    private Integer contractLength;
    @Column(columnDefinition = "varchar(50) not null")
    private String serviceDetails;
    @Column(columnDefinition = "varchar(50) not null")
    private String title;
    @Column
    private boolean isComplete;
    private String name;
    //time left to propose offers to project
    private LocalDate timeLeft;

    @ManyToOne
    @JoinColumn(name = "company_id" , referencedColumnName = "id")
    @JsonIgnore
    private Company company;


    @ManyToOne
    @JoinColumn(name = "competition_id" , referencedColumnName = "id")
    @JsonIgnore
    private Competition competition;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "rfp")
    private Set<Offer> offers;

}