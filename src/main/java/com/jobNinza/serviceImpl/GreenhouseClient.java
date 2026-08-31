package com.jobNinza.serviceImpl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.entity.Company;
import com.jobNinza.entity.Jobs;
import com.jobNinza.service.ATSClient;

@Component
public class GreenhouseClient implements ATSClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GreenhouseClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public String fetchJobs(Company company) {
        if (!"Greenhouse".equalsIgnoreCase(company.getAts())) {
            return null;
        }

        if (company.getSlug() == null || company.getSlug().isEmpty()) {
           return null;
        }

        String url = "https://boards-api.greenhouse.io/v1/boards/" 
                + company.getSlug()  // This will be "pure-storage", "roku", "gopro", etc.
                + "/jobs";

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
        	e.printStackTrace();
           // new RuntimeException("Greenhouse API call failed for: " + company.getCompanyName(),e);
        }
        return "";
    }

    /**
     * Parse the Greenhouse JSON response into Job objects
     */
    public List<Jobs> parseJobs(String jsonResponse, Company company) {
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode jobsNode = root.get("jobs");
            
            if (jobsNode == null || !jobsNode.isArray()) {
            	return new ArrayList<>();
            }

            List<Jobs> jobs = new ArrayList<>();
            for (JsonNode jobNode : jobsNode) {
                Jobs job = new Jobs();
                job.setTitle(jobNode.path("title").asText());
                job.setCompany(company);
                job.setLocation(jobNode.path("location").path("name").asText());
                String employmentType = jobNode.path("employment_type").asText();
                jobs.add(job);
            }
            return jobs;

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

	@Override
	public String getAtsType() {
		return "Greenhouse";
	}
}