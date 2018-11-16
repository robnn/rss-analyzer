package hu.robnn.rss_analyzer.api;

import hu.robnn.rss_analyzer.model.NodeHolder;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishWebSocket(NodeHolder data){
        template.convertAndSend("/topic/notification", data);
    }
    
    @SendTo("/topic/notification")
    public NodeHolder sendChangedNodes(NodeHolder a) {
        return a;
    }
}
