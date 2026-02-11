package com.example.webtechass2.controller.student;

import com.example.webtechass2.model.student.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private List<Student> students = new ArrayList<>();

    public StudentController() {
        // Sample data
        students.add(new Student(1L, "John", "Doe", "john@example.com", "Computer Science", 3.8));
        students.add(new Student(2L, "Jane", "Smith", "jane@example.com", "Mathematics", 3.5));
        students.add(new Student(3L, "Alice", "Johnson", "alice@example.com", "Physics", 3.2));
        students.add(new Student(4L, "Bob", "Brown", "bob@example.com", "Computer Science", 3.9));
        students.add(new Student(5L, "Charlie", "Davis", "charlie@example.com", "Engineering", 3.4));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        return students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<Student> filterStudentsByGpa(@RequestParam Double gpa) {
        return students.stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        // Simple mock ID generation
        if (student.getStudentId() == null) {
            student.setStudentId((long) (students.size() + 1));
        }
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            student.setMajor(studentDetails.getMajor());
            student.setGpa(studentDetails.getGpa());
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
