package com.jobNinza.clients;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.service.ATSClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class SmartRecruitersClient implements ATSClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public SmartRecruitersClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String fetchJobs(Company company) {
        if (!"SmartRecruiters".equalsIgnoreCase(company.getAts())) {
            return null;
        }

        if (company.getSlug() == null || company.getSlug().isEmpty()) {
            return null;
        }

        // SmartRecruiters API
        String url = "https://api.smartrecruiters.com/v1/companies/" 
                + company.getSlug() 
                + "/postings";

        try {
            String response = restClient
                    .get()
                    .uri(url)
                    .header("Accept", "application/json")
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isEmpty()) {
                return "{}";
            }
            return response;

        } catch (Exception e) {
           // throw new RuntimeException("SmartRecruiters API call failed for: " + company.getCompanyName(), e);
        }
        return "";
    }


    @Override
    public String getAtsType() {
        return "SmartRecruiters";
    }
}