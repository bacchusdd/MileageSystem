package com.example.triple.domain.pointhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<Histories, Long> {

    boolean existsByReviewIdAndTypeContains(String reviewId, String type);
}
