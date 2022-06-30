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

@Getter
@NoArgsConstructor
@Entity
@Table(name = "place", indexes = @Index(name = "idx_placeId", columnList = "placeId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Places {

    @Id
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String placeId;

    @NotNull
    @Column(name = "placeCount")
    private int placeCount;

    @Builder
    public Places(String placeId, int placeCount){
        this.placeId = placeId;
        this.placeCount = placeCount;
    }

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
}
