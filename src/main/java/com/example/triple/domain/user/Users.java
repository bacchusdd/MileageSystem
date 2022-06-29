package com.example.triple.domain.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name="user", indexes = @Index(name = "idxuserId", columnList = "userId"))
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Users {

    @Id
    @GeneratedValue
    private String userId;

    @NotNull
    @Column(name="points")
    private int points;

    @Builder
    public Users(String userId, int points){
        this.userId = userId;
        this.points = points;
    }

}
