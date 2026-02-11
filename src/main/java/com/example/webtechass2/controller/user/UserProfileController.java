package com.example.webtechass2.controller.user;

import com.example.webtechass2.model.user.ApiResponse;
import com.example.webtechass2.model.user.UserProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private List<UserProfile> users = new ArrayList<>();

    public UserProfileController() {
        // Sample data
        users.add(new UserProfile(1L, "john_doe", "john@example.com", "John Doe", 30, "USA", "Java Developer", true));
        users.add(new UserProfile(2L, "jane_smith", "jane@example.com", "Jane Smith", 25, "Canada", "Data Scientist",
                true));
        users.add(new UserProfile(3L, "bob_builder", "bob@example.com", "Bob Builder", 40, "UK", "Construction Manager",
                false));
        users.add(new UserProfile(4L, "alice_wonder", "alice@example.com", "Alice Wonderland", 22, "USA", "Student",
                true));
        users.add(new UserProfile(5L, "charlie_chaplin", "charlie@example.com", "Charlie Chaplin", 55, "UK", "Actor",
                true));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserProfile>>> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> getUserById(@PathVariable Long userId) {
        Optional<UserProfile> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        return user.map(u -> ResponseEntity.ok(new ApiResponse<>(true, "User found", u)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "User not found", null)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<UserProfile>>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge) {

        List<UserProfile> result = users.stream()
                .filter(u -> (username == null || u.getUsername().toLowerCase().contains(username.toLowerCase())))
                .filter(u -> (country == null || u.getCountry().equalsIgnoreCase(country)))
                .filter(u -> (minAge == null || u.getAge() >= minAge))
                .filter(u -> (maxAge == null || u.getAge() <= maxAge))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(true, "Search results", result));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserProfile>> createUser(@RequestBody UserProfile user) {
        if (user.getUserId() == null) {
            user.setUserId((long) (users.size() + 1));
        }
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "User profile created successfully", user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfile>> updateUser(@PathVariable Long userId,
            @RequestBody UserProfile userDetails) {
        Optional<UserProfile> userOptional = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setFullName(userDetails.getFullName());
            user.setAge(userDetails.getAge());
            user.setCountry(userDetails.getCountry());
            user.setBio(userDetails.getBio());
            user.setActive(userDetails.isActive());
            return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<UserProfile>> updateUserStatus(@PathVariable Long userId,
            @RequestParam boolean active) {
        Optional<UserProfile> userOptional = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();

        if (userOptional.isPresent()) {
            UserProfile user = userOptional.get();
            user.setActive(active);
            String status = active ? "activated" : "deactivated";
            return ResponseEntity.ok(new ApiResponse<>(true, "User " + status + " successfully", user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        boolean removed = users.removeIf(u -> u.getUserId().equals(userId));
        if (removed) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found", null));
        }
    }
}
