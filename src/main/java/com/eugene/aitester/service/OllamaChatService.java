package com.eugene.aitester.service;

import java.util.Map;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@Service
public class OllamaChatService {

    private final OllamaChatModel chatModel;

    @Autowired
    public OllamaChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Map<String, String> generate(String message) {
        return Map.of("generation", chatModel.call(message));
    }

    public Flux<ChatResponse> generateStream(String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }
}
