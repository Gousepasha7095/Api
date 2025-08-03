package Springboot.springboot.Controller;
import Springboot.springboot.Dto.EmployeePayrollDto;
import Springboot.springboot.Entity.Company;
import Springboot.springboot.Entity.EmployeePayroll;
import Springboot.springboot.Response.EmployeePayrollResponse;
import Springboot.springboot.Service.CompanyService;
import Springboot.springboot.Service.EmployeePayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class EmployeePayrollController {

    @Autowired
    EmployeePayrollService employeePayrollService;

    @Autowired
    CompanyService companyService;

    @PostMapping("/createEmployeePayroll")
    public ResponseEntity<?> create(@RequestBody EmployeePayrollDto employeePayrollDto){
        Company companyDetails = companyService.findByCompanyNameAndCompanyCode
                        (employeePayrollDto.getCompanyName(),employeePayrollDto.getCompanyCode());
        if (companyDetails == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Companies Details Not found"+employeePayrollDto.getCompanyName());
        }
        EmployeePayroll exemployeePayroll = employeePayrollService.findByEmployeeNameAndEmployeeCode(employeePayrollDto.getEmployeeName(), employeePayrollDto.getEmployeeCode());
        if (exemployeePayroll != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already Existed by"+employeePayrollDto.getEmployeeName()+employeePayrollDto.getEmployeeCode());
        }
        
        try{
             EmployeePayroll employeePayroll = new EmployeePayroll();

            employeePayroll.setCompanyName(employeePayrollDto.getCompanyName());
            employeePayroll.setCompanyCode(employeePayrollDto.getCompanyCode());
            employeePayroll.setEmployeeName(employeePayrollDto.getEmployeeName());
            employeePayroll.setEmployeeCode(employeePayrollDto.getEmployeeCode());

            employeePayroll.setTotalLPA(employeePayrollDto.getTotalLPA());

            float grossPay=employeePayrollDto.getTotalLPA()/12f;
            employeePayroll.setGrossPay(grossPay);

            employeePayroll.setStateIncomeTax(employeePayrollDto.getStateIncomeTax());
            float stateTaxAmount=(grossPay * employeePayrollDto.getStateIncomeTax())/100;
            employeePayroll.setStateIncomeTaxAmount(stateTaxAmount);

            employeePayroll.setFederalIncomeTax(employeePayrollDto.getFederalIncomeTax());
            float federalTaxAmount=(grossPay * employeePayrollDto.getFederalIncomeTax())/100;
            employeePayroll.setFederalTaxAmount(federalTaxAmount);

            employeePayroll.setMedicareTax(employeePayrollDto.getMedicareTax());
            float medicareTaxAmount=(grossPay * employeePayrollDto.getMedicareTax())/100;
            employeePayroll.setMedicareTaxAmount(medicareTaxAmount);

            float totalTaxAmount=stateTaxAmount+federalTaxAmount+medicareTaxAmount;
            employeePayroll.setTaxAmount(totalTaxAmount);

            float netPay=grossPay-totalTaxAmount;
            employeePayroll.setNetToGross(netPay);

            employeePayrollService.save(employeePayroll);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Added Successfully");
            return ResponseEntity.ok(Map.of("message","Employee Payroll Added Sucessfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Creating");
        }
    }

    @GetMapping("/getAllEmpPyrolls")
    public ResponseEntity<?> getAllDetails(@RequestParam(required = false)Integer page,
                                           @RequestParam(required = false)Integer size) {
        try {
            List<EmployeePayroll> employeePayrolls;
            long totalCount;
            if (page == null || size == null) {
                employeePayrolls = employeePayrollService.getAllEmployeePayrolls();
                totalCount = employeePayrolls.size();
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<EmployeePayroll> employeePayrollPage = employeePayrollService.getAllEmployeePayrolls(pageable);
                employeePayrolls = employeePayrollPage.getContent();
                totalCount = employeePayrollPage.getTotalElements();
            }
            if (employeePayrolls.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Deatils found");
            }
            EmployeePayrollResponse employeePayrollResponse = new EmployeePayrollResponse(employeePayrolls, totalCount);
            return ResponseEntity.ok(employeePayrollResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retriving");
        }
    }

        @PutMapping("/updateEmployeePayroll")
        public ResponseEntity<String>update(@RequestBody EmployeePayrollDto employeePayrollDto){
            try{
                Optional<EmployeePayroll> exemployeePayroll = employeePayrollService.findById(employeePayrollDto.getId());
                if (exemployeePayroll.isEmpty()){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found with id"+employeePayrollDto.getId());
                }
                EmployeePayroll employeePayroll = exemployeePayroll.get();
//                employeePayroll.setCompanyName(employeePayrollDto.getCompanyName());
//                employeePayroll.setCompanyCode(employeePayrollDto.getCompanyCode());
                employeePayroll.setEmployeeName(employeePayrollDto.getEmployeeName());
                employeePayroll.setEmployeeCode(employeePayrollDto.getEmployeeCode());

//                float grossPay = employeePayrollDto.getGrossPay();
                float grossPay=employeePayrollDto.getTotalLPA()/12f;
                float stateTaxAmount = (grossPay * employeePayrollDto.getStateIncomeTax()) / 100;
                float federalTaxAmount = (grossPay * employeePayrollDto.getFederalIncomeTax()) / 100;
                float medicareTaxAmount = (grossPay * employeePayrollDto.getMedicareTax()) / 100;
                float totalTaxAmount = stateTaxAmount + federalTaxAmount + medicareTaxAmount;
                float netPay = grossPay - totalTaxAmount;

                employeePayroll.setGrossPay(grossPay);
                employeePayroll.setStateIncomeTax(employeePayrollDto.getStateIncomeTax());
                employeePayroll.setFederalIncomeTax(employeePayrollDto.getFederalIncomeTax());
                employeePayroll.setMedicareTax(employeePayrollDto.getMedicareTax());

                employeePayroll.setTaxAmount(totalTaxAmount);
                employeePayroll.setNetToGross(netPay);
                employeePayroll.setFederalTaxAmount(federalTaxAmount);
                employeePayroll.setStateIncomeTaxAmount(stateTaxAmount);
                employeePayroll.setMedicareTaxAmount(medicareTaxAmount);

                employeePayrollService.save(employeePayroll);
                return ResponseEntity.status(HttpStatus.OK).body("Updated Sucessfully");
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating");
            }
        }

    @DeleteMapping("/deleteEmployeePayroll")
    public ResponseEntity<String> delete(@RequestBody EmployeePayrollDto employeePayrollDto){
        try{
            Optional<EmployeePayroll> exemployeePayroll = employeePayrollService.findById(employeePayrollDto.getId());
            if (exemployeePayroll.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No details found with id"+employeePayrollDto.getId());
            }
            EmployeePayroll employeePayroll = exemployeePayroll.get();
            employeePayroll.setDeleted(true);
            employeePayrollService.save(employeePayroll);
            return ResponseEntity.status(HttpStatus.OK).body("EmployeePayroll soft-deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting Payroll");
        }
    }

}
