package com.example.munafis.Controller;


import com.example.munafis.DTO.OrderDTO;
import com.example.munafis.Model.User;
import com.example.munafis.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;




    @GetMapping("/get")
    public ResponseEntity getAllOrder(){
        return ResponseEntity.status(200).body(orderService.getAllOrders());
    }
    @PostMapping("/add")
    public ResponseEntity addOrder(@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal User user){
        orderService.addOrder(orderDTO,user.getId());
        return ResponseEntity.status(200).body("order added");
    }


    @PutMapping("/update/{order_id}")
    public ResponseEntity updateOrder(@PathVariable Integer order_id,@Valid @RequestBody OrderDTO orderDTO, @AuthenticationPrincipal User user){
        orderService.updateOrder(orderDTO,user.getId(),order_id);
        return ResponseEntity.status(200).body("order updated");
    }

    @DeleteMapping("/delete/{order_id}")
    public ResponseEntity deleteOrder(@PathVariable Integer order_id,@AuthenticationPrincipal User user){
        orderService.deleteOrder(order_id,user.getId());
        return ResponseEntity.status(200).body("order deleted");
    }

    @GetMapping("/invoice/{id}")
    public ResponseEntity invoice(@PathVariable Integer id,@AuthenticationPrincipal User user){

        String invoice = orderService.invoice(id,user.getId());
        return ResponseEntity.status(200).body(invoice);
    }

    @PutMapping("/acceptOrder/{user_id}/{order_id}")
    public ResponseEntity acceptOrder(@PathVariable Integer user_id, @PathVariable Integer order_id){
        orderService.acceptOrder(user_id,order_id);
        return ResponseEntity.status(200).body("order accepted");

    }
}
