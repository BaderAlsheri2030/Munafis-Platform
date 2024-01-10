package com.example.munafis.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data


public class ProductDetalisDTO {
    
    private Integer product_id;
    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be number")
    private Integer quantity;

}
