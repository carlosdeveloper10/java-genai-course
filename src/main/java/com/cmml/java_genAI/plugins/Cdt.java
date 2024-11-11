package com.cmml.java_genAI.plugins;

public record Cdt (String ID, float amount,  Float returnn, int days){

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cdt){
            if (this.ID.equals(obj)) return true;
        }
        return false;
    }
}
