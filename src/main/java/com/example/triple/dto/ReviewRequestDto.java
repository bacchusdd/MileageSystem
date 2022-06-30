package com.example.triple.dto;

import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.domain.user.Users;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewRequestDto {

    private String type;
    private String action;
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;
    private String userId;
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

    public Reviews toEntityReviews(int userPoint){
        return Reviews.builder()
                .reviewId(reviewId)
                .content(content)
                //.users(new Users(userId, userPoint))
                .users(Users.builder().userId(userId).points(userPoint).build())
                .places(Places.builder().placeId(placeId).build())
                .point(0)
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
