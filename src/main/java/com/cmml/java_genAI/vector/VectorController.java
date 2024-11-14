package com.cmml.java_genAI.vector;

import com.cmml.java_genAI.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/collection")
    private ResponseEntity saveEmbeddings() throws ExecutionException,
            InterruptedException {
        simpleVectorActions.createCollection();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    private ResponseEntity saveEmbeddings(@RequestBody Message message) throws ExecutionException,
            InterruptedException {
        simpleVectorActions.processAndSaveText(message.input());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    private ResponseEntity findScore(@RequestBody Message message) throws ExecutionException,
            InterruptedException {
        simpleVectorActions.search(message.input());
       return ResponseEntity.ok(simpleVectorActions.search(message.input()).toString());
    }


}
