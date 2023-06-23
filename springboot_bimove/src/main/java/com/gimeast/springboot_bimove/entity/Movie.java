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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie", cascade = CascadeType.ALL)
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
