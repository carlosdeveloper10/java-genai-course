package com.cmml.java_genAI.message;


import com.azure.core.annotation.QueryParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/moderate")
public class MessageController {

    private final MessagesAnalyzer messagesAnalyzer;

    @Autowired
    public MessageController(MessagesAnalyzer task1Service) {
        this.messagesAnalyzer = task1Service;
    }

    @PostMapping("/contact-info")
    public ResponseEntity<String> chat(@RequestBody Message message,
                                    @RequestParam(name = "tone", required = false, defaultValue = "moderated") String tone) {
        return ResponseEntity.ok(messagesAnalyzer.analyzeIfConctatInfo(message, tone));
    }
}
