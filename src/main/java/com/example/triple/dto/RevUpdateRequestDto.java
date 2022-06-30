package com.example.triple.dto;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RevUpdateRequestDto {
    private String content;
    private Places places;
    private int point;

    @Builder
    public RevUpdateRequestDto(String content, Places places, int point) {
        this.content = content;
        this.places = places;
        this.point = point;
    }
}
