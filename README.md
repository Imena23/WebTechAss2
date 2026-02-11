# Spring Boot RESTful API Assignment

This project contains the implementation for Module 1-3 Practical Questions.
It includes REST APIs for:
1.  Library Book Management
2.  Student Registration
3.  Restaurant Menu
4.  E-Commerce Product
5.  Task Management
6.  User Profile (Bonus)

## How to Run
1.  Ensure you have Java 21 and Maven installed.
2.  Navigate to the project root directory.
3.  Run `mvn spring-boot:run`.
4.  The application will start on `http://localhost:8080`.

## API Endpoints & Usage

### 1. Library Book Management API
*   **Base URL**: `/api/books`
*   `GET /api/books`: List all books.
*   `GET /api/books/{id}`: Get book by ID.
*   `GET /api/books/search?title={title}`: Search books by title.
*   `POST /api/books`: Add a new book.
*   `DELETE /api/books/{id}`: Delete a book.

### 2. Student Registration API
*   **Base URL**: `/api/students`
*   `GET /api/students`: List all students.
*   `GET /api/students/{studentId}`: Get student by ID.
*   `GET /api/students/major/{major}`: Get students by major.
*   `GET /api/students/filter?gpa={minGpa}`: Filter students by minimum GPA.
*   `POST /api/students`: Register a new student.
*   `PUT /api/students/{studentId}`: Update student info.

### 3. Restaurant Menu API
*   **Base URL**: `/api/menu`
*   `GET /api/menu`: List all menu items.
*   `GET /api/menu/{id}`: Get menu item by ID.
*   `GET /api/menu/category/{category}`: Get items by category.
*   `GET /api/menu/available`: Get available items.
*   `GET /api/menu/search?name={name}`: Search items by name.
*   `POST /api/menu`: Add new menu item.
*   `PUT /api/menu/{id}/availability`: Toggle availability.
*   `DELETE /api/menu/{id}`: Remove menu item.

### 4. E-Commerce Product API
*   **Base URL**: `/api/products`
*   `GET /api/products`: List products (supports pagination `?page=1&limit=10`).
*   `GET /api/products/{productId}`: Get product details.
*   `GET /api/products/category/{category}`: Get products by category.
*   `GET /api/products/brand/{brand}`: Get products by brand.
*   `GET /api/products/search?keyword={keyword}`: Search products.
*   `GET /api/products/price-range?min={min}&max={max}`: Filter by price range.
*   `GET /api/products/in-stock`: Get in-stock products.
*   `POST /api/products`: Add product.
*   `PUT /api/products/{productId}`: Update product.
*   `PATCH /api/products/{productId}/stock?quantity={quantity}`: Update stock.
*   `DELETE /api/products/{productId}`: Delete product.

### 5. Task Management API
*   **Base URL**: `/api/tasks`
*   `GET /api/tasks`: List all tasks.
*   `GET /api/tasks/{taskId}`: Get task by ID.
*   `GET /api/tasks/status?completed={true/false}`: Filter by status.
*   `GET /api/tasks/priority/{priority}`: Filter by priority.
*   `POST /api/tasks`: Create task.
*   `PUT /api/tasks/{taskId}`: Update task.
*   `PATCH /api/tasks/{taskId}/complete`: Mark as completed.
*   `DELETE /api/tasks/{taskId}`: Delete task.

### Bonus. User Profile API
*   **Base URL**: `/api/users`
*   `GET /api/users`: List all users.
*   `GET /api/users/{userId}`: Get user by ID.
*   `GET /api/users/search?username={name}&country={country}...`: Search users.
*   `POST /api/users`: Create user.
*   `PUT /api/users/{userId}`: Update user.
*   `PATCH /api/users/{userId}/status?active={true/false}`: Update status.
*   `DELETE /api/users/{userId}`: Delete user.

## Sample Requests
See `requests.http` for a full list of sample HTTP requests that can be run directly in VS Code (using REST Client extension) or IntelliJ IDEA.
