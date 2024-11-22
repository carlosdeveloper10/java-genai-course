package com.cmml.java_genAI.vector;

import io.qdrant.client.grpc.Points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class VectorService {
    
    @Autowired
    private SimpleVectorActions simpleVectorActions;

    public String conversate(String text) throws ExecutionException, InterruptedException {
        List<Points.ScoredPoint> embedingPoint = simpleVectorActions.search(text);

        return null;
    }
}
