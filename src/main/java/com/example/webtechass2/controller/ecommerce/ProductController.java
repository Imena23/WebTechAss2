package com.example.webtechass2.controller.ecommerce;

import com.example.webtechass2.model.ecommerce.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();

    public ProductController() {
        // Sample data - 10 products
        products.add(new Product(1L, "Smartphone", "High-end smartphone with 5G", 699.99, "Electronics", 50, "BrandA"));
        products.add(new Product(2L, "Laptop", "Lightweight laptop for work", 999.99, "Electronics", 30, "BrandB"));
        products.add(
                new Product(3L, "Headphones", "Noise-cancelling headphones", 199.99, "Electronics", 100, "BrandA"));
        products.add(new Product(4L, "Running Shoes", "Comfortable running shoes", 89.99, "Fashion", 200, "BrandC"));
        products.add(new Product(5L, "T-Shirt", "Cotton t-shirt", 19.99, "Fashion", 500, "BrandC"));
        products.add(new Product(6L, "Coffee Maker", "Automatic coffee maker", 49.99, "Home", 20, "BrandD"));
        products.add(new Product(7L, "Blender", "High-speed blender", 39.99, "Home", 15, "BrandD"));
        products.add(new Product(8L, "Desk Lamp", "LED desk lamp", 29.99, "Home", 40, "BrandE"));
        products.add(
                new Product(9L, "Watch", "Smart watch with fitness tracking", 149.99, "Electronics", 60, "BrandB"));
        products.add(new Product(10L, "Backpack", "Durable backpack for travel", 59.99, "Fashion", 80, "BrandF"));
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit) {
        if (page != null && limit != null && page > 0 && limit > 0) {
            int skip = (page - 1) * limit;
            return products.stream().skip(skip).limit(limit).collect(Collectors.toList());
        }
        return products;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/brand/{brand}")
    public List<Product> getProductsByBrand(@PathVariable String brand) {
        return products.stream()
                .filter(p -> p.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerKeyword) ||
                        p.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    @GetMapping("/in-stock")
    public List<Product> getProductsInStock() {
        return products.stream()
                .filter(p -> p.getStockQuantity() > 0)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (product.getProductId() == null) {
            product.setProductId((long) (products.size() + 1));
        }
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        Optional<Product> productOptional = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setBrand(productDetails.getBrand());
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        Optional<Product> productOptional = products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst();

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setStockQuantity(quantity);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
