package com.example.triple.domain.reviewphoto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photos, String> {

    List<Photos> findAllByReviewId(String reviewId);

    Photos findByAttachedPhotoId(String photoId);
}
