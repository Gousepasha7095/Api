package Springboot.springboot.Controller;

import Springboot.springboot.Dto.CompanyDto;
import Springboot.springboot.Entity.Company;
import Springboot.springboot.Response.CompanyResponse;
import Springboot.springboot.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
//@RequestMapping("/auth")
//@RequestMapping("/auth")
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    CompanyService companyService;

//    @Autowired
//    CompanyDto companyDto;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/addCompany")
    public ResponseEntity<?> create(@RequestBody CompanyDto companyDto) {
        try {
            Company existingCompany = companyService.findByCompanyNameAndCompanyCode(
                    companyDto.getCompanyName(), companyDto.getCompanyCode());

            if (existingCompany != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Company already exists: " + companyDto.getCompanyName());
            }
            Company company = new Company();
                   company.setCompanyName(companyDto.getCompanyName());
                    company.setCompanyCode(companyDto.getCompanyCode());
                    company.setCeo(companyDto.getCeo());
            companyService.saveCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(company);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/getAllCompanies")
    public ResponseEntity<?> getAllCompanies(@RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer size) {

        try {
            List<Company> companies;
            long totalCount;

            if (page == null || size == null) {
                companies = companyService.getAllCompanies();
                totalCount = companies.size();
            } else {
                Pageable pageable = PageRequest.of(page, size);
                Page<Company> companyPage = companyService.getAllCompanies(pageable);
                companies = companyPage.getContent();
                totalCount = companyPage.getTotalElements();
            }
            if (companies.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Data Found");
            }
            CompanyResponse companyResponse = new CompanyResponse(companies, totalCount);
            return ResponseEntity.ok(companyResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving companies" + e.getMessage());
        }
    }

//    @GetMapping("/getAllCompanies")
//    public Page<Company> getCompanies(@RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return companyService.findAllActiveCompanies(pageable);
//    }




    @PutMapping("/updateCompany")
    public ResponseEntity<?> update(@RequestBody CompanyDto companyDto) {
        try {
            if (companyDto.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company ID is required");
            }
            Optional<Company> exCompany = companyService.findById(companyDto.getId());
            if (exCompany.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Details not found with id" +companyDto.getId());
            }
            Company company = exCompany.get();
            company.setCompanyName(companyDto.getCompanyName());
            company.setCompanyCode(companyDto.getCompanyCode());
            company.setCeo(companyDto.getCeo());
            companyService.save(company);
//            return ResponseEntity.status(HttpStatus.OK).body("updated successfully");
            return ResponseEntity.ok(Map.of("message", "updated successfully"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Updating company");
        }
    }

    @DeleteMapping("/deleteCompany")
    public ResponseEntity<String> delete(@RequestBody CompanyDto companyDto) {
        try {
            if (companyDto.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company ID is required");
            }

            Optional<Company> existingCompany = companyService.findById(companyDto.getId());
            if (existingCompany.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found with ID: " + companyDto.getId());
            }

            Company company = existingCompany.get();
            company.setDeleted(true);
            companyService.save(company);

            return ResponseEntity.status(HttpStatus.OK).body("Company soft-deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Deleting company");
        }
    }

   @GetMapping("/fetchCompanyNames")
    public Set<String> fetchAllCompanyNames(){
        return companyService.getAllCompanyNames();
   }
   @GetMapping("/fetchCompanyCode")
   public ResponseEntity<Integer> fetchCompanyCode(@RequestParam String companyName){
        int companyCode=companyService.getCompanyCode(companyName);
        return ResponseEntity.ok(companyCode);
   }
}
