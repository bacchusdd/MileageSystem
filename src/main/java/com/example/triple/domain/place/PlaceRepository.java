package com.example.triple.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Places, String> {

    boolean existsByPlaceId(String placeId);

    Places findByPlaceId(String placeId);

    long countByPlaceId(String placeId);
}
