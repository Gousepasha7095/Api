package Springboot.springboot.Controller;

import Springboot.springboot.Dto.StudentDto;
import Springboot.springboot.Entity.Student;
import Springboot.springboot.Response.StudentResponse;
import Springboot.springboot.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public ResponseEntity<String> create(@RequestBody StudentDto studentDto) {
        try {
            Optional<Student> exstudent = studentService.findByName(studentDto.getName());
            if ((exstudent.isPresent())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Existed by Name" + studentDto.getName());
            }
            Student student = new Student();
            student.setName(studentDto.getName());
            student.setCourse(studentDto.getCourse());

            studentService.save(student);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Creating");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllStudents(@RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer size) {

        try {
            List<Student> students;
            long totalCount;

            if (page == null || size == null) {
                students = studentService.getAllStudents();
                totalCount = students.size();
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<Student> studentPage = studentService.getAllStudents(pageable);
                students = studentPage.getContent();
                totalCount = studentPage.getTotalElements();
            }
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data Found");
            }
            StudentResponse studentResponse = new StudentResponse(students, totalCount);
            return ResponseEntity.ok(studentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retreiving students" + e.getMessage());
        }
    }

    @PutMapping("/updateStudent")
    public ResponseEntity<String> update(@RequestBody StudentDto studentDto) {
        try {
            if (studentDto.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student ID is required");
            }
            Optional<Student> exStudent = studentService.findById(studentDto.getId());
            if (exStudent.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Details not found with id" +studentDto.getId());
            }
            Student student = exStudent.get();
            student.setName(studentDto.getName());
            student.setCourse(studentDto.getCourse());
            studentService.save(student);
            return ResponseEntity.status(HttpStatus.OK).body("updated successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Updating student");
        }
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestBody StudentDto studentDto) {
        try {
            if (studentDto.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student ID is required");
            }

            Optional<Student> existingStudent = studentService.findById(studentDto.getId());
            if (existingStudent.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found with ID: " + studentDto.getId());
            }

//            studentService.deleteById(studentDto.getId());
//            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
            Student student = existingStudent.get();
            student.setDeleted(true);
            studentService.save(student);

            return ResponseEntity.status(HttpStatus.OK).body("Student soft-deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Student");
        }
    }

    @GetMapping("/search/{name}")
   public ResponseEntity<List<Student>> search(@PathVariable String name){
        return ResponseEntity.ok(studentService.search(name));
    }
}
