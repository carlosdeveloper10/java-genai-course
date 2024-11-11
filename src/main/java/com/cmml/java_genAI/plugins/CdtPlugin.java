package com.cmml.java_genAI.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CdtPlugin {

    Set<Cdt> cdts = new HashSet();

    @DefineKernelFunction(name = "open_cdt", description = "Requires and amount and next open CDT. The amount is always provide by the user", returnDescription = "The id of the opened CDT")
    public String openCdt(@KernelFunctionParameter(description = "amount for the new CDT. Must be provide by the user", name = "amount") Float amount,
                          @KernelFunctionParameter(description = "days for the new CDT(period). Must be provide by the user", name = "days")Integer days){
        String id = UUID.randomUUID().toString().substring(0, 8);
        cdts.add(new Cdt(id, amount, 0.13f, days));
        return id;
    }


    @DefineKernelFunction(name = "retrieve_Cdt", description = "retrieves cdt by Id")
    public Cdt retrieveCdtById(@KernelFunctionParameter(description = "Retrieve only the CDT with the given ID", name = "query") String cdtId){
        return cdts.stream().filter(cdt -> cdt.ID().equals(cdtId))
                .findFirst()
                .orElse(null);
    }

    @DefineKernelFunction(name = "list_all_cdt", description = "retrieves all the CDTS")
    public Set<Cdt> retrieveCdtById(){
        return cdts;
    }
}
