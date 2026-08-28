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
    public ResumeAIParserServiceImpl(ObjectMapper objectMapper,RestClient restClient) {
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    @Override
    public ResumeData parseResume(String resumeText) {
        String prompt = buildPrompt(resumeText);
        OllamaResponse response=null;
        try {
             response = restClient.post().uri("/api/chat").body(Map.of("model",
                             "gemma3:4b","messages",
                             List.of(Map.of(
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

            json = json.trim();

            if (json.startsWith("```json")) {
                json = json.substring("```json".length()).trim();
            }

            if (json.startsWith("```")) {
                json = json.substring("```".length()).trim();
            }
            if (json.endsWith("```")) {
                json = json.substring(0, json.length() - "```".length()).trim();
            }
            return objectMapper.readValue( json, ResumeData.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse resume JSON",e);
        }
    }

    private String buildPrompt(String resumeText) {

        return """
            You are a resume information extraction system.

            Extract structured information from the resume below.

            IMPORTANT RULES:

            1. Return ONLY valid JSON.
            2. The first character of your response MUST be '{'.
            3. The last character of your response MUST be '}'.
            4. Do NOT return Markdown.
            5. Do NOT return ```json.
            6. Do NOT return ``` or any other code fence.
            7. Do NOT add explanations, comments, or any text before or after the JSON.
            8. Use EXACTLY the JSON structure provided below.
            9. Do NOT create any additional fields.
            10. Do NOT rename any fields.
            11. If information is not available, use null.
            12. If an array has no information, return [].
            13. Do NOT guess or invent information.
            14. Extract information exactly from the resume whenever possible.
            15. For experience dates, always separate startDate and endDate.
            16. If the candidate is currently working at a company, set endDate to "Present".

            JSON structure:

            {
              "name": null,
              "email": null,
              "phone": null,
              "location": null,
              "summary": null,
              "skills": [],
              "education": [
                {
                  "degree": null,
                  "field": null,
                  "institution": null,
                  "startDate": null,
                  "endDate": null
                }
              ],
              "experience": [
                {
                  "company": null,
                  "designation": null,
                  "startDate": null,
                  "endDate": null,
                  "description": null
                }
              ],
              "projects": [
                {
                  "name": null,
                  "description": null,
                  "technologies": []
                }
              ]
            }

            FIELD RULES:

            - "name": Candidate's full name.
            - "email": Candidate's email address.
            - "phone": Candidate's phone number.
            - "location": Candidate's location.
            - "summary": Candidate's professional summary or objective.
            - "skills": List of skills, technologies, frameworks, tools, databases, and programming languages.
            
            - "education.degree": Degree name such as B.Tech, B.E., M.Tech, MCA, etc.
            - "education.field": Field of study such as Computer Science, Information Technology, etc.
            - "education.institution": University, college, or institution name.
            - "education.startDate": Education start date if available.
            - "education.endDate": Graduation/completion date if available.

            - "experience.company": Company or organization name.
            - "experience.designation": Job title or designation.
            - "experience.startDate": Employment start date.
            - "experience.endDate": Employment end date. Use "Present" if currently employed.
            - "experience.description": Responsibilities, achievements, and work performed.

            - "projects.name": Project name.
            - "projects.description": Project description.
            - "projects.technologies": Technologies used in the project.

            DATE FORMAT RULE:

            If the resume contains:

            "May 2023 - Present"

            return:

            "startDate": "May 2023",
            "endDate": "Present"

            Do NOT return:

            "startDate": "May 2023 - Present"

            If the resume contains:

            "Apr 2021 - May 2022"

            return:

            "startDate": "Apr 2021",
            "endDate": "May 2022"

            FINAL INSTRUCTION:

            Return ONLY the JSON object.
            Do NOT return ```json.
            Do NOT return Markdown.
            Do NOT return explanations.
            Do NOT return any text outside the JSON object.

            Resume:

            %s
            """.formatted(resumeText);
    }
}