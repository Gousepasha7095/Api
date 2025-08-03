package Springboot.springboot.Repository;

import Springboot.springboot.Entity.EmployeePayroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePayrollRepo extends JpaRepository<EmployeePayroll,Long> {

    EmployeePayroll findByEmployeeNameAndEmployeeCode(String employeeName, int employeeCode);

}
