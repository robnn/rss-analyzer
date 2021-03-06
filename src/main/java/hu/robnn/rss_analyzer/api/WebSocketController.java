package hu.robnn.rss_analyzer.api;

import hu.robnn.rss_analyzer.model.RssStringHolder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishWebSocket(RssStringHolder rss) {
        template.convertAndSend("/topic/notification", rss);
    }

}
