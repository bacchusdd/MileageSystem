package com.example.triple.domain.reviewphoto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photos, String> {

    List<Photos> findAllByReviews_reviewId(@Param(value = "reviewId") String reviewId);

    Photos findByAttachedPhotoId(String photoId);

    boolean existsByReviews_reviewId(@Param(value = "reviewId") String reviewId);
}
