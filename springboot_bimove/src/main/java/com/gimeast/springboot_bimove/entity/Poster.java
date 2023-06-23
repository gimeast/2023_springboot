package com.gimeast.springboot_bimove.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = {"movie"})
@Table(name = "tbl_poster")
public class Poster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String fname;

    private int idx;//포스터 순번

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    //set 메서드는 가능하면 줄이는것이 좋지만 하위 엔티티는 상위 엔티티에서 관리하기 편하도록 지정하는 경우가 있다.
    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
