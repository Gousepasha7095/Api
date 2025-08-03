package Springboot.springboot.Service;

import Springboot.springboot.Entity.Order;
import Springboot.springboot.Repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Async
    public CompletableFuture<String> placeOrder(String customerName, String product, int quantity) {
        Order order = new Order(customerName,product,quantity,"PENDING");
        orderRepo.save(order);
        System.out.println("🕒 Order is being processed...");

        try {
            Thread.sleep(3000); // Simulate processing delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        order.setStatus("CONFIRMED");
        orderRepo.save(order);  // Update status to "CONFIRMED"

        return CompletableFuture.completedFuture("✅ Order confirmed for " + customerName);
    }
}
