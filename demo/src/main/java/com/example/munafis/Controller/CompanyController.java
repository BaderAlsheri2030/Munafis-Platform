package com.example.munafis.Controller;


import com.example.munafis.DTO.CompanyDTO;
import com.example.munafis.Model.Company;
import com.example.munafis.Model.Provider;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.RfpRepository;
import com.example.munafis.Service.CompanyService;
import com.example.munafis.Service.RfpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final RfpService rfpService;


    @GetMapping("/my-company")
    public ResponseEntity getMycompany(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(companyService.getMycompany(user.getId()));
    }

    @PutMapping("/reset-password")
    public ResponseEntity resetPassword(@AuthenticationPrincipal User user,@Valid @RequestBody User user1){
        companyService.resetPassword(user1,user.getId());
        return ResponseEntity.status(200).body("password reset successfully");
    }

    //admin
    @GetMapping("/get")
    public ResponseEntity getAllCompanies(){
        return ResponseEntity.status(200).body(companyService.getAllCompanies());
    }

    // only company role
    @PostMapping("/add")
    public ResponseEntity addCompany(@Valid @RequestBody CompanyDTO companyDTO){
        companyService.register(companyDTO);
        return ResponseEntity.status(200).body("company added");
    }


    //company
    @PutMapping("/update")
    public ResponseEntity updateCompany( @Valid @RequestBody CompanyDTO company,@AuthenticationPrincipal User user){
        companyService.updateCompany(company,user.getId());
        return ResponseEntity.status(200).body("company updated");
    }

    //admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity companyService(@PathVariable Integer id){
        companyService.deleteCompany(id);
        return ResponseEntity.status(200).body("company deleted");
    }

    //company
    @GetMapping("/view-completed-orders")
    public ResponseEntity viewMyCompletedOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(companyService.viewMyCompletedOrders(user.getId()));
    }
    @GetMapping("/view-pending-orders")
    public ResponseEntity viewMyPendingOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(companyService.viewMyPendingOrders(user.getId()));
    }
    @GetMapping("/view-accepted-orders")
    public ResponseEntity viewMyAcceptedOrders(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(companyService.viewMyAcceptedOrders(user.getId()));
    }
    @GetMapping("/view-my-proposals")
    public ResponseEntity viewMyProposals(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(rfpService.viewMyProposals(user.getId()));
    }
}
