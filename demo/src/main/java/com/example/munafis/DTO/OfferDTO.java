package com.example.munafis.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OfferDTO {
    private LocalDate dead_line;
    private double price;
    private String conditions;
    private String description;
    private String status;


}
