package com.example.triple.domain.pointhistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends JpaRepository<Histories, Long> {

    boolean existsByReviewIdAndTypeContains(String reviewId, String type);

    List<Histories> findByUsers_userId(@Param(value = "userId") String userId);
}
