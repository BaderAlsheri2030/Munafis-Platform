package com.example.munafis.DTO;

import com.example.munafis.Model.Product;
import com.example.munafis.Model.ProductsDetails;
import com.example.munafis.Model.Service;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Data
public class OrderDTO {

//    @Pattern(regexp = "^(accepted|pending|completed)$")
    private String status;
    private double totalPrice;
    private Integer product_id;
    private Integer service_id;
    private Set<Service> services;
    private Set<ProductsDetails> productsDetails;
    private Integer company_id;



}
