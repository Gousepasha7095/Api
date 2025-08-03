package Springboot.springboot.Response;

import Springboot.springboot.Dto.CompanyDto;
import Springboot.springboot.Entity.Company;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CompanyResponse {

    private List<Company> companies;
    private long totalCount;

    public CompanyResponse(List<Company> companies, long totalCount) {
        this.companies = companies;
        this.totalCount = totalCount;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
