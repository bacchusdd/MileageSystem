package com.example.triple.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Reviews, String> {

    Reviews findByReviewId(String reviewId);

    //Reviews deleteByReviewId(String reviewId);

    boolean existsByUsers_userIdAndPlaces_placeId(@Param(value="userId") String userId, @Param(value = "placeId") String placeId);
    boolean existsByReviewId(String reviewId);

    long countByPlaces_placeId(@Param(value = "placeId") String placeId);



}
