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

    private ConnectListener onConnected() {
        return (client) -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.getOrDefault("room", List.of("default")));
            String username = String.join("", params.getOrDefault("username", List.of("Anonymous")));
            client.joinRoom(room);
            log.info("Client [{}] joined room [{}] with username [{}]", client.getSessionId(), room, username);

            // Log all clients in the room
            var clients = server.getRoomOperations(room).getClients();
            log.info("Room [{}] has [{}] clients", room, clients.size());

            // Log handshake data
            var handshakeData = client.getHandshakeData();
            log.info("Handshake data: {}", handshakeData);

            // Log if any session attributes are set
            log.info("Session ID [{}] - User [{}]", client.getSessionId(), client.getSessionId().toString());

            socketService.saveInfoMessage(client, String.format(Constants.WELCOME_MESSAGE, username), room, username);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.getOrDefault("room", List.of("default")));
            String username = String.join("", params.getOrDefault("username", List.of("Anonymous")));

            // Log disconnection reason if available
            log.info("Client [{}] disconnected from room [{}] with username [{}]", client.getSessionId(), room, username);

            socketService.saveInfoMessage(client, String.format(Constants.EXIST_MESSAGE, username), room, username);
            client.leaveRoom(room); // Ensure client leaves the room

            // Log remaining clients in the room
            var clients = server.getRoomOperations(room).getClients();
            log.info("Room [{}] has [{}] clients remaining", room, clients.size());
        };
    }


}
