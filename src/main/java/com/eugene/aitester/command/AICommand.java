package com.eugene.aitester.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eugene.aitester.service.OllamaChatService;

@ShellComponent
public class AICommand {

    private static final Logger LOG = LogManager.getLogger(AICommand.class);
    private OllamaChatService ollamaChatService;
    private String conversationId = "genericIDForNow";

    @Autowired
    public AICommand(OllamaChatService ocs) {
        this.ollamaChatService = ocs;
    }

    @ShellMethod(key = { "q", "question" })
    public String askQuestionwithKeyword(@ShellOption(defaultValue = "I have a question") String arg) {
        String question = formatStringToSentence(arg);
        LOG.debug("- Args for q command is :" + question);
        return ollamaChatService.generate(question, conversationId, 10);
    }

    @ShellMethod(key = { "c", "clear", "wipe" })
    public void clearMemoryAndHistory() {
        ollamaChatService.clearMemoryAndHistory(conversationId);
    }

    @ShellMethod(key = { "v", "voice", "set voice" })
    public void setVoice(String arg) {
        ollamaChatService.setVoice(formatStringToSentence(arg));
    }

    @ShellMethod(key = { "rv", "reset voice" })
    public void resetVoice(String arg) {
        ollamaChatService.resetVoice();
    }

    @ShellMethod(key = { "gv", "get voice" })
    public void getVoice() {
        ollamaChatService.getVoice();
    }

    @ShellMethod(key = { "w", "write" })
    public void writeToFile(String path) {
        String file = formatStringToSentence(path);
        LOG.info("Output path is :" + file);
        try {
            ollamaChatService.writeToFile(file);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String formatStringToSentence(String args) {
        return args.replaceAll(",", " ");
    }

}
