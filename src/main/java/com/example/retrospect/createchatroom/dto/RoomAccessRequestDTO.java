package com.example.retrospect.createchatroom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAccessRequestDTO {
    private String email;
    private long roomId;
}
