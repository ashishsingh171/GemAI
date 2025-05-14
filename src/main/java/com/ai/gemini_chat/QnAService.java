package com.ai.gemini_chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {

    //Access to APIKey and URL

    @Value("${gemini.api.url}")
    private String geniminiApiUrl;


    @Value("${gemini.api.Key}")
    private String geniminiApiKey;

    private final WebClient webClient;



    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {

        //construct payload

        Map<String,Object> requestBody=Map.of(
                "contents",new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",question)

                })
                }
        );
        // Make APi call

        String response=webClient.post()
                        .uri(geniminiApiUrl+geniminiApiKey)
                        .header("Content-Type","application/json")
                      .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        //return response
        return response;
    }
}
