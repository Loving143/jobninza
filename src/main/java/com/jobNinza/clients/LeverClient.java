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
public class LeverClient implements ATSClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public LeverClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String fetchJobs(Company company) {
        if (!"Lever".equalsIgnoreCase(company.getAts())) {
            return null;
        }

        if (company.getSlug() == null || company.getSlug().isEmpty()) {
            return null;
        }

        // Lever API endpoint format
        String url = "https://api.lever.co/v1/postings/" 
                + company.getSlug() 
                + "?mode=json";

        try {
            String response = restClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .body(String.class);

            if (response == null || response.isEmpty()) {
                return "{}";
            }
            return response;

        } catch (Exception e) {
//            throw new RuntimeException("Lever API call failed for: " + company.getCompanyName(), e);
        }
        return "";
    }

//    @Override
//    public List<Jobs> parseJobs(String jsonResponse, Company company) {
//        if (jsonResponse == null || jsonResponse.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        try {
//            JsonNode root = objectMapper.readTree(jsonResponse);
//            
//            // Lever API returns array directly, not wrapped in "jobs" field
//            if (!root.isArray()) {
//                return new ArrayList<>();
//            }
//
//            List<Jobs> jobs = new ArrayList<>();
//            for (JsonNode jobNode : root) {
//                Jobs job = new Jobs();
//                job.setTitle(jobNode.path("text").asText());
//                job.setCompany(company);
//                
//                // Location might be in different fields
//                JsonNode locationNode = jobNode.path("location");
//                String location = locationNode.path("city").asText();
//                if (location.isEmpty()) {
//                    location = locationNode.path("country").asText();
//                }
//                if (location.isEmpty()) {
//                    location = locationNode.path("name").asText();
//                }
//                job.setLocation(location);
//                
//                // Lever uses "employmentType" or "type"
//                String employmentType = jobNode.path("employmentType").asText();
//                if (employmentType.isEmpty()) {
//                    employmentType = jobNode.path("type").asText();
//                }
//                
//                // Additional fields
////                job.setDescription(jobNode.path("description").asText());
////                job.setUrl(jobNode.path("hostedUrl").asText());
////                job.setPostedDate(jobNode.path("createdAt").asText());
////                
//                jobs.add(job);
//            }
//            return jobs;
//
//        } catch (Exception e) {
//            return new ArrayList<>();
//        }
//    }

    @Override
    public String getAtsType() {
        return "Lever";
    }
}