package com.jobNinza.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobNinza.Response.OllamaResponse;
import com.jobNinza.exception.BadRequestException;
import com.jobNinza.service.ResumeAIParserService;
import com.jobNinza.util.ResumeData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ResumeAIParserServiceImpl implements ResumeAIParserService {

    private final RestClient restClient;

    private final ObjectMapper objectMapper;

    public ResumeAIParserServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    @Override
    public ResumeData parseResume(String resumeText) {

        String prompt = buildPrompt(resumeText);
        System.out.println(prompt+" This is my Prompt");
        OllamaResponse response=null;
        try {
             response = restClient.post()
                    .uri("/api/chat")
                    .body(Map.of(
                            "model", "gemma3:4b",
                            "messages", List.of(
                                    Map.of(
                                            "role", "user",
                                            "content", prompt
                                    )
                            ),
                            "stream", false
                    ))
                    .retrieve()
                    .body(OllamaResponse.class);
        }
        catch(Exception e){
            throw new BadRequestException("I am getting ex",e);
        }
        try {
            String json = response.getMessage().getContent();
            return objectMapper.readValue(
                    json,
                    ResumeData.class
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse resume JSON",
                    e
            );
        }
    }

    private String buildPrompt(String resumeText) {

        return """
                You are a resume information extraction system.

                Extract information from the resume below.

                Return ONLY valid JSON.
                Do not return markdown.
                Do not return ```json.
                Do not add explanations.

                JSON structure:

                {
                  "name": "",
                  "email": "",
                  "phone": "",
                  "location": "",
                  "summary": "",
                  "skills": [],
                  "education": [],
                  "experience": [],
                  "projects": []
                }

                If information is not present, use null or an empty array.

                Resume:
                
                %s
                """.formatted(resumeText);
    }
}