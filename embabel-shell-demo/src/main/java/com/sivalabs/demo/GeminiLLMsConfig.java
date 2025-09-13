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
@ConditionalOnProperty("GEMINI_API_KEY")
public class GeminiLLMsConfig extends OpenAiCompatibleModelFactory {
    private static final String PROVIDER = "Gemini";
    private static final String GEMINI_2_0_FLASH_MODEL = "gemini-2.0-flash";

    public GeminiLLMsConfig(
            @Value("${gemini.base-url}")
            String baseUrl,
            @Value("${gemini.api-key}")
            String apiKey,
            @Value("${gemini.completions-path}")
            String completionsPath,
            @Value("${gemini.embeddings-path:''}")
            String embeddingsPath,
            @NotNull ObservationRegistry observationRegistry) {
        super(baseUrl, apiKey, completionsPath, embeddingsPath, observationRegistry);
    }

    @Bean
    Llm gemini20Flash() {
        return openAiCompatibleLlm(
                GEMINI_2_0_FLASH_MODEL,
                perTokenPricingModel(),
                PROVIDER,
                null,
                OpenAiChatOptionsConverter.INSTANCE,
                RetryUtils.DEFAULT_RETRY_TEMPLATE
        );
    }

    PerTokenPricingModel perTokenPricingModel() {
        return new PerTokenPricingModel(0, 0);
    }
}
