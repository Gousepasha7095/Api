package Springboot.springboot.Repository;

import Springboot.springboot.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByName(String name);

    @Query("select st from Student st where st.name like %?1%")
    List<Student> findByLike(String name);
}
