package Springboot.springboot.Service;

import Springboot.springboot.Entity.Company;
import Springboot.springboot.Entity.Student;
import Springboot.springboot.Repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    public Company saveCompany(Company company){
        return companyRepo.save(company);
    }
    public Company save(Company Company){
        return companyRepo.save(Company);
    }
    public void delete(Company company){
        companyRepo.delete(company);
    }
    public Optional<Company> findById(Long id){
        return companyRepo.findById(id);
    }
    public Page<Company> getAllCompanies(Pageable pageable){
        return companyRepo.findAll(pageable);
    }
//public Page<Company> findAllActiveCompanies(Pageable pageable){
//    return companyRepo.findAllActiveCompanies(pageable);
//}
    public List<Company> getAllCompanies(){
        return companyRepo.findAll();
    }

    public Company findByCompanyNameAndCompanyCode(String companyName, int companyCode) {
        return companyRepo.findByCompanyNameAndCompanyCode(companyName,companyCode);
    }
//    public Company findByCompanyNameAndCompanyCode(String companyName, int companyCode){
//        return companyRepo.findByCompanyNameAndCompanyCode(companyName,companyCode);
//    }

  public Set<String> getAllCompanyNames(){
        return companyRepo.findAll().stream()
                .filter(f->!f.isDeleted())
                .map(Company::getCompanyName)
                .collect(Collectors.toSet());
  }

  public int getCompanyCode(String companyName){
        return companyRepo.
                findAll().stream()
                .filter(f->!f.isDeleted())
                .filter(company -> company.getCompanyName().equalsIgnoreCase(companyName))
                .map(Company::getCompanyCode)
                .findFirst()
                .orElse(0);
  }
}
