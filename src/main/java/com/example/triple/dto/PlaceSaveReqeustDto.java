package com.example.triple.dto;

import com.example.triple.domain.place.Places;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceSaveReqeustDto {

    private String placeId;
    private String address;

    public PlaceSaveReqeustDto(Places entity){
        placeId = entity.getPlaceId();
        address = entity.getAddress();
        //placeCount = entity.getPlaceCount();
    }

    public Places toEntity(){
        return Places.builder()
                .placeId(placeId)
                .address(address)
                //.placeCount(placeCount)
                .build();
    }
}
