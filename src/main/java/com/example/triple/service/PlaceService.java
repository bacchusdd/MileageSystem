package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.dto.PlaceSaveReqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;


    public boolean existCheck(String placeId){
        return placeRepository.existsByPlaceId(placeId);
    }

    public long countPlace(String placeId){
        return placeRepository.countByPlaceId(placeId);
    }

    public void save(PlaceSaveReqeustDto dto){
        placeRepository.save(dto.toEntity());
    }

}
