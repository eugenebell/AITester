package com.eugene.aitester.command;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eugene.aitester.service.OllamaChatService;

@ShellComponent
public class AICommand {

    private static final Logger LOG = LogManager.getLogger(AICommand.class);
    private OllamaChatService ollamaChatService;

    @Autowired
    public AICommand(OllamaChatService ocs) {
        this.ollamaChatService = ocs;
    }

    @ShellMethod(key = "hello-world")
    public String helloWorld(
            @ShellOption(defaultValue = "spring") String arg) {
        return "Hello world " + arg;
    }

    @ShellMethod(key = "q")
    public String askQuestionwithKeyword(@ShellOption(defaultValue = "I have a question") String arg) {
        String question = formatStringToSentence(arg);
        // q LOG.info("- Args for q command is :" + question);
        Map<String, String> res = ollamaChatService.generate(question);
        Collection<String> vals = res.values();
        String getFirst = null;
        for (String s : vals) {
            // LOG.info("Response is :" + s);
            getFirst = s;
        }
        return getFirst;

    }

    private String formatStringToSentence(String args) {
        return args.replaceAll(",", " ");
    }

}
