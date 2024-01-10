package com.example.munafis.DTO;

import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Service;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ProviderDTO {
    private Integer user_id;

    @NotNull(message = "UserName cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;
    @Email(message = "Must be a valid email")
    @NotNull(message = "email cannot be null")
    private String email;
    @Pattern(regexp = "^(COMPANY|PROVIDER)$" , message = "Role must be Company or Provider only")
    private String role;
    @NotNull(message = "company name cannot be null")
    private String companyName;
    @NotNull(message = "business number cannot be null")
    private String businessNumber;
    @NotNull(message = "address cannot be null")
    private String address;
    private String field;
    private Set<Service> services;
    private Set<Product> products;
    private Set<Offer> offers;
}