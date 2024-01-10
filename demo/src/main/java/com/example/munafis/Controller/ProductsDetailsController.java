package com.example.munafis.Controller;


import com.example.munafis.DTO.ProductDetalisDTO;
import com.example.munafis.Model.User;
import com.example.munafis.Service.ProductsDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productsDetails")
@RequiredArgsConstructor
public class ProductsDetailsController {



    private final ProductsDetailsService productsDetailsService;



    @PostMapping("/add")
    public ResponseEntity addProductsDetails(@Valid @RequestBody ProductDetalisDTO productDetalisDTO){

        productsDetailsService.addProductsDetails(productDetalisDTO);
        return ResponseEntity.status(200).body("Products Details added");
    }


    //Only provider
    @PutMapping("/addStock/{product_id}/{quantity}")
    public ResponseEntity addStock( @PathVariable Integer product_id, @PathVariable Integer quantity, @AuthenticationPrincipal User user){
        productsDetailsService.addStock(product_id,quantity,user.getId());
        return ResponseEntity.status(200).body("Stock added successfully");
    }
}
