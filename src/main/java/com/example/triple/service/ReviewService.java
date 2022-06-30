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
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void reviewEvent(ReviewRequestDto dto){

        Users users = userRepository.findByUserId(dto.getUserId());

        if (dto.getAction().trim().equals("ADD")){

            Reviews newReview = dto.toEntityReviews();

            /** User Point 기존 값으로 초기화 **/
            newReview.getUsers().initPoint(users.getPoints());

            /** 첫리뷰 보너스 point, place count 처리 **/
            //만약 해당 장소 첫 리뷰라면
            if (placeRepository.existsByPlaceId(dto.getPlaceId()) == false){

                /** point 처리 **/
                newReview.increasePoint();
                newReview.getUsers().increasePoint();

            }
            else{

                Places places = placeRepository.findByPlaceId(dto.getPlaceId());

                /** PlaceCount 기존 값으로 초기화 **/
                newReview.getPlaces().initCount(places.getPlaceCount());

                if (places.getPlaceCount() == 0){

                    /** point 처리 **/
                    newReview.increasePoint();
                    newReview.getUsers().increasePoint();

                }

                /** count 처리 **/
                newReview.getPlaces().increaseCount();
            }

            /** 작성 point 처리 **/
            //내용 작성시
            if (dto.getContent() != null) {
                newReview.increasePoint();
                //users.increasePoint();
                newReview.getUsers().increasePoint();
            }

            //사진 추가시
            if (dto.getAttachedPhotoIds().size() > 1){
                newReview.increasePoint();
                //users.increasePoint();
                newReview.getUsers().increasePoint();
            }


            /** data 추가 **/
            reviewRepository.save(newReview);
        }

        else if (dto.getAction().trim().equals("MOD")){

            Reviews reviews = reviewRepository.findByReviewId(dto.getReviewId());

            /** point 처리 **/
            //사진 모두 삭제시 -1
            if (dto.getAttachedPhotoIds().size() < 1){
                reviews.decreasePoint();
               reviews.getUsers().decreasePoint(1);
            }

            //기존에 사진 없는 경우에 사진 추가시 +1
            if (dto.getAttachedPhotoIds().size() > 0 && dto.toEntityReviews().getPoint() < 2){
                reviews.increasePoint();
                reviews.getUsers().increasePoint();
            }

            /** data 수정 **/
            //reviews.update(dto.getContent(), dto.getUserId(), dto.getPlaceId());
            //reviews.update(dto.getContent(), dto.getPlaceId());
            reviews.update(dto.getContent(), dto.toEntityPlaces());
        }

        else if (dto.getAction().trim().equals("DELETE")){

            Reviews reviews = reviewRepository.findByReviewId(dto.getReviewId());

            /** point 처리 **/
            reviews.getUsers().decreasePoint(reviews.getPoint());

            /** data 삭제 **/
            //findBy 이미 사용되었으므로 delete로 사용
            //reviewRepository.deleteByReviewId(dto.getReviewId());
            reviewRepository.delete(reviews);

        }
    }
}
