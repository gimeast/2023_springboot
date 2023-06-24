package com.gimeast.springboot_bimove.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = {"posterList"})
@Table(name = "tbl_movie")
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;


    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "movie", //Poster의 movie가 연관관계의 주인이라는 것을 의미.
            cascade = CascadeType.ALL, //현재 엔티티 객체의 상태를 하위 엔티티 객체들에게 전파하고 처리할수있다.(Movie 저장시 Poster와 같이 저장되게 해주는 설정.)
            orphanRemoval = true //'orphanRemoval = true 는 참조가 없는 하위 엔티티 객체는 삭제할 것인가?'에 대한 설정.
    )
    @Builder.Default
    private List<Poster> posterList = new ArrayList<>();

    public void addPoster(Poster poster) {
        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    public void removePoster(Long ino) {
        Optional<Poster> result = posterList.stream().filter(poster -> poster.getIno() == ino).findFirst();

        result.ifPresent(poster -> {
            poster.setMovie(null);
            posterList.remove(poster);
        });

        changeIdx();
    }

    private void changeIdx() {
        for (int i = 0; i < posterList.size(); i++) {
            posterList.get(i).setIdx(i);
        }
    }


}
