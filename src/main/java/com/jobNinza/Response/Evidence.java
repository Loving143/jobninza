package com.jobNinza.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Evidence { 
	
    @JsonProperty("apply_host")
    private String applyHost;
    
    public String getApplyHost() { return applyHost; }
    public void setApplyHost(String applyHost) { this.applyHost = applyHost; }
}