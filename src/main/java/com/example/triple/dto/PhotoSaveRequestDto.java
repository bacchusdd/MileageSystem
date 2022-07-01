package com.example.triple.dto;

import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.Photos;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PhotoSaveRequestDto {
    private String attachedPhotoId;
    private Reviews reviews;


    public PhotoSaveRequestDto(Photos entity){
        this.attachedPhotoId = entity.getAttachedPhotoId();
        this.reviews = entity.getReviews();
    }

    @Builder
    public PhotoSaveRequestDto(String attachedPhotoId, Reviews reviews){
        this.attachedPhotoId = attachedPhotoId;
        this.reviews = reviews;
    }

    public Photos toEntity(){
        return Photos.builder()
                .attachedPhotoId(attachedPhotoId)
                .reviews(reviews)
                .build();
    }

}
