package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.CompanyCreateCommand;
import hu.kalmancheysandor.webshop.dto.CompanyInfo;
import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductInfo;
import hu.kalmancheysandor.webshop.service.CompanyService;
import hu.kalmancheysandor.webshop.service.ProductService;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/company")
@Slf4j
public class CompanyController {




    @Autowired
    private CompanyService companyService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyInfo saveCompany(@Valid @RequestBody CompanyCreateCommand command) {
        return companyService.saveCompany(command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyInfo> listAllCompany() {
        List<CompanyInfo> companies = companyService.listAllCompany();
        return companies;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyInfo findProduct(@PathVariable("id") int companyId) {
        return companyService.findCompanyById(companyId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyInfo deleteCompany(@PathVariable("Id") int companyId){
        CompanyInfo deletedCompany = companyService.deleteCompanyById(companyId);
        return deletedCompany;
    }

}
