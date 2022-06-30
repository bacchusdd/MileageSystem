package com.example.triple.service;

import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.pointhistory.HistoryRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.HistoryRequestDto;
import com.example.triple.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;


    public void save(String type, String action, String reviewId, UserResponseDto userDto){
        HistoryRequestDto dto = new HistoryRequestDto(type, action, reviewId, userDto.toEntity());
        //Histories h = new Histories(type, action, reviewId, userDto.toEntity());
        //historyRepository.save(dto.toEntity());
        historyRepository.save(dto.toEntity());

        //return dto;
    }


    public boolean checkType(String reviewId, String type){
        return historyRepository.existsByReviewIdAndTypeContains(reviewId, type);
    }
}
