package com.jobNinza.clients;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.entity.Company;
import com.jobNinza.service.ATSClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Component
public class BambooHRClient implements ATSClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public BambooHRClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String fetchJobs(Company company) {
        if (!"BambooHR".equalsIgnoreCase(company.getAts())) {
            return null;
        }

        if (company.getSlug() == null || company.getSlug().isEmpty()) {
            return null;
        }

        // BambooHR API requires API key authentication
        String url = "https://api.bamboohr.com/api/gateway.php/" 
                + company.getSlug() 
                + "/v1/jobs";

        try {
            String response = restClient
                    .get()
                    .uri(url)
                    .header("Accept", "application/json")
                    // Uncomment if using API key
                    // .header("Authorization", "Basic " + company.getApiKey())
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isEmpty()) {
                return "{}";
            }
            return response;

        } catch (Exception e) {
//            throw new RuntimeException("BambooHR API call failed for: " + company.getCompanyName(), e);
        }
        return "";
    }

   

    @Override
    public String getAtsType() {
        return "BambooHR";
    }
}