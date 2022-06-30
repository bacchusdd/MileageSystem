package com.example.triple.domain.reviewphoto;

import com.example.triple.domain.review.Reviews;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="reviewphoto")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Photos {

    @Id
    //@GeneratedValue(generator="system-uuid")
    //@GenericGenerator(name="system-uuid", strategy = "uuid")
    private String attachedPhotoId;

    //@Column(name = "reviewId")
    //private String reviewId;

    //review 1 : photo many
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewId", referencedColumnName = "reviewId", columnDefinition="VARCHAR(36)")
    private Reviews reviews;

    @Builder
    public Photos(String attachedPhotoId, Reviews reviews){
        this.attachedPhotoId = attachedPhotoId;
        this.reviews = reviews;
    }
}
