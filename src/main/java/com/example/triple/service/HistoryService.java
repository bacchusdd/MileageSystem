package com.example.triple.service;

import com.example.triple.domain.pointhistory.HistoryRepository;
import com.example.triple.dto.HistorySaveRequestDto;
import com.example.triple.dto.HistoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public List<HistoryResponseDto> findUserHistory(String userId){

        /*
        List<HistoryUserResponseDto> historyList = new ArrayList<>();
        for (Histories h : historyRepository.findByUsers_userId(userId)){
            historyList.add(new HistoryUserResponseDto(h));
        }
        return historyList;
         */

        return historyRepository.findByUsers_userId(userId)
                .stream()
                .map(HistoryResponseDto::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public List<HistoryResponseDto> findAllHistory(Pageable pageable){

        return historyRepository.findAll(pageable)
                .stream()
                .map(HistoryResponseDto::new)
                .collect(Collectors.toList());
    }


}
