package com.example.triple.domain.place;

import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    //@NotNull
    //@Column(name = "placeCount")
    //private int placeCount;

    //@NotNull
    //@Column(name = "placeCount")
    //private int placeCount;

    @Column(name = "address", columnDefinition="VARCHAR(36)")
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String address;

    //@OneToMany(mappedBy = "places", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Reviews> reviews = new ArrayList<>();


    @Builder
    public Places(String placeId, String address){
        this.placeId = placeId;
        this.address = address;
        //this.placeCount = placeCount;
    }

    /*
    public void increaseCount(){
        this.placeCount += 1;
    }
    public void decreaseCount(){
        if (placeCount > 0) {
            this.placeCount -= 1;
        }
    }

    public void initCount(int count){
        this.placeCount = count;
    }
     */

}
