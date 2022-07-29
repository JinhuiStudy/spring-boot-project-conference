package softfocus.space.conference.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import softfocus.space.conference.module.chat.ChatHandler;
import softfocus.space.conference.module.video.handler.VideoHandler;

import java.util.logging.SocketHandler;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new ChatHandler(), "/web-socket");

        webSocketHandlerRegistry.addHandler(new VideoHandler(), "/socket")
                .withSockJS();
    }

}
