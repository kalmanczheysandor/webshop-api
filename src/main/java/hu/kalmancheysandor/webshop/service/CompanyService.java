package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Address;
import hu.kalmancheysandor.webshop.domain.Company;
import hu.kalmancheysandor.webshop.dto.CompanyCreateCommand;
import hu.kalmancheysandor.webshop.dto.CompanyInfo;
import hu.kalmancheysandor.webshop.respository.CompanyRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CompanyNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }




    public CompanyInfo saveCompany(CompanyCreateCommand command) {
        Company companyToSave = modelMapper.map(command, Company.class);
        companyToSave.setAddress(null);
        Company savedCompany = companyRepository.saveCompany(companyToSave);

        Address addressToSave = modelMapper.map(command.getAddress(), Address.class);

        Address savedAddress = companyRepository.saveAddress(addressToSave);
        savedAddress.setCompany(savedCompany);
        savedCompany.setAddress(savedAddress);
        return modelMapper.map(savedCompany, CompanyInfo.class);
    }













//    public CompanyInfo saveCompany(CompanyCreateCommand command) {
//        Company companyToSave = modelMapper.map(command, Company.class);
//        companyToSave.setAddress(null);
//        Company savedCompany = companyRepository.saveCompany(companyToSave);
//
//        Address addressToSave = modelMapper.map(command.getAddress(), Address.class);
//        Address savedAddress = companyRepository.saveAddress(addressToSave);
//        companyToSave.setAddress(savedAddress);
//        return null;
//    }







//    public CompanyInfo saveCompany(CompanyCreateCommand command) {
//        Address addressToSave = modelMapper.map(command.getAddress(), Address.class);
//        Address savedAddress = companyRepository.saveAddress(addressToSave);
//        Company companyToSave = modelMapper.map(command, Company.class);
//        companyToSave.setAddress(savedAddress);
//        Company savedCompany = companyRepository.saveCompany(companyToSave);
//        return modelMapper.map(savedCompany, CompanyInfo.class);
//    }

//        Address addressToSave = modelMapper.map(command.getAddress(), Address.class);
//        Address savedAddress = companyRepository.saveAddress(addressToSave);
//
//        Company companyToSave = new Company();
//        companyToSave.setAddress(savedAddress);
//        companyToSave.setName(command.getName());
//
//        Company savedCompany = companyRepository.saveCompany(companyToSave);
//
//        return modelMapper.map(savedCompany, CompanyInfo.class);
    //}


//    public CompanyInfo saveCompany(CompanyCreateCommand command) {
//
//        Address addressToSave = modelMapper.map(command.getAddress(), Address.class);
//        Address savedAddress = companyRepository.saveAddress(addressToSave);
//
//        Company companyToSave = new Company();
//        companyToSave.setAddress(savedAddress);
//        companyToSave.setName(command.getName());
//
//        Company savedCompany = companyRepository.saveCompany(companyToSave);
//
//        return modelMapper.map(savedCompany, CompanyInfo.class);
//    }

    public List<CompanyInfo> listAllCompany() {
        List<Company> companys = companyRepository.listCompanies();
        return companys.stream()
                .map(item -> modelMapper.map(item, CompanyInfo.class))
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
            return modelMapper.map(deletedCompany, CompanyInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CompanyNotFoundException(e.getId());
        }
    }
}
