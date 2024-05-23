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
            log.info("Received message from client {}: {}", senderClient.getSessionId(), data.getContent() );
            socketService.saveMessage(senderClient, data);
            // Handle sending the message to other clients
            server.getRoomOperations(data.getRoom()).sendEvent("receive_message", data);
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {

            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.get("room"));
            String username = String.join("", params.get("username"));
            client.joinRoom(room);
            socketService.saveInfoMessage(client, String.format(Constants.WELCOME_MESSAGE, username), room ,username );
            log.info("Socket ID[{}] - room[{}] - username [{}]  Connected to chat module through", client.getSessionId().toString(), room, username);
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String room = String.join("", params.get("room"));
            String username = String.join("", params.get("username"));
            socketService.saveInfoMessage(client, String.format(Constants.EXIST_MESSAGE, username), room ,username);
            log.info("Socket ID[{}] - room[{}] - username [{}]  disconnected to chat module through", client.getSessionId().toString(), room, username);
        };
    }
}
