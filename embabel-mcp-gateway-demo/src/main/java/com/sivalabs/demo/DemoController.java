package com.sivalabs.demo;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);
    private final AgentPlatform agentPlatform;

    public DemoController(AgentPlatform agentPlatform) {
        this.agentPlatform = agentPlatform;
    }

    @PostMapping("/api/chat")
    SearchResult searchWikipedia(@RequestBody SearchRequest request) {
        var invocation = AgentInvocation.create(agentPlatform, SearchResult.class);
        SearchResult result = invocation.invoke(request);
        log.info("Result: {}", result);
        return result;
    }
}
