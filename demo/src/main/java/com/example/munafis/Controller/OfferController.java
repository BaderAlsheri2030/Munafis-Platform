package com.example.munafis.Controller;

import com.example.munafis.DTO.OfferDTO;
import com.example.munafis.Model.User;
import com.example.munafis.Service.OfferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offer")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @GetMapping("/get-all-offers")
    public ResponseEntity getAllOffers(){
        return ResponseEntity.status(200).body(offerService.getAllOffers());
    }

    @PostMapping("/create-offer/{rfp_id}")
    public ResponseEntity createOffer(@PathVariable Integer rfp_id, @Valid @RequestBody OfferDTO offerDTO, @AuthenticationPrincipal User user){
        offerService.addOffer(offerDTO,rfp_id,user.getId());
        return ResponseEntity.status(200).body("Offer created");
    }

    @PutMapping("/update-offer/{offer_id}")
    public ResponseEntity updateOffer(@PathVariable Integer offer_id, @Valid @RequestBody OfferDTO offerDTO,@AuthenticationPrincipal User user){
        offerService.updateOffer(offer_id,offerDTO,user.getId());
        return ResponseEntity.status(200).body("Offer updated");
    }

    @DeleteMapping("/delete-offer/{offer_id}")
    public ResponseEntity withdrawOffer(@PathVariable Integer offer_id,@AuthenticationPrincipal User user){
        offerService.withdrawOffer(offer_id, user.getId());
        return ResponseEntity.status(200).body("Offer withdrawn");
    }
}