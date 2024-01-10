package com.example.munafis.Controller;


import com.example.munafis.DTO.ProductDTO;
import com.example.munafis.DTO.ServiceDTO;
import com.example.munafis.Model.Product;
import com.example.munafis.Model.Service;
import com.example.munafis.Model.User;
import com.example.munafis.Service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {


    private final ServiceService serviceService;



    //ALL
    @GetMapping("/get")
    public ResponseEntity getAllServices(){
        return ResponseEntity.status(200).body(serviceService.getAllServices());
    }

    @GetMapping("/getAllByOrderByPrice")
    public ResponseEntity getAllByOrderByPrice(){
        List<Service> services = serviceService.getAllByOrderByPrice();
        return  ResponseEntity.status(200).body(services);
    }


    //ALl
    @GetMapping("/getServicesByName/{name}")
    public ResponseEntity getServicesByName(@PathVariable String name){
        List<Service> services = serviceService.getServicesByName(name);
        return  ResponseEntity.status(200).body(services);
    }



    @PostMapping("/add")
    public ResponseEntity addService(@Valid @RequestBody ServiceDTO serviceDTO,  @AuthenticationPrincipal User user){
        serviceService.addService(serviceDTO,user.getId());
        return  ResponseEntity.status(200).body("Service added");
    }

    //Only provider
    @PutMapping("/update/{id}")
    public ResponseEntity updateService(@PathVariable Integer id ,@Valid @RequestBody ServiceDTO serviceDTO ,@AuthenticationPrincipal User user){

        serviceService.updateService(id,serviceDTO,user.getId());
        return  ResponseEntity.status(200).body("Service updated");
    }



    //Only provider
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteService(@PathVariable Integer id , @AuthenticationPrincipal User user){
        serviceService.deleteService(id,user.getId());
        return  ResponseEntity.status(200).body("Service deleted");
    }
    @GetMapping("/Get-My-Services")
    public ResponseEntity getMyServices(@AuthenticationPrincipal User user){
        List<Service> services = serviceService.getMyServices(user.getId());
        return ResponseEntity.status(200).body(services);
    }

}
