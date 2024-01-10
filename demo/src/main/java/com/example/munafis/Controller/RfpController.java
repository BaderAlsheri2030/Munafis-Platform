package com.example.munafis.Controller;

import com.example.munafis.DTO.RfpDTO;
import com.example.munafis.Model.Offer;
import com.example.munafis.Model.Rfp;
import com.example.munafis.Model.User;
import com.example.munafis.Service.RfpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/proposal")
@RequiredArgsConstructor
public class RfpController {

    private final RfpService rfpService;

    @GetMapping("/get-all-proposals")
    public ResponseEntity getAllProposals() {
        return ResponseEntity.status(200).body(rfpService.getAll());
    }

    @GetMapping("/get-all-proposals-permit-all")
    public ResponseEntity getProposals() {
        return ResponseEntity.status(200).body(rfpService.getAllProposals());
    }

    //create proposal using the company id and the competition id
    @PostMapping("/create-proposal/{comp_id}")
    public ResponseEntity addProposal(@PathVariable Integer comp_id, @Valid @RequestBody RfpDTO rfpDTO,@AuthenticationPrincipal User user) {
        rfpService.addRfp(rfpDTO,comp_id,user.getId());
        return ResponseEntity.status(200).body("Proposal created");
    }

    @PutMapping("/update-proposal/{rfp_id}")
    public ResponseEntity updateProposal(@PathVariable Integer rfp_id, @Valid @RequestBody RfpDTO rfpDTO,@AuthenticationPrincipal User user) {
        rfpService.updateRfp(rfp_id, rfpDTO, user.getId());
        return ResponseEntity.status(200).body("Proposal updated");
    }

    @DeleteMapping("/delete-proposal/{rfp_id}")
    public ResponseEntity deleteProposal(@PathVariable Integer rfp_id,@AuthenticationPrincipal User user) {
        rfpService.deleteRfp(rfp_id,user.getId());
        return ResponseEntity.status(200).body("Proposal deleted");
    }

    @PutMapping("/acceptOffer/{rfp_id}/{offer_id}")
    public ResponseEntity acceptOffer(@PathVariable Integer rfp_id,@PathVariable Integer offer_id,@AuthenticationPrincipal User user) {
        rfpService.acceptOffer(rfp_id,offer_id, user.getId());
        return ResponseEntity.status(200).body("offer accepted");
    }

    @GetMapping("/viewReceivedOffers/{rfp_id}")
    public ResponseEntity viewReceivedOffers(@PathVariable Integer rfp_id,@AuthenticationPrincipal User user) {
        List<Offer> offers = rfpService.viewReceivedOffers(rfp_id,user.getId());
        return ResponseEntity.status(200).body(offers);
    }

    @GetMapping("/viewOffersByPrice/{rfp_id}/{min}/{max}")
    public ResponseEntity viewOffersByPrice(@PathVariable Integer rfp_id, @PathVariable double min, @PathVariable double max,@AuthenticationPrincipal User user) {
        List<Offer> offers = rfpService.viewOffersByPrice(rfp_id, min, max, user.getId());
        return ResponseEntity.status(200).body(offers);

    }

    //permit all
    @GetMapping("/find-proposals-ByDeadlineBefore/{date}")
    public ResponseEntity findRfpsByDeadlineBefore(@PathVariable LocalDate date) {
        Set<Rfp> rfps = rfpService.findRfpsBydeadlineBefore(date);
        return ResponseEntity.status(200).body(rfps);
    }

    //permit all
    @GetMapping("/find-proposals-Location/{location}")
    public ResponseEntity findProposalsByLocation(@PathVariable String location) {
        Set<Rfp> rfps = rfpService.findProposalsByLocation(location);
        return ResponseEntity.status(200).body(rfps);
    }

    //permit all
    @GetMapping("/findProposalsByCompanyName/{name}")
    public ResponseEntity findProposalsByCompanyName(@PathVariable String name) {
        Set<Rfp> rfps = rfpService.findProposalsByCompanyName(name);
        return ResponseEntity.status(200).body(rfps);
    }

    @PutMapping("/reject-offer/{rfp_id}/{offer_id}")
    public ResponseEntity rejectOffer(@PathVariable Integer rfp_id, @PathVariable Integer offer_id, @AuthenticationPrincipal User user) {
        rfpService.rejectOffer(rfp_id, offer_id, user.getId());
        return ResponseEntity.status(200).body("offer rejected");
    }

}
