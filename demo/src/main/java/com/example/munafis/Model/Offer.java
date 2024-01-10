package com.example.munafis.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Offer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        //    @NotNull(message = "description cannot be null")
        @Column(columnDefinition = "varchar(400) not null")
        private String description;
        @Column
        private LocalDate deadLine;
        @Column(columnDefinition = "double not null")
        private double price;
        //    @Pattern(regexp = "^(accepted|rejected|completed)$")
        //    @NotNull(message = "status cannot be null")
        @Column(columnDefinition = "varchar(20)")
        private String status;
        //    @NotNull(message = "conditions cannot be null")
        @Column(columnDefinition = "varchar(400) not null")
        private String conditions;


        @ManyToOne
        @JoinColumn(name = "rfp_id" , referencedColumnName = "id")
        @JsonIgnore
        private Rfp rfp;

        @ManyToOne
        @JoinColumn(name = "provider_id" , referencedColumnName = "id")
        @JsonIgnore
        private Provider provider;

    }
