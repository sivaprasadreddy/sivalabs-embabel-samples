package com.sivalabs.demo;

import com.embabel.agent.config.models.OpenAiChatOptionsConverter;
import com.embabel.agent.config.models.OpenAiCompatibleModelFactory;
import com.embabel.common.ai.model.Llm;
import com.embabel.common.ai.model.PerTokenPricingModel;
import io.micrometer.observation.ObservationRegistry;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("OPENROUTER_API_KEY")
public class OpenRouterLLMsConfig extends OpenAiCompatibleModelFactory {

    public OpenRouterLLMsConfig(
            @Value("${openrouter.base-url}")
            String baseUrl,
            @Value("${openrouter.api-key}")
            String apiKey,
            @Value("${openrouter.completions-path}")
            String completionsPath,
            @Value("${openrouter.embeddings-path:''}")
            String embeddingsPath,
            @NotNull ObservationRegistry observationRegistry) {
        super(baseUrl, apiKey, completionsPath, embeddingsPath, observationRegistry);
    }

    @Bean
    Llm qwen3Coder(@Value("${openrouter.chat.model}") String chatModelName) {
        return openAiCompatibleLlm(
                chatModelName,
                new PerTokenPricingModel(0, 0),
                "DeepSeek",
                null,
                OpenAiChatOptionsConverter.INSTANCE,
                RetryUtils.DEFAULT_RETRY_TEMPLATE
        );
    }
}
