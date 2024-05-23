package com.example.retrospect.createchatroom.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoomDTO {
    private int roomId;
    private String roomName;
    private String roomDescription;
    private String roomStatus = "active";
    private String roomCreatedBy;
    private String access;
    private Set<String> allowedEmails;


}