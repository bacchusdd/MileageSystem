package com.example.triple.dto;

import com.example.triple.domain.place.Places;

public class PlaceResponseDto {

    private String placeId;

    public PlaceResponseDto(Places entity){
        placeId = entity.getPlaceId();
        //placeCount = entity.getPlaceCount();
    }

    public Places toEntity(){
        return Places.builder()
                .placeId(placeId)
                //.placeCount(placeCount)
                .build();
    }
}
