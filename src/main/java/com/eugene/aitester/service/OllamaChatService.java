package com.eugene.aitester.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

@Service
public class OllamaChatService {

    private static final Logger LOG = LogManager.getLogger(OllamaChatService.class);
    private final static String DEFAULT_NAME = "Normal";
    private final ChatClient chatClient;
    private Map<String, String> responseTracker = new HashMap<String, String>();
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
        String resp = this.chatClient.prompt()
                .system("You are a friendly chat bot that answers question in the voice of " + voice)
                .advisors(new MessageChatMemoryAdvisor(chatMemory, conversationId, chatHistoryWindowSize))
                .user(message)
                .call()
                .content();
        int size = responseTracker.size();
        if (size == 0) {
            LOG.info("- Size is: " + size);
            responseTracker.put(responseTracker.size() + "", resp);
        } else {
            LOG.info("Size is: " + size);
            responseTracker.put(responseTracker.size() + "", resp);
        }
        return resp;
    }

    public void clearMemoryAndHistory(String conversationId) {
        chatMemory.clear(conversationId);
        responseTracker.clear();
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

    public void writeToFile(String path) throws IOException {
        int size = responseTracker.values().size();
        File f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(f);

        for (int i = 0; i < size; i++) {
            String op = responseTracker.get(i + "");
            LOG.info(op);
            outputStream.write(op.getBytes());
            outputStream.write(
                    "\n\n ----------------------------------------------------------------------------------------- \n\n"
                            .getBytes());

        }
        outputStream.close();
    }

}
