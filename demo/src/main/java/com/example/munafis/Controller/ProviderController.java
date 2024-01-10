package com.example.munafis.Controller;


import com.example.munafis.DTO.ProviderDTO;
import com.example.munafis.Model.*;
import com.example.munafis.Service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;



    //Admin
    @GetMapping("get")
    public ResponseEntity getAllProviders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(providerService.getAllProviders(user.getId()));
    }


    @PostMapping("/add")
    public ResponseEntity addProvider(@Valid @RequestBody ProviderDTO providerDTO){
        providerService.register(providerDTO);
        return ResponseEntity.status(200).body("provider added");
    }



    @PutMapping("/update")
    public ResponseEntity updateProvider(@Valid @RequestBody Provider provider,@AuthenticationPrincipal User user){
        providerService.updateProvider(user.getId(), provider);
        return ResponseEntity.status(200).body("provider updated");
    }


    //admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProvider(@PathVariable Integer id){
      providerService.deleteProvider(id);
        return ResponseEntity.status(200).body("provider deleted");
    }



    //ALL
    @GetMapping("/getAllProductsByProvider/{providerName}")
    public ResponseEntity getAllProductsByProvider(@PathVariable String providerName){
        Set<Product> products =providerService.getAllProductsByProvider(providerName);
        return ResponseEntity.status(200).body(products);
    }

    //all
    @GetMapping("/getAllServicesByProvider/{providerName}")
    public ResponseEntity getAllServicesByProvider(@PathVariable String providerName){
        Set<Service> services =providerService.getAllServicesByProvider(providerName);
        return ResponseEntity.status(200).body(services);
    }


    @GetMapping("/viewMyAcceptedOffers")
    public ResponseEntity viewMyAcceptedOffers(@AuthenticationPrincipal User user) {
        List<Offer> offers =providerService.viewMyAcceptedOffers(user.getId());
        return ResponseEntity.status(200).body(offers);
    }


    @GetMapping("/viewMyPendingOffers")
    public ResponseEntity viewMyPendingOffers(@AuthenticationPrincipal User user) {
        List<Offer> offers =providerService.viewMyPendingOffers(user.getId());
        return ResponseEntity.status(200).body(offers);
    }

    @GetMapping("/viewMyRejectedOffers")
    public ResponseEntity viewMyRejectedOffers(@AuthenticationPrincipal User user) {
        List<Offer> offers =providerService.viewMyRejectedOffers(user.getId());
        return ResponseEntity.status(200).body(offers);
    }
}
