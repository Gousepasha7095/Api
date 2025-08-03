package Springboot.springboot.Response;

import Springboot.springboot.Entity.StudentDetails;
import lombok.Data;

import java.util.List;

@Data
public class StudentDetailsResponse {

    private List<StudentDetails>studentDetails;
    private long totalCount;

    public List<StudentDetails> getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(List<StudentDetails> studentDetails) {
        this.studentDetails = studentDetails;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public StudentDetailsResponse(List<StudentDetails> studentDetails, long totalCount) {
        this.studentDetails = studentDetails;
        this.totalCount = totalCount;
    }


}
