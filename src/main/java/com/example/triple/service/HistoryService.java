package com.example.triple.service;

import com.example.triple.domain.pointhistory.HistoryRepository;
import com.example.triple.dto.HistoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;


    public void save(HistoryRequestDto dto){
        historyRepository.save(dto.toEntity());
    }


    public boolean checkType(String reviewId, String type){
        return historyRepository.existsByReviewIdAndTypeContains(reviewId, type);
    }
}
