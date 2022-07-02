package com.example.triple.service;

import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.pointhistory.HistoryRepository;
import com.example.triple.dto.HistorySaveRequestDto;
import com.example.triple.dto.HistoryUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final HistoryRepository historyRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    public void save(HistorySaveRequestDto dto){
        //historyRepository.save(dto.toEntity());
        historyRepository.save(dto.toEntity(dto.getUserId(), dto.getUserPoint()));
    }


    public boolean checkType(String reviewId, String type){
        return historyRepository.existsByReviewIdAndTypeContains(reviewId, type);
    }

    @Transactional
    public List<HistoryUserResponseDto> findUserHistory(String userId){

        /*
        List<HistoryUserResponseDto> historyList = new ArrayList<>();
        for (Histories h : historyRepository.findByUsers_userId(userId)){
            historyList.add(new HistoryUserResponseDto(h));
        }
        return historyList;
         */

        return historyRepository.findByUsers_userId(userId)
                .stream()
                .map(HistoryUserResponseDto::new)
                .collect(Collectors.toList());

    }
}
