package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.company.CompanyCreateCommand;
import hu.kalmancheysandor.webshop.dto.company.CompanyInfo;
import hu.kalmancheysandor.webshop.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/company")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyInfo saveCompany(@Valid @RequestBody CompanyCreateCommand command) {
        return companyService.saveCompany(command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyInfo> listAllCompany() {
        List<CompanyInfo> companys = companyService.listAllCompany();
        return companys;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyInfo findCompanyById(@PathVariable("id") int companyId) {
        return companyService.findCompanyById(companyId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyInfo deleteCompanyById(@PathVariable("id") int companyId){
        CompanyInfo deletedCompany = companyService.deleteCompanyById(companyId);
        return deletedCompany;
    }
}
