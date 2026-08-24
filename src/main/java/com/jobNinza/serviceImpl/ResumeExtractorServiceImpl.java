package com.jobNinza.serviceImpl;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobNinza.service.ResumeExtractorService;

@Service
public class ResumeExtractorServiceImpl implements ResumeExtractorService {
	private final Tika tika = new Tika();
	private static final Pattern MULTIPLE_SPACES =
            Pattern.compile("[ \\t]+");

    private static final Pattern MULTIPLE_NEW_LINES =
            Pattern.compile("\\n{3,}");

    public String extractText(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Resume file is empty");
        }

        try (PDDocument document =Loader.loadPDF(new RandomAccessReadBuffer(file.getInputStream()))) {

            PDFTextStripper stripper = new PDFTextStripper();

            // Important for multi-column resumes
            stripper.setSortByPosition(true);

            // Preserve line structure
            stripper.setLineSeparator("\n");

            // Preserve paragraphs reasonably
            stripper.setParagraphStart("");
            stripper.setParagraphEnd("");

            String rawText = stripper.getText(document);

            return cleanResumeText(rawText);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    private String cleanResumeText(String text) {

        if (text == null || text.isBlank()) {
            return "";
        }

        // Normalize Windows line endings
        text = text.replace("\r\n", "\n")
                   .replace("\r", "\n");

        // Remove unwanted control characters
        text = text.replaceAll("[\\u0000-\\u0008\\u000B\\u000C\\u000E-\\u001F]", "");

        // Normalize tabs/spaces
        text = MULTIPLE_SPACES.matcher(text).replaceAll(" ");

        // Remove spaces at beginning/end of every line
        String[] lines = text.split("\n");

        StringBuilder result = new StringBuilder();

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            // Fix common PDF extraction problems
            line = normalizeLine(line);

            if (!line.isBlank()) {
                result.append(line).append("\n");
            }
        }

        return MULTIPLE_NEW_LINES
                .matcher(result.toString())
                .replaceAll("\n\n")
                .trim();
    }

    private String normalizeLine(String line) {

        // PDF sometimes produces:
        // SpringBoot -> Spring Boot
        line = line.replaceAll("(?i)SpringBoot", "Spring Boot");

        line = line.replaceAll("(?i)SpringSecurity", "Spring Security");

        line = line.replaceAll("(?i)SpringMVC", "Spring MVC");

        line = line.replaceAll("(?i)MicroservicesArchitecture",
                "Microservices Architecture");

        line = line.replaceAll("(?i)JavaDeveloper",
                "Java Developer");

        line = line.replaceAll("(?i)ObjectOrientedProgramming",
                "Object-Oriented Programming");

        line = line.replaceAll("(?i)DesignPatterns",
                "Design Patterns");

        line = line.replaceAll("(?i)TechnicalSkills",
                "Technical Skills");

        line = line.replaceAll("(?i)WorkExperience",
                "Work Experience");

        line = line.replaceAll("(?i)ProfileSummary",
                "Profile Summary");

        line = line.replaceAll("(?i)CoreCompetencies",
                "Core Competencies");

        line = line.replaceAll("(?i)PersonalDetails",
                "Personal Details");

        // Normalize bullet characters
        line = line.replace("", "•")
                   .replace("▪", "•")
                   .replace("■", "•");

        // Normalize weird escaped characters
        line = line.replace("\\:", ":")
                   .replace("\\@", "@");

        return line.trim();
    

	
}

}
