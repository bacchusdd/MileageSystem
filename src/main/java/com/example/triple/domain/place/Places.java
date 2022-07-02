package com.example.triple.domain.place;

import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "place", indexes = @Index(name = "idx_placeId", columnList = "placeId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Places {

    @Id
    @Column(name = "placeId")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String placeId;


    @Column(name = "address", columnDefinition="VARCHAR(36)")
    private String address;

    @OneToMany(mappedBy = "places", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @Builder
    public Places(String placeId, String address){
        this.placeId = placeId;
        this.address = address;
    }

}
