package hu.kalmancheysandor.webshop.service.exception;

public class CompanyNotFoundException extends RuntimeException {
    private int companyId;

    public CompanyNotFoundException(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }
}
