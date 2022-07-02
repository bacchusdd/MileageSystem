package com.example.triple.service;

import com.example.triple.domain.reviewphoto.PhotoRepository;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.dto.PhotoSaveRequestDto;
import com.example.triple.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;


    //public void saveAll(List<Photos> photos){
    // photoRepository.saveAll(photos);
    //}

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void saveAll(List<PhotoSaveRequestDto> dto){
        List<Photos> photos = new ArrayList<>();
        for (PhotoSaveRequestDto d : dto){
            photos.add(d.toEntity());
        }

        photoRepository.saveAll(photos);
    }

    public boolean checkReview(String reviewId){
        return photoRepository.existsByReviews_reviewId(reviewId);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public void deletePhotos(String reviewId){
        //List<Photos> photos = photoRepository.findAllByReviews_reviewId(reviewId);
        //photoRepository.deletea(photos);
        photoRepository.deleteAllByReviews_reviewId(reviewId);
    }


}
