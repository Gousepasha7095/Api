package Springboot.springboot.Response;

import Springboot.springboot.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private List<Student>students;
    private long totalCount;

    public StudentResponse(List<Student> students, long totalCount) {
        this.students=students;
        this.totalCount=totalCount;
    }

    public List<Student> getStudents() {
        return students;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
