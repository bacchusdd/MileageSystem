package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.PlaceResponseDto;
import com.example.triple.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public void save(PlaceResponseDto dto){
        placeRepository.save(dto.toEntity());
    }

}
