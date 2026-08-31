package com.jobNinza.clients;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.entity.Company;
import com.jobNinza.service.ATSClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ICIMSClient implements ATSClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public ICIMSClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String fetchJobs(Company company) {
        if (!"iCIMS".equalsIgnoreCase(company.getAts())) {
            return null;
        }

        if (company.getSlug() == null || company.getSlug().isEmpty()) {
            return null;
        }

        // iCIMS API endpoint
        String url = "https://api.icims.com/v1/jobs?companyId=" + company.getSlug();

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
//            throw new RuntimeException("iCIMS API call failed for: " + company.getCompanyName(), e);
        }
        return "";
    } 

    @Override
    public String getAtsType() {
        return "iCIMS";
    }
}