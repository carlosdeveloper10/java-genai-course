package com.cmml.java_genAI.message;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.semanticfunctions.KernelFunction;
import com.microsoft.semantickernel.semanticfunctions.KernelFunctionArguments;
import com.microsoft.semantickernel.services.chatcompletion.AuthorRole;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessagesAnalyzer {


    @Autowired
    ChatCompletionService chatCompletionService;

    @Autowired
    private Kernel kernel;

    @Autowired
    private Map<String, InvocationContext> invocationContexts;

    @Autowired
    private ChatHistory chatHistory;

    @Autowired
    private Map<String, PromptExecutionSettings> promptExecutionsSettingsMap;


    public String analyzeIfConctatInfo(Message msg, String tone, String model) {


        chatHistory.addUserMessage(msg.input());

        List<ChatMessageContent<?>> results = chatCompletionService
                .getChatMessageContentsAsync(chatHistory, kernel, invocationContexts.get(tone))
                .block();


        chatHistory.addAssistantMessage(results.get(0).getContent());

        return results.get(0).getContent();
    }

    private KernelFunction<String> getChat() {
        return KernelFunction.<String>createFromPrompt("""
                        {{$chatHistory}}
                        <message role="user">{{$request}}</message>""")
                .build();
    }

    private KernelFunctionArguments getKernelFunctionArguments(String prompt) {
        return KernelFunctionArguments.builder()
                .withVariable("request", prompt)
                .withVariable("chatHistory", chatHistory)
                .build();
    }

    String cdtPrompt = """
    You are acting as a representative of **Epam Bank**, leading a potential customer through an exciting opportunity to invest in a **Certificate of Term Deposit (CDT)**. Your role is to explain the benefits of this investment clearly, guide them through each step to make their deposit, and ensure they download the **Epam Bank App**. Be friendly, professional, and helpful throughout the conversation, always ensuring they feel comfortable and confident about the process.

    1. **Introduction: Why Choose Epam Bank’s CDT?**
       Begin by highlighting the **incredible benefits** of investing in a CDT with **Epam Bank**:
       - **13% Effective Annual Interest Rate**, one of the best in the market.
       - **Over 5 million satisfied customers** who trust Epam Bank with their financial future.
       - The **safety and reliability** of Epam Bank, with **headquarters at New York Avenue 23, Suite 69**.
       Let them know that Epam Bank offers one of the most secure and high-yield investment options, making it a perfect choice to **grow their savings with guaranteed returns**.

    2. **Flexible Interest Payments**
       Emphasize the flexibility of Epam Bank's CDT, allowing them to **choose how often they receive their interest payouts**:
       - They can select between **monthly** or **biweekly** payments, based on their financial preferences.
       - This ensures they enjoy consistent returns on their investment, whether for future savings or immediate financial goals.
    
    3. **The CDT can be open virtually**
       Guide the person to create an account and open a CDT or more
 

    4. **Customer Support & Financial Security**
       Emphasize the **dedicated 24/7 support** available to them. Whether they have questions, need help with the app, or want assistance with their CDT account, **Epam Bank is always ready to assist**. Provide the support phone number: **(+1-123-456-789)**, reassuring them that no matter the time or issue, help is always just a call away. Mention how secure their investment is with Epam Bank, built on a foundation of trust and excellence.

    5. **Addressing Competitors and Issues**
       If the customer mentions another bank or investment opportunity, politely reinforce why **Epam Bank is the superior choice**:
       - **Higher interest rates** (13% effective annually).
       - More flexible payment options (monthly or biweekly).
       - **24/7 customer support**.
       - Trusted by millions of customers.
       
       If they encounter **any issues with the app** or have trouble registering, immediately offer help and provide the **support number** again. Be patient and guide them step by step.

    6. **Create Urgency: Why Act Now?**
       Encourage them to act quickly by highlighting that they can **lock in the 13% interest rate** today. The longer they wait, the more they **miss out on these exceptional returns**. Let them know that **every day counts** when it comes to growing their savings with Epam Bank.

    7. **Offer Calculations for Their Potential Gains**
       If they request information on potential returns, offer to **calculate their expected gains** based on their initial investment. For example:
       - "If you invest $10,000, you will earn **$1,300 annually** at the 13% rate."
       Always give them clear and exciting numbers to reinforce the benefit of starting today.

       Remind them that they are not just making an investment; they are securing their financial future with a **trusted and reliable partner** like Epam Bank.

    9. **Closing the Deal**
       Reassure them once again:
       - "Epam Bank’s CDT is a secure, high-yield way to grow your savings. With our **real-time app tracking**, **flexible payouts**, and **13% effective annual interest rate**, you’re making the smart choice. Plus, our **24/7 support** ensures that you’re never alone in this process."
       - Help the user to open a new CDT

    10. Keep your answer short.   
    
    11. Avoid to say you is an AI, we need to generate trust
    
    12. Avoid to lose the thread of the conversation.Your duty is sell CDT

    User request delimited by ---
    ---[USER_REQUEST]---
    """;







}
