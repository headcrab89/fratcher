package com.webengineering.fratcher.util;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler extends TextWebSocketHandler {
    private ConcurrentHashMap<WebSocketSession, Long> clients;

    public MessageHandler() {
        clients = new ConcurrentHashMap<WebSocketSession, Long>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        if (message.getPayload().startsWith("id: ")) {
            String idText = message.getPayload().substring(4);
            Long userId = Long.valueOf(idText);

            clients.put(session, userId);
        } else if (message.getPayload().startsWith("send to: ")) {
            String idText = message.getPayload().substring(9);
            Long chatPartnerId = Long.valueOf(idText);


            if (clients.containsValue(chatPartnerId)) {
                Optional<WebSocketSession> chatPartner = clients.entrySet()
                        .stream().filter(s -> s.getValue() == chatPartnerId)
                        .map(Map.Entry::getKey).findFirst();

                if (chatPartner.isPresent()) {
                    TextMessage answerMsg = new TextMessage("new");
                    chatPartner.get().sendMessage(answerMsg);
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.put(session, -1L);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        clients.remove(session);
    }
}
