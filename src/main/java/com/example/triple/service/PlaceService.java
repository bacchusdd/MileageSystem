package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.dto.PlaceSaveReqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existCheck(String placeId){
        return placeRepository.existsByPlaceId(placeId);
    }

    public long countPlace(String placeId){
        return placeRepository.countByPlaceId(placeId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void save(PlaceSaveReqeustDto dto){
        placeRepository.save(dto.toEntity());
    }

}
