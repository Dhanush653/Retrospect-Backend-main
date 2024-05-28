package com.example.retrospect.websockets;


import com.corundumstudio.socketio.SocketIOClient;
import com.example.retrospect.websockets.entity.Message;
import com.example.retrospect.websockets.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocketService {

    private final MessageService messageService;

    public void sendSocketMessage(SocketIOClient senderClient, Message message, String room) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message", message);
            }
        }
    }

    public void saveMessage(SocketIOClient senderClient, Message message) {
        Message storedMessage = messageService.saveMessage(Message.builder()
                .messageType(message.getMessageType())
                .contentType(message.getContentType())
                .content(message.getContent())
                .room(message.getRoom())
                .username(message.getUsername())
                .build());
        sendSocketMessage(senderClient, storedMessage, message.getRoom());
    }

    public void saveInfoMessage(SocketIOClient senderClient, String message, String room, String username) {
        Message storedMessage = messageService.saveMessage(Message.builder()
                .content(message)
                .room(room)
                .username(username)
                .build());
        sendSocketMessage(senderClient, storedMessage, room);
    }
}

