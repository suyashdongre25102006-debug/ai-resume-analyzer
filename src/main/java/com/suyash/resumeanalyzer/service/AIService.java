package com.suyash.resumeanalyzer.service;
import com.suyash.resumeanalyzer.model.Resume;
import com.suyash.resumeanalyzer.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIService
{
    private final ResumeRepository repository;
    @Value("${groq.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public AIService(ResumeRepository repository)
    {
        this.repository=repository;
        this.webClient = WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1")
                .build();
    }
    public String analyzeResume(String text)
    {
        String prompt = """
                Analyze this resume strictly based on the information provided.
                
                Do not assume achievements that are not explicitly mentioned.
                Give the in depth analsis of the uploaded resume
                Give:
                1. Strengths
                2. Weaknesses
                3. Skills detected
                4. Suggestions
                
                Be realistic and objective.
                Resume:
                """ + text;

        Map<String, Object> body = Map.of(
                "model", "llama-3.1-8b-instant",

                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                )
        );
        try
        {
            Map response = webClient.post()
                    .uri("/chat/completions")
                    .header("Authorization",
                            "Bearer " + apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List choices =
                    (List) response.get("choices");

            Map firstChoice =
                    (Map) choices.get(0);

            Map message =
                    (Map) firstChoice.get("message");

            String content =
                    (String) message.get("content");
            Resume resume = new Resume();

            resume.setFileName("uploaded_resume.pdf");
            resume.setExtractedText(text);
            resume.setAnalysis(content);

            repository.save(resume);

            return content;
        }
        catch (Exception e)
        {
            return "Groq Error 😭 : "
                    + e.getMessage();
        }
    }
}