package com.example.triple.dto;

import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HistoryRequestDto {

    private String type;
    private String action;
    private String reviewId;
    private Users users;

    public HistoryRequestDto(Histories entity) {
        this.type = entity.getType();
        this.action = entity.getAction();
        this.reviewId = entity.getReviewId();
        this.users = entity.getUsers();
    }

    public Histories toEntity(){
        return Histories.builder()
                .type(type)
                .action(action)
                .reviewId(reviewId)
                .users(users)
                .build();
    }


    @Builder
    public HistoryRequestDto(String type, String action, String reviewId, Users users){
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.users = users;
    }
}
