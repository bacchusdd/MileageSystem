package com.example.triple.dto;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.domain.user.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewRequestDto {

    @Schema(description = "type" , example = "REVIEW")
    private String type;

    @Schema(description = "action" , example = "ADD/MOD/DELETE")
    private String action;

    @Schema(description = "reviewId" , example = "UUID-type")
    private String reviewId;

    @Schema(description = "content", example = "1자 이상 내용")
    private String content;

    @Schema(description = "attachedPhotoIds", example = "UUID-type 형태 배열")
    private List<String> attachedPhotoIds;

    @Schema(description = "userId" , example = "UUID-type")
    private String userId;

    @Schema(description = "placeId" , example = "UUID-type")
    private String placeId;

    /*
    public ReviewRequestDto(Reviews reviews, Places places, List<Photos> photos) {
        this.reviewId = reviews.getReviewId();
        this.content = reviews.getContent();
        //this.attachedPhotoIds = photos.stream()
        //                            .map(Photos::toEntity)
        //                            .collect(Collectors.toList());
        this.userId = reviews.getUserId();
        this.placeId = reviews.getPlaceId();
    }
    */

    @Override
    public String toString(){
        return "dto [reviewId=" + reviewId + ", photos=" + attachedPhotoIds + "]";
    }

    public Reviews toEntityReviews(int userPoint, int reviewPoint){
        return Reviews.builder()
                .reviewId(reviewId)
                .content(content)
                //.users(new Users(userId, userPoint))
                .users(Users.builder().userId(userId).points(userPoint).build())
                .places(Places.builder().placeId(placeId).build())
                .point(reviewPoint)
                .build();
    }

    /*
    public Places toEntityPlaces(){
        return Places.builder()
                .placeId(placeId)
                .placeCount(1)
                .build();
    }
     */

    public List<Photos> toEntityPhotoList(Reviews reviews) {

        List<Photos> newPhotos = new ArrayList<>();

        for (String ids : attachedPhotoIds) {
            //Photos photos = new Photos(ids, reviewId);
            Photos photos = Photos.builder()
                            .attachedPhotoId(ids)
                            .reviews(reviews)
                            .build();
            newPhotos.add(photos);
        }

        return newPhotos;
    }

}
