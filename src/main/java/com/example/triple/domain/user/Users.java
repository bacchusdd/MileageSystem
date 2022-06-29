package com.example.triple.domain.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name="user", indexes = @Index(name = "idxuserId", columnList = "userId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Users {

    @Id
    @GeneratedValue
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "uuid2")
    //@Column(columnDefinition = "BINARY(16)")
    private String userId;
    private int points;

    @Builder
    public Users(String userId, int points){
        this.userId = userId;
        this.points = points;
    }

}
