package com.agencia.viagens.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "API de Agência de Viagens");
        response.put("version", "1.0.0");
        response.put("endpoints", Map.of(
            "destinos", "GET /destinos",
            "cadastrar", "POST /destinos (requer autenticação)",
            "usuarios", "GET /usuarios (apenas ADMIN)"
        ));
        return response;
    }
}
