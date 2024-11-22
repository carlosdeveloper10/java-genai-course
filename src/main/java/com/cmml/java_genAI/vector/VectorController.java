package com.cmml.java_genAI.vector;

import com.cmml.java_genAI.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/vector")
public class VectorController {

    @Autowired
    private SimpleVectorActions simpleVectorActions;

    @Autowired
    private KnowledgeService knowledgeService;

    @PostMapping("/collection")
    private ResponseEntity saveEmbeddings() throws ExecutionException,
            InterruptedException {
        simpleVectorActions.createCollection();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/talk")
    private ResponseEntity convesate(@RequestBody Message message) throws ExecutionException,
            InterruptedException {
       return ResponseEntity.ok(knowledgeService.converse(message.input()));
    }

    @PostMapping("/knowledge")
    private ResponseEntity create(@RequestBody Message message) throws ExecutionException,
            InterruptedException {
        knowledgeService.storeKnowledge(message.input());
        return ResponseEntity.ok("Knowledge has been acquired.");
    }

}
