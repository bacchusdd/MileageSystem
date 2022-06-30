package com.example.triple.service;

import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.place.Places;
import com.example.triple.domain.pointhistory.Histories;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.review.Reviews;
import com.example.triple.domain.reviewphoto.PhotoRepository;
import com.example.triple.domain.reviewphoto.Photos;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.domain.user.Users;
import com.example.triple.dto.HistoryRequestDto;
import com.example.triple.dto.PlaceResponseDto;
import com.example.triple.dto.ReviewRequestDto;
import com.example.triple.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserService userService;
    private final PlaceService placeService;
    private final PhotoService photoService;
    private final HistoryService historyService;


    @Transactional
    public String addReview(ReviewRequestDto dto){
        String msg = null;

        UserResponseDto userDto = userService.findUser(dto.getUserId());
        Users users = userDto.toEntity();

        Reviews reviews = dto.toEntityReviews(users.getPoints());

        //해당 place에 review 작성 이력 있는지 확인
        if (reviewRepository.existsByUsers_userIdAndPlaces_placeId(users.getUserId(), dto.getPlaceId()) == false) {

            /** 첫리뷰 bonus point 처리 **/
            //해당 placeId가 존재하지 않음
            if (placeService.existCheck(dto.getPlaceId()) == false){
                //장소 저장
                PlaceResponseDto placeDto = new PlaceResponseDto(reviews.getPlaces());
                placeService.save(placeDto);

                //리뷰에 보너스 점수 부여
                reviews.increasePoint();

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("bonus", "increase1", dto.getReviewId(), reviews.getUsers());
                //historyService.save(historyDto);

            }
            else {
                //해당 placeId가 존재하지만 리뷰는 없는 경우
                if (reviewRepository.countByPlaces_placeId(dto.getPlaceId()) == 0){
                    //보너스 점수 부여
                    reviews.increasePoint();

                    //history 저장
                    HistoryRequestDto historyDto = new HistoryRequestDto("bonus", "increase1", dto.getReviewId(), users);
                    //historyService.save(historyDto);
                }
            }

            /** 작성 point 처리 **/
            //내용 작성시
            if (dto.getContent() != null) {
                reviews.increasePoint();

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("content", "increase1", dto.getReviewId(), users);
                //historyService.save(historyDto);
            }

            //사진 추가시
            if (dto.getAttachedPhotoIds().size() > 1){
                reviews.increasePoint();

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("photo", "increase1", dto.getReviewId(), users);
                //historyService.save(historyDto);
            }

            /** 해당 review point user에 적용 **/
            reviews.getUsers().increasePoint(reviews.getPoint());
            //users.increasePoint(reviews.getPoint());


            /** data 추가 **/
            reviewRepository.save(reviews);
            photoService.saveAll(dto.toEntityPhotoList(reviews));

        }
        else{
            msg = "이미 review를 작성한 place예요!";
            return msg;
        }

        msg = "ADD 성공";
        return msg;
    }

    @Transactional
    public String modReview(ReviewRequestDto dto){
        String msg = null;

        UserResponseDto userDto = userService.findUser(dto.getUserId());
        Users users = userDto.toEntity();

        Reviews reviews = dto.toEntityReviews(users.getPoints());

        //해당 reviewId가 존재하는지
        if (reviewRepository.existsByReviewId(dto.getReviewId()) == true){

            Reviews pastReviews = reviewRepository.findByReviewId(dto.getReviewId());

            /** point 처리 **/
            //이전에 사진 있었으나 사진 아예 삭제한 경우
            if (photoService.checkReview(dto.getReviewId()) == true && dto.getAttachedPhotoIds().size() < 1){
                //리뷰 point, 유저 point 모두 -1
                pastReviews.decreasePoint();
                users.decreasePoint(1);

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("photo", "decrease1", dto.getReviewId(), users);
                //historyService.save(historyDto);

                //기존 사진 삭제
                photoService.deletePhotos(dto.getReviewId());
            }

            //기존에 사진 없는 경우에 사진 추가시 +1
            if (photoService.checkReview(dto.getReviewId()) == false && dto.getAttachedPhotoIds().size() > 0){
                //리뷰 point, 유저 point 모두 +1
                pastReviews.increasePoint();
                users.increasePoint(1);

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("photo", "increase1", dto.getReviewId(), users);
                //historyService.save(historyDto);

                //새로운 사진 저장
                photoService.saveAll(dto.toEntityPhotoList(reviews));
            }

            /** 첫리뷰 bonus point 처리 **/
            //해당 placeId가 존재하지 않음 && 기존에 보너스 받지 않음
            if (placeService.existCheck(dto.getPlaceId()) == false && historyService.checkType(dto.getReviewId(), "bonus") == false){
                //장소 저장
                PlaceResponseDto placeDto = new PlaceResponseDto(reviews.getPlaces());
                placeService.save(placeDto);

                //리뷰에 보너스 점수 부여
                reviews.increasePoint();

                //history 저장
                HistoryRequestDto historyDto = new HistoryRequestDto("bonus", "increase1", dto.getReviewId(), users);
                //historyService.save(historyDto);

            }
            else {
                //해당 placeId가 존재하지만 리뷰는 없는 경우 && 기존에 보너스 받지 않음
                if (reviewRepository.countByPlaces_placeId(dto.getPlaceId()) == 0 && historyService.checkType(dto.getReviewId(), "bonus") == false){
                    //보너스 점수 부여
                    reviews.increasePoint();

                    //history 저장
                    HistoryRequestDto historyDto = new HistoryRequestDto("bonus", "increase1", dto.getReviewId(), users);
                    //historyService.save(historyDto);
                }
            }


        }
        else{
            msg = "존재하지 않는 review예요!";
            return msg;
        }



        msg = "MOD 성공";
        return msg;
    }

    @Transactional
    public String deleteReview(ReviewRequestDto dto){
        String msg = null;

        UserResponseDto userDto = userService.findUser(dto.getUserId());
        Users users = userDto.toEntity();

        Reviews reviews = dto.toEntityReviews(users.getPoints());


        //해당 reviewId가 존재하는지
        if (reviewRepository.existsByReviewId(dto.getReviewId()) == true){

            Reviews pastReviews = reviewRepository.findByReviewId(dto.getReviewId());

            /** user point 처리 **/
            reviews.getUsers().decreasePoint(pastReviews.getPoint());

            /** pointhistory 처리 **/
            //history 저장
            HistoryRequestDto historyDto = new HistoryRequestDto("delete", "decrease" + Integer.toString(pastReviews.getPoint()), dto.getReviewId(), users);
            //historyService.save(historyDto);

            /** data 처리 : review 삭제 */
            reviewRepository.delete(pastReviews);

        }
        else{
            msg = "존재하지 않는 review예요!";
            return msg;
        }

        msg = "DELETE 성공";
        return msg;
    }


}
