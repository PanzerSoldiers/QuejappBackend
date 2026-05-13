package com.quejapp.quejapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ChatService {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String preguntar(String mensaje) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            String prompt = """
            Responde SOLO en JSON válido.
            
            Acciones posibles:
            - create_csv
            
            Entidades posibles:
            - usuarios
            - pqrs
            - quejas
            
            Formato:
            
            {
              "action":"create_csv",
              "entity":"usuarios"
            }
            
            Mensaje:
            %s
            """.formatted(mensaje);

            Map<String, Object> request = Map.of(
                    "model", "llama3",
                    "prompt", prompt,
                    "stream", false
            );

            Map response = restTemplate.postForObject(
                    OLLAMA_URL,
                    request,
                    Map.class
            );

            if (response != null && response.get("response") != null) {

                return response.get("response").toString();

            } else {

                return "Error IA";
            }

        } catch (Exception e) {

            e.printStackTrace();

            return "Error al conectar con IA";
        }
    }
}