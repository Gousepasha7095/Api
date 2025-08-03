package Springboot.springboot.Dto;

import lombok.Data;

@Data
public class EmployeePayrollDto {
    private Long id;
    private String companyName;
    private int companyCode;
    private String employeeName;
    private int employeeCode;
    private long totalLPA;
    private float grossPay;
    private float netToGross;
    private float medicareTax;
    private float stateIncomeTax;
    private float federalIncomeTax;
    private float taxAmount;
    private float medicareTaxAmount;
    private float federalTaxAmount;
    private float stateIncomeTaxAmount;

    public long getTotalLPA() {
        return totalLPA;
    }

    public void setTotalLPA(long totalLPA) {
        this.totalLPA = totalLPA;
    }

    public int getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(int companyCode) {
        this.companyCode = companyCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(int employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public float getGrossPay() {
        return grossPay;
    }

    public void setGrossPay(float grossPay) {
        this.grossPay = grossPay;
    }

    public float getNetToGross() {
        return netToGross;
    }

    public void setNetToGross(float netToGross) {
        this.netToGross = netToGross;
    }

    public float getMedicareTax() {
        return medicareTax;
    }

    public void setMedicareTax(float medicareTax) {
        this.medicareTax = medicareTax;
    }

    public float getStateIncomeTax() {
        return stateIncomeTax;
    }

    public void setStateIncomeTax(float stateIncomeTax) {
        this.stateIncomeTax = stateIncomeTax;
    }

    public float getFederalIncomeTax() {
        return federalIncomeTax;
    }

    public void setFederalIncomeTax(float federalIncomeTax) {
        this.federalIncomeTax = federalIncomeTax;
    }

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public float getMedicareTaxAmount() {
        return medicareTaxAmount;
    }

    public void setMedicareTaxAmount(float medicareTaxAmount) {
        this.medicareTaxAmount = medicareTaxAmount;
    }

    public float getFederalTaxAmount() {
        return federalTaxAmount;
    }

    public void setFederalTaxAmount(float federalTaxAmount) {
        this.federalTaxAmount = federalTaxAmount;
    }

    public float getStateIncomeTaxAmount() {
        return stateIncomeTaxAmount;
    }

    public void setStateIncomeTaxAmount(float stateIncomeTaxAmount) {
        this.stateIncomeTaxAmount = stateIncomeTaxAmount;
    }




}
