package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.place.Places;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.PhotoRepository;
import com.example.triple.domain.reviewphoto.Photos;
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
    private final PhotoRepository photoRepository;

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

                firstBonus(newReview);

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
            photoRepository.saveAll(dto.toEntityPhotoList(newReview));
        }

        else if (dto.getAction().trim().equals("MOD")){

            Reviews pastReviews = reviewRepository.findByReviewId(dto.getReviewId());
            Reviews newReviews = dto.toEntityReviews();

            /** User Point 기존 값으로 초기화 **/
            newReviews.getUsers().initPoint(users.getPoints());
            /** Review Point 기존 값으로 초기화 **/
            newReviews.initPoint();


            /** point 처리 **/
            //사진 모두 삭제시 -1
            if (dto.getAttachedPhotoIds().size() < 1){
                pastReviews.decreasePoint();
                //pastReviews.getUsers().decreasePoint(1);
                users.decreasePoint(1);
                photoRepository.deleteInBatch(photoRepository.findAllByReviews_reviewId(dto.getReviewId()));
            }

            //기존에 사진 없는 경우에 사진 추가시 +1
            if (dto.getAttachedPhotoIds().size() > 0 && photoRepository.existsByReviews_reviewId(pastReviews.getReviewId()) == false){
                pastReviews.increasePoint();
                //pastReviews.getUsers().increasePoint();
                users.increasePoint();
            }


            /** place data 수정 **/
            //place 바뀌었다면 새로 지정
            if (pastReviews.getPlaces().getPlaceId() != dto.getPlaceId()){
                //기존 place -1
                pastReviews.getPlaces().decreaseCount();

                //new place가 새로운 place라면
                if (placeRepository.existsByPlaceId(dto.getPlaceId()) == false) {

                    /** 첫리뷰 보너스 point **/
                    if (pastReviews.getPoint() < 3) {
                        firstBonus(pastReviews);
                    }

                    //placeRepository.save(new Places(dto.getPlaceId(), 1));

                }
                else {

                    Places places = placeRepository.findByPlaceId(dto.getPlaceId());

                    /** PlaceCount 기존 값으로 초기화 **/
                    newReviews.getPlaces().initCount(places.getPlaceCount());

                    /** count 처리 **/
                    newReviews.getPlaces().increaseCount();

                }
            }
            //place가 같다면 기존 값으로 초기화
            else{
                Places places = placeRepository.findByPlaceId(dto.getPlaceId());
                /** PlaceCount 기존 값으로 초기화 **/
                newReviews.getPlaces().initCount(places.getPlaceCount());
            }


            /** data 업데이트 **/
            if (dto.toEntityPhotoList(newReviews).size() != 0) {
                photoRepository.saveAll(dto.toEntityPhotoList(newReviews));
            }

            Reviews reviews = reviewRepository.getOne(dto.getReviewId());
            reviews.update(newReviews.getContent(), newReviews.getPlaces());
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

    public void firstBonus(Reviews reviews){

        /** 새로운 place point 처리 **/
        reviews.increasePoint();
        reviews.getUsers().increasePoint();
    }
}
