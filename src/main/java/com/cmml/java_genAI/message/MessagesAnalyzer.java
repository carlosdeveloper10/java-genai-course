package com.cmml.java_genAI.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MessagesAnalyzer {


    private final ChatCompletionService chatCompletionService;

    private final Kernel kernel;

    private final InvocationContext invocationContext;


    @Autowired
    public MessagesAnalyzer(ChatCompletionService chatCompletionService, Kernel kernel, InvocationContext invocationContext) {
        this.chatCompletionService = chatCompletionService;
        this.kernel = kernel;
        this.invocationContext = invocationContext;
    }

    public Map analyzeIfConctatInfo(String message) throws JsonProcessingException {

        List<ChatMessageContent<?>> response = chatCompletionService
                .getChatMessageContentsAsync(prompt.replace("[message-come-here]", message), kernel, invocationContext)
                .block();

        Map map = makeResponsePretty(response.get(1).getContent());
        map.put("originalMessage", message);
        return map;
    }

    public Map makeResponsePretty(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(message, Map.class);
    }

    private final String prompt = """
                Analyze the provided message, text, letter, question, or comment to detect any hidden phone numbers or email addresses. 
                Be mindful of various advanced techniques users might employ to obscure this information:
                
                1) **Numerical Substitution**: Users may express numbers in words (e.g., 'one two three') or combine letters and numbers (e.g., 'twenty3').
                
                2) **Language Mixing**: Users can mix languages, embedding numbers within sentences in different languages (e.g., 'uno de mis amigos tiene tres perros').
                
                3) **Contextual Embedding**: Numbers may be embedded in seemingly normal phrases, often camouflaged within common dialogue (e.g., 'I remember when we celebrated the 14th of April at 67 Main Street').
                
                4) **Alphanumeric Patterns**: Utilizing a combination of letters and numbers to form contact information in a non-standard format (e.g., 'call me at 1-800-FLOWERS').
                
                5) **Acronyms and Initialisms**: Hiding numbers or emails in the first letters of words or phrases, creating a form of acrostic (e.g., 'My number is Three Eagles Run On Four Paths').
                
                6) **Obfuscation Techniques**: Users might break apart email addresses or phone numbers using unrelated words or symbols (e.g., 'You can contact me at my new email, j.ane-dot-doe-at-example.com').
                
                7) **Indirect Descriptions**: Users can refer to numbers indirectly, using calculations or phrases that suggest numeric values (e.g., 'the sum of two and seven is what I call my favorite number').
                
                8) **Emoji and Special Characters**: Using emojis or special characters to represent parts of the contact information (e.g., 'my email is janedoe🌐example.com').
                
                9) **Coded Messages**: Crafting sentences that appear normal but are layered with meaning; the phone number may be hidden in the order of letters or syllables (e.g., 'I’ll call you at dawn, just before the sun rises at six thirty').
                
                10) **Noise Words**: Including superfluous words to distract from the hidden information (e.g., 'Please let me know your thoughts on the project and my email, which is just janedoe in a world where everything is connected like at example.com').
                
                The output must be formatted as JSON:
                
                {
                    "status": "",
                    
                    "finding": "",
                    
                    "highlight": [
                        {
                            "method": "",
                            "details": ""
                        }
                    ]
                }
                
                - **"status"** can have the following values:
                  1) NOT_PASS: A hidden phone number or email was detected.
                  2) NEED_REVIEW: A potential hidden phone number or email requires manual review.
                  3) PASS: No hidden phone number or email was found.
                
                - **"finding"** should contain the detected all hidden phone numbers and/or emails.
                
                - **"highlight"** should provide a comprehensive list of objects detailing how the hidden information was found or concealed. Each object should include:
                  - **method**: The technique used to hide the information (e.g., "numbers expressed as words").
                  - **details**: A concise explanation of how the method was applied (e.g., "the number 82 was articulated as 'eighty-two'").
                 
                **Examples of hidden information**:
                - Users may conceal numbers within regular sentences: 'I will be there on the 14th of April, at 67 Main St, just before 9 pm.' 
                - In this instance, the number '31486799' could be hidden within the context.
                - Another technique: 'You can reach me at three one four, followed by eight six seven and nine nine three one, on my birthday.'
                
                Ensure thorough detection of cases where numbers are hidden through linguistic play or a mix of languages, such as utilizing English and Spanish to obscure numeric information.
                
                **Token Handling**:
                  If the token limit is not enough to complete the JSON response, reduce the response to the minimum required fields to maintain the JSON format.
                
                message to be analyzed: [message-come-here]
            """;
}
