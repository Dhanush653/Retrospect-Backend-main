package com.example.retrospect.websockets;


import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.retrospect.websockets.constants.Constants;
import com.example.retrospect.websockets.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SocketModule {

    @Autowired
    SocketIOServer server;
    @Autowired
    SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("message", Message.class, onChatReceived());
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info("Received message from client {}: {}", senderClient.getSessionId(), data.getContent());
            socketService.saveMessage(senderClient, data);
            server.getRoomOperations(data.getRoom()).sendEvent("receive_message", data);
        };
    }

private ConcurrentHashMap<UUID, Long> clientLastConnectionTime = new ConcurrentHashMap<>();

    private ConnectListener onConnected() {
        return client -> {
            UUID clientId = client.getSessionId();
            long now = System.currentTimeMillis();
            Long lastConnectionTime = clientLastConnectionTime.get(clientId);

            if (lastConnectionTime != null && (now - lastConnectionTime < 5000)) {
                // Ignore this connection if it happened too soon after the last one
                log.warn("Ignoring rapid reconnect for client [{}]", clientId);
                return;
            }

            clientLastConnectionTime.put(clientId, now);

            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.getOrDefault("room", List.of("default")));
            String username = String.join("", params.getOrDefault("username", List.of("Anonymous")));
            client.joinRoom(room);

            log.info("Client [{}] joined room [{}] with username [{}] at [{}]", clientId, room, username, now);

            var clients = server.getRoomOperations(room).getClients();
            log.info("Room [{}] has [{}] clients after join event", room, clients.size());
        };
    }


    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.getOrDefault("room", List.of("default")));
            String username = String.join("", params.getOrDefault("username", List.of("Anonymous")));

            log.info("Client [{}] disconnected from room [{}] with username [{}] at [{}]",
                    client.getSessionId(), room, username, System.currentTimeMillis());

            client.leaveRoom(room);
            var clients = server.getRoomOperations(room).getClients();
            log.info("Room [{}] has [{}] clients remaining after disconnect event", room, clients.size());

            // Check if the client had any specific disconnect reason
            String disconnectReason = client.getTransport().name(); // or any other reason you can derive
            log.info("Client [{}] disconnected due to [{}]", client.getSessionId(), disconnectReason);
        };
    }
}
