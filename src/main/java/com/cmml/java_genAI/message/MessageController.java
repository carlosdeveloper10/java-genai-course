package com.cmml.java_genAI.message;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/message-analyzer")
public class MessageController {

    private final MessagesAnalyzer messagesAnalyzer;

    @Autowired
    public MessageController(MessagesAnalyzer task1Service) {
        this.messagesAnalyzer = task1Service;
    }

    @PostMapping("/contact-info")
    public ResponseEntity<Map>   chat(@RequestBody Message message) throws JsonProcessingException {
        return ResponseEntity.ok(messagesAnalyzer.analyzeIfConctatInfo(message.input()));
    }
}
