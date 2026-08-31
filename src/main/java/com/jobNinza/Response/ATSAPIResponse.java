package com.jobNinza.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ATSAPIResponse {
    private List<CompanyATS> companies;
    
    public List<CompanyATS> getCompanies() { return companies; }
    public void setCompanies(List<CompanyATS> companies) { this.companies = companies; }
}