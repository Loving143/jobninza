package com.jobNinza.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyATS {
    private String company;
    private String slug;
    private String ats;
    private boolean verified;
    private Evidence evidence;
    
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    
    public String getAts() { return ats; }
    public void setAts(String ats) { this.ats = ats; }
    
    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }
    
    public Evidence getEvidence() { return evidence; }
    public void setEvidence(Evidence evidence) { this.evidence = evidence; }
    
    // Helper method to get apply_host from evidence
    public String getApplyHost() {
        return evidence != null ? evidence.getApplyHost() : null;
    }
}