package com.example.triple.dto;

import com.example.triple.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class UserResponseDto {

    private String userId;
    private int points;

    public UserResponseDto(Users entity){
        this.userId = entity.getUserId();
        this.points = entity.getPoints();
    }

    public Users toEntity(){
        return Users.builder()
                .userId(userId)
                .points(points)
                .build();
    }

}
