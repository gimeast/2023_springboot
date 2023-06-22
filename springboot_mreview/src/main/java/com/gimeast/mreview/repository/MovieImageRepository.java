package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

    void deleteByMovieMno(Long mno);
}
