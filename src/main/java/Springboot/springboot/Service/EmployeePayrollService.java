package Springboot.springboot.Service;

import Springboot.springboot.Entity.EmployeePayroll;
import Springboot.springboot.Repository.EmployeePayrollRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeePayrollService {

    @Autowired
    EmployeePayrollRepo employeePayrollRepo;

    public EmployeePayroll save(EmployeePayroll employeePayroll){
        return employeePayrollRepo.save(employeePayroll);
    }

    public EmployeePayroll findByEmployeeNameAndEmployeeCode(String employeeName,int employeeCode) {
        return employeePayrollRepo.findByEmployeeNameAndEmployeeCode(employeeName,employeeCode);
    }

    public List<EmployeePayroll> getAllEmployeePayrolls() {
        return employeePayrollRepo.findAll();
    }
    public Page<EmployeePayroll>getAllEmployeePayrolls(Pageable pageable){
        return employeePayrollRepo.findAll(pageable);
    }

    public Optional<EmployeePayroll> findById(Long id) {
        return employeePayrollRepo.findById(id);
    }
}
