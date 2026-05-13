package com.quejapp.quejapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quejapp.quejapi.dto.ChatActionDTO;
import com.quejapp.quejapi.service.ChatService;
import com.quejapp.quejapi.service.CsvService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;
    private final CsvService csvService;

    public ChatController(
            ChatService chatService,
            CsvService csvService
    ) {
        this.chatService = chatService;
        this.csvService = csvService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(
            @RequestBody Map<String, Object> request
    ) {

        try {

            String message =
                    request.get("message").toString();

            // RESPUESTA IA
            String respuestaIA =
                    chatService.preguntar(message);

            // CONVERTIR JSON → DTO
            ObjectMapper mapper = new ObjectMapper();

            ChatActionDTO dto =
                    mapper.readValue(
                            respuestaIA,
                            ChatActionDTO.class
                    );

            // SI LA IA QUIERE CREAR CSV
            if(dto.getAction().equals("create_csv")) {

                // EXPORTAR USUARIOS
                if(dto.getEntity().equals("usuarios")) {

                    String resultado =
                            csvService.exportarUsuariosCSV();

                    return Map.of(
                            "response",
                            resultado
                    );
                }

                return Map.of(
                        "response",
                        "Entidad no reconocida"
                );
            }

            if(dto.getEntity().equals("usuarios")) {

                String resultado =
                        csvService.exportarUsuariosCSV();

                return Map.of(
                        "response",
                        resultado
                );
            }
            return Map.of(
                    "response",
                    "Acción no reconocida"
            );

        } catch (Exception e) {

            e.printStackTrace();

            return Map.of(
                    "response",
                    "Error procesando IA"
            );


        }
    }
}