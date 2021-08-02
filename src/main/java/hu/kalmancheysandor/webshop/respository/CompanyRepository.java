package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Company;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CompanyRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Company saveCompany(Company company) {
        entityManager.persist(company);
        return company;
    }

    public Company findCompanyById(Integer companyId) {
        Company company = entityManager.find(Company.class, companyId);
        if (company == null) {
            throw new RecordNotFoundByIdException(companyId);
        }
        return company;
    }

    public List<Company> listAllCompany() {
        List<Company> company = entityManager.createQuery("SELECT c FROM Company c", Company.class).getResultList();
        return company;
    }

    public Company updateCompany(Company company) {
        Company updated = entityManager.merge(company);
        return updated;
    }

    public Company deleteCompanyById(int companyId) {
        Company companyToDelete = findCompanyById(companyId);
        entityManager.remove(companyToDelete);
        return companyToDelete;
    }

    public Company deleteCompany(Company company) {
        entityManager.remove(company);
        return company;
    }
}
