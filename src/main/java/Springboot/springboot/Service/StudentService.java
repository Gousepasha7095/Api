package Springboot.springboot.Service;

import Springboot.springboot.Entity.Student;
import Springboot.springboot.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    public Optional<Student> findByName(String name){
        return studentRepo.findByName(name);
    }

    public void save(Student student) {
         studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Page<Student> getAllStudents(Pageable pageable){
        return studentRepo.findAll(pageable);
    }

    public Optional<Student> findById(long id) {
        return studentRepo.findById(id);
    }

    public void deleteById(Long id) {
         studentRepo.findById(id);
    }

   public List<Student> search(String name){
        return studentRepo.findByLike(name);
   }
}
