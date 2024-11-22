package com.cmml.java_genAI.vector;

import com.microsoft.semantickernel.Kernel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class KnowledgeService {

    @Autowired
    private  KnowledgeRepository knowledgeRepository;

    @Autowired
    private  SimpleVectorActions qdrantService;

    @Autowired
    private Kernel kernel;

    public void storeKnowledge(String knowledgeBase) throws ExecutionException, InterruptedException {
        UUID id = UUID.randomUUID();
        Knowledge knowledge = new Knowledge(id.toString(), knowledgeBase);
        knowledgeRepository.save(knowledge);
        qdrantService.processAndSaveText(id, knowledgeBase);
    }

    public String converse(String text) throws ExecutionException, InterruptedException {

        List<String> nearestId = qdrantService.searchNearestEmbeddingsId(text);
        List<String> knowledges = knowledgeRepository.findAllById(nearestId)
                .stream()
                .map(Knowledge::getKnowledgeBase)
                .collect(Collectors.toList());


        return doInternalConverse(text, knowledges);
    }

    private String doInternalConverse(String query, List<String> knowledges) {
        String prompt = String.format("Question: %s\nContext: %s", query, String.join("\n", knowledges));
        return (String) kernel.invokePromptAsync(prompt)
                .block()
                .getResult();
    }

}
