package com.example.triple.dto;

import com.example.triple.domain.user.Users;
import lombok.Getter;

@Getter
public class UserPointResponseDto {

    private int points;

    public UserPointResponseDto(Users entity){

        this.points = entity.getPoints();
    }

}
