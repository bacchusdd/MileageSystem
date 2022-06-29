package com.example.triple.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Reviews, String> {

    Reviews findByReviewId(String reviewId);
    //Reviews deleteByReviewId(String reviewId);

}
