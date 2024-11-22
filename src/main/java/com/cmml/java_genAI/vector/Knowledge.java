package com.cmml.java_genAI.vector;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Knowledge {

    @Id
    private String id;

    @Column(length = 10000)
    private String knowledgeBase;

    public Knowledge(String id, String knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
        this.id = id;
    }

}
