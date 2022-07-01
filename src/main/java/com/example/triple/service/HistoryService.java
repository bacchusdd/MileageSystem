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


    public void save(HistoryRequestDto dto){
        //historyRepository.save(dto.toEntity());
        historyRepository.save(dto.toEntity(dto.getUserId(), dto.getUserPoint()));
    }


    public boolean checkType(String reviewId, String type){
        return historyRepository.existsByReviewIdAndTypeContains(reviewId, type);
    }
}
