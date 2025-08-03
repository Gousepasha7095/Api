package Springboot.springboot.Service;

import Springboot.springboot.Entity.StudentDetails;
import Springboot.springboot.Repository.StudentDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentDetailsService {
    @Autowired
    StudentDetailsRepo studentDetailsRepo;

    public StudentDetails save(StudentDetails studentDetails){
        return studentDetailsRepo.save(studentDetails);
    }

    public StudentDetails findByStudentIdAndName(int studentId, String name) {
        return studentDetailsRepo.findByStudentIdAndName(studentId,name);
    }

     public List<StudentDetails> getAllStudentDetails() {
      return  studentDetailsRepo.findAll();
    }
    public Page<StudentDetails> getAllStudentDetails(Pageable pageable){
        return studentDetailsRepo.findAll(pageable);
    }

    public Optional<StudentDetails> findById(Long id) {
        return studentDetailsRepo.findById(id);
    }
}
