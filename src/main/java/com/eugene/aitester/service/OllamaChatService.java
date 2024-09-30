package com.eugene.aitester.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaChatService {

    private final static String DEFAULT_NAME = "Normal";
    private final ChatClient chatClient;
    private String voice = DEFAULT_NAME;
    private ChatMemory chatMemory;

    @Autowired
    public OllamaChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Autowired
    public void setChatMemory(InMemoryChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    public String generate(String message, String conversationId, int chatHistoryWindowSize) {
        return this.chatClient.prompt()
                .system("You are a friendly chat bot that answers question in the voice of " + voice)
                .advisors(new MessageChatMemoryAdvisor(chatMemory, conversationId, chatHistoryWindowSize))
                .user(message)
                .call()
                .content();
    }

    public void clearMemory(String conversationId) {
        chatMemory.clear(conversationId);
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getVoice() {
        return this.voice;
    }

    public void resetVoice() {
        this.voice = DEFAULT_NAME;
    }

}
