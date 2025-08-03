package Springboot.springboot.Controller;

import Springboot.springboot.Entity.Order;
import Springboot.springboot.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public CompletableFuture<String> placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order.getCustomerName(), order.getProduct(), order.getQuantity());
    }
}
