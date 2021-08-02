package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Company;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.dto.CompanyCreateCommand;
import hu.kalmancheysandor.webshop.dto.CompanyInfo;
import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductInfo;
import hu.kalmancheysandor.webshop.respository.CompanyRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CompanyNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public CompanyInfo saveCompany(CompanyCreateCommand command) {
        Company companyToSave = modelMapper.map(command, Company.class);
        Company savedCompany = companyRepository.saveCompany(companyToSave);
        return modelMapper.map(savedCompany, CompanyInfo.class);
    }

    public List<CompanyInfo> listAllCompany() {
        List<CompanyInfo> companyInfos = new ArrayList<>();
        List<Company> companies = companyRepository.listAllCompany();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyInfo.class))
                .collect(Collectors.toList());
    }



    public CompanyInfo findCompanyById(int companyId) {
        try {
            Company company = companyRepository.findCompanyById(companyId);
            return modelMapper.map(company, CompanyInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CompanyNotFoundException(e.getId());
        }
    }

    public CompanyInfo deleteCompanyById(int companyId) {
        try {
            Company deletedCompany = companyRepository.deleteCompanyById(companyId);
            return modelMapper.map(deletedCompany,CompanyInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CompanyNotFoundException(e.getId());
        }
    }
}
