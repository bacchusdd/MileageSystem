package com.example.triple.service;

import com.example.triple.domain.reviewphoto.PhotoRepository;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepository;


    public void saveAll(List<Photos> photos){
        photoRepository.saveAll(photos);
    }

    public boolean checkReview(String reviewId){
        return photoRepository.existsByReviews_reviewId(reviewId);
    }

    public void deletePhotos(String reviewId){
        //List<Photos> photos = photoRepository.findAllByReviews_reviewId(reviewId);
        //photoRepository.deletea(photos);
        photoRepository.deleteAllByReviews_reviewId(reviewId);
    }


}
