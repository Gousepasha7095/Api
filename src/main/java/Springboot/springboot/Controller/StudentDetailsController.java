package Springboot.springboot.Controller;

import Springboot.springboot.Dto.StudentDetailsDto;
import Springboot.springboot.Entity.Student;
import Springboot.springboot.Entity.StudentDetails;
import Springboot.springboot.Response.StudentDetailsResponse;
import Springboot.springboot.Service.StudentDetailsService;
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

@RestController
@RequestMapping("/auth")
public class StudentDetailsController {
    @Autowired
    StudentDetailsService studentDetailsService;

    @Autowired
    StudentService studentService;

    @PostMapping("/addDetails")
    public ResponseEntity<String> create(@RequestBody StudentDetailsDto studentDetailsDto){

            Optional<Student> studentName = studentService.findByName(studentDetailsDto.getName());
            if(!studentName.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student Name Not found"+studentDetailsDto.getName());
            }
            StudentDetails exstudentDetails = studentDetailsService.findByStudentIdAndName(studentDetailsDto.getStudentId(),studentDetailsDto.getName());
            if (exstudentDetails != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Existed by"+studentDetailsDto.getName()+studentDetailsDto.getStudentId());
            }
            try{
                StudentDetails studentDetails = new StudentDetails();
                studentDetails.setName(studentDetailsDto.getName());
                studentDetails.setStudentId(studentDetailsDto.getStudentId());
                studentDetails.setPhoneNumber(studentDetailsDto.getPhoneNumber());
                studentDetails.setAddress(studentDetailsDto.getAddress());

                studentDetailsService.save(studentDetails);
                return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully");

            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Craeting");
            }

    }

    @GetMapping("/getStudentAllDetails")
    public ResponseEntity<?> getAllDetails(@RequestParam(required = false)Integer page,
                                           @RequestParam(required = false)Integer size){
        try{
            List<StudentDetails>studentDetails;
            long totalCount;
            if(page == null || size == null){
                studentDetails = studentDetailsService.getAllStudentDetails();
                totalCount = studentDetails.size();
            }else {
                Pageable pageable = PageRequest.of(page,size);
                Page<StudentDetails> studentDetailsPage = studentDetailsService.getAllStudentDetails(pageable);
                studentDetails = studentDetailsPage.getContent();
                totalCount = studentDetailsPage.getTotalElements();
            }
            if(studentDetails.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Deatils found");
            }
            StudentDetailsResponse studentDetailsResponse = new StudentDetailsResponse(studentDetails,totalCount);
            return ResponseEntity.ok(studentDetailsResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retriving");
        }
    }

    @PutMapping("/updateStudentDetails")
    public ResponseEntity<String>update(@RequestBody StudentDetailsDto studentDetailsDto){
        try{
            Optional<StudentDetails> exstudentDetails = studentDetailsService.findById(studentDetailsDto.getId());
            if (exstudentDetails.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found with id"+studentDetailsDto.getId());
            }
            StudentDetails studentDetails = exstudentDetails.get();
            studentDetails.setStudentId(studentDetailsDto.getStudentId());
            studentDetails.setPhoneNumber(studentDetailsDto.getPhoneNumber());
            studentDetails.setAddress(studentDetailsDto.getAddress());

            studentDetailsService.save(studentDetails);
            return ResponseEntity.status(HttpStatus.OK).body("Updated Sucessfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating");
        }
    }

    @DeleteMapping("/deleteDetails")
    public ResponseEntity<String> delete(@RequestBody StudentDetailsDto studentDetailsDto){
        try{
            Optional<StudentDetails> exstudentDetails = studentDetailsService.findById(studentDetailsDto.getId());
            if (exstudentDetails.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found with id"+studentDetailsDto.getId());
            }
            StudentDetails studentDetails = exstudentDetails.get();
            studentDetails.setDeleted(true);
            studentDetailsService.save(studentDetails);
            return ResponseEntity.status(HttpStatus.OK).body("Student soft-deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Student");
        }
    }
}
