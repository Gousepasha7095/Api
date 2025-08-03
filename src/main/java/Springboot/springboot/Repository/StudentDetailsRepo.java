package Springboot.springboot.Repository;

import Springboot.springboot.Entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDetailsRepo extends JpaRepository<StudentDetails,Long> {

    StudentDetails findByStudentIdAndName(int studentId, String name);
}
