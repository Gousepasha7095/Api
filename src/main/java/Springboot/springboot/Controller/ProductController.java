package Springboot.springboot.Controller;

import Springboot.springboot.Entity.Product;
import Springboot.springboot.Exceptions.ProductNotFoundException;
import Springboot.springboot.Repository.ProductRepo;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class ProductController {

    @Autowired
    ProductRepo productRepo;

    @PostMapping("/addProduct")
   public ResponseEntity<?> create (@RequestBody Product product)throws SQLException{
        try{
            if(product.getName() == null || product.getPrice() == null){
                throw new SQLException("Product name and price cannot be null");
            }
            Product savedProduct = productRepo.save(product);
            return ResponseEntity.ok(savedProduct);
        }catch (SQLException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable Long id)throws ProductNotFoundException{
        Optional<Product>product=productRepo.findById(id);
        if (product.isEmpty()){
            throw new ProductNotFoundException("Not Found with id"+id);
        }
        return ResponseEntity.ok(product.get());
    }
    @GetMapping("/all")
    public ResponseEntity<?>getAll()throws ProductNotFoundException {
        try {
            List<Product> products = productRepo.findAll();
            if (products.isEmpty()) {
                throw new ProductNotFoundException("No products found in database");
            }
            return ResponseEntity.ok(products);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?>updateProduct(@PathVariable Long id,@RequestBody Product product){
        try{
            Product exproduct=productRepo.findById(id)
                    .orElseThrow(()->new IOException("Product not found for update"));
            exproduct.setName(product.getName());
            exproduct.setPrice(product.getPrice());

            return ResponseEntity.ok(productRepo.save(exproduct));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws Exception {
        try {
            Product product = productRepo.findById(id)
                    .orElseThrow(() -> new Exception("Product not found for deletion"));

            productRepo.delete(product);
            return ResponseEntity.ok("Product deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
