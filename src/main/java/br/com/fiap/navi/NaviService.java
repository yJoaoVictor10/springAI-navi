package br.com.fiap.navi;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class NaviService {

    private final ChatClient chatClient;

    private final String systemMessage = """
            Você é um especialista em tradução e adaptação de textos.

            Sua tarefa é reescrever o texto original utilizando
            o estilo solicitado pelo usuário.

            Regras:
            - Preserve o significado original.
            - Adapte a linguagem ao estilo solicitado.
            - Não acrescente informações desnecessárias.
            - Retorne apenas o texto traduzido.
            """;

    public NaviService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem(systemMessage)
                .build();
    }

    public String translate(String text, String style) {

        return chatClient.prompt()
                .user(u -> u.text("""
                        Traduza o texto abaixo para o estilo {style}.

                        Texto original:
                        {text}
                        """)
                        .param("style", style)
                        .param("text", text))
                .call()
                .content();
    }
}