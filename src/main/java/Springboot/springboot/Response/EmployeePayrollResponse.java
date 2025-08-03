package Springboot.springboot.Response;

import Springboot.springboot.Entity.EmployeePayroll;
import lombok.Data;

import java.util.List;

@Data
public class EmployeePayrollResponse {
    private List<EmployeePayroll> employeePayrolls;
    private long totalCount;

    public EmployeePayrollResponse(List<EmployeePayroll> employeePayrolls, long totalCount) {
        this.employeePayrolls=employeePayrolls;
        this.totalCount=totalCount;
    }

    public List<EmployeePayroll> getEmployeePayrolls() {
        return employeePayrolls;
    }

    public void setEmployeePayrolls(List<EmployeePayroll> employeePayrolls) {
        this.employeePayrolls = employeePayrolls;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
