package com.example.triple.dto;

import com.example.triple.domain.pointhistory.Histories;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class HistoryUserResponseDto {

    private Long pointId;
    private String type;
    private String action;
    private String reviewId;
    private String userId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
    private LocalDateTime time;

    public HistoryUserResponseDto(Histories entity){
        this.pointId = entity.getPointId();
        this.type = entity.getType();
        this.action = entity.getAction();
        this.reviewId = entity.getReviewId();
        this.userId = entity.getUsers().getUserId();
        this.time = entity.getCreatedDate();
    }
}
