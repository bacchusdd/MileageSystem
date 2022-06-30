package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.ReviewRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void reviewEvent(ReviewRequestDto dto){

        if (dto.getAction().trim().equals("ADD")){

            if (placeRepository.existsByPlaceId(dto.getPlaceId()) == false){
                //placeRepository.save(dto.toEntityPlaces());

                /** point 처리 **/
                /** 첫 리뷰 **/
                //Users users = userRepository.findByUserId(dto.getUserId());
                //users.increasePoint();
            }
            else{
                Places places = placeRepository.findByPlaceId(dto.getPlaceId());

                //if (places.getPlaceCount() == 0){
                //    /** point 처리 **/
                //    /** 첫 리뷰 **/
                //    Users users = userRepository.findByUserId(dto.getUserId());
                //    users.increasePoint();
                //}

                //places.increaseCount();
            }

        }

        else if (dto.getAction().trim().equals("MOD")) {

            /*
            //dto의 reviewId에 저장된 review
            //수정 전
            Reviews pastReviews = reviewRepository.findByReviewId(dto.getReviewId());
            //Places pastPlaces = placeRepository.findByPlaceId(pastReviews.getPlaceId());
            Places pastPlaces = pastReviews.getPlaces();
            Places newPlaces = placeRepository.findByPlaceId(dto.getPlaceId());

            if (pastReviews.getPlaces().getPlaceId() != dto.getPlaceId()){
                //장소 변경
                //기존 place -1
                pastPlaces.decreaseCount();

                //new place +1
                newPlaces.increaseCount();
            }

        }

        else if (dto.getAction().trim().equals("DELETE")) {

            Places places = placeRepository.findByPlaceId(dto.getPlaceId());

            places.decreaseCount();

        }

             */

        }
    }
}
