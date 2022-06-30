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

    @Transactional
    public void reviewEvent(ReviewRequestDto dto){

        /*
        if (dto.getAction().trim().equals("ADD")){

            photoRepository.saveAll(dto.toEntityPhotoList());

        }

        else if (dto.getAction().trim().equals("MOD")){

            //for(String ids : dto.getAttachedPhotoIds()){
            for(Photos photos : dto.toEntityPhotoList()){
                //사진의 아이디는 다 다르다고 가정
                if (photoRepository.findByAttachedPhotoId(photos.getAttachedPhotoId()) == null){
                    photoRepository.save(photos);
                };
            }
        }

        else if (dto.getAction().trim().equals("DELETE")){

            photoRepository.deleteInBatch(photoRepository.findAllByReviewId(dto.getReviewId()));

        }
        */
    }

}
