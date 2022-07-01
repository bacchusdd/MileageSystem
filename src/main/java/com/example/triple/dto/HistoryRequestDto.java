package com.example.triple.dto;

import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoryRequestDto {

    private String type;
    private String action;
    private String reviewId;
    //private Users users;
    private String userId;
    private int userPoint;

    /*
    public HistoryRequestDto(Histories entity) {
        this.type = entity.getType();
        this.action = entity.getAction();
        this.reviewId = entity.getReviewId();
        this.users = entity.getUsers();
    }

     */

    @Builder
    public HistoryRequestDto(String type, String action, String reviewId, String userId, int userPoint){
        this.type = type;
        this.action = action;
        this.reviewId = reviewId;
        this.userId = userId;
        this.userPoint = userPoint;
    }

    public Histories toEntity(String userId, int userPoint){
        return Histories.builder()
                .type(type)
                .action(action)
                .reviewId(reviewId)
                .users(Users.builder().userId(userId).points(userPoint).build())
                .build();
    }
}
