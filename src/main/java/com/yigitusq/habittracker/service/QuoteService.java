package com.yigitusq.habittracker.service;

import com.yigitusq.habittracker.dto.QuoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final RestTemplate restTemplate;

    public String getRandomQuote() {
        String url = "https://zenquotes.io/api/random";
        try {
            QuoteDTO[] response = restTemplate.getForObject(url, QuoteDTO[].class);
            if (response != null && response.length > 0) {
                // Yeni değişken isimlerini kullanıyoruz: text ve author
                return response[0].getText() + " — " + response[0].getAuthor();
            }
        } catch (Exception e) {
            System.out.println("API Hatası: " + e.getMessage());
        }
        return "Hata oluştu ama zinciri kırma!";
    }
}