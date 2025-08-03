package Springboot.springboot.Repository;

import Springboot.springboot.Entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company,Long> {
    Company findByCompanyNameAndCompanyCode(String companyName, int companyCode);


//    Company findByCompanyNameAndCompanyCode(String companyName, int companyCode);

//    @Query("SELECT c FROM Company c WHERE c.deleted = false")
//    Page<Company> findAllActiveCompanies(Pageable pageable);
}
