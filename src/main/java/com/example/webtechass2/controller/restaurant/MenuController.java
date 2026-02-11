package com.example.webtechass2.controller.restaurant;

import com.example.webtechass2.model.restaurant.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuController() {
        // Sample data - 8 items
        menuItems.add(new MenuItem(1L, "Spring Rolls", "Vegetable spring rolls", 5.99, "Appetizer", true));
        menuItems.add(new MenuItem(2L, "Chicken Wings", "Spicy chicken wings", 8.99, "Appetizer", true));
        menuItems.add(new MenuItem(3L, "Caesar Salad", "Classic Caesar salad", 9.99, "Appetizer", false));
        menuItems.add(new MenuItem(4L, "Grilled Salmon", "Salmon with vegetables", 18.99, "Main Course", true));
        menuItems.add(new MenuItem(5L, "Steak Frites", "Grilled steak with fries", 22.99, "Main Course", true));
        menuItems.add(new MenuItem(6L, "Cheesecake", "New York cheesecake", 6.99, "Dessert", true));
        menuItems.add(new MenuItem(7L, "Chocolate Cake", "Dark chocolate cake", 7.99, "Dessert", false));
        menuItems.add(new MenuItem(8L, "Iced Tea", "Refreshing lemon iced tea", 3.99, "Beverage", true));
    }

    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItems;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> getMenuItemsByCategory(@PathVariable String category) {
        return menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    @GetMapping("/available")
    public List<MenuItem> getAvailableMenuItems() { // Assuming ?available keyword was just description and endpoint is
                                                    // specific
        return menuItems.stream()
                .filter(MenuItem::isAvailable)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<MenuItem> searchMenuItems(@RequestParam String name) {
        return menuItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        if (menuItem.getId() == null) {
            menuItem.setId((long) (menuItems.size() + 1));
        }
        menuItems.add(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        Optional<MenuItem> itemOptional = menuItems.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();

        if (itemOptional.isPresent()) {
            MenuItem item = itemOptional.get();
            item.setAvailable(!item.isAvailable());
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        boolean removed = menuItems.removeIf(item -> item.getId().equals(id));
        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
