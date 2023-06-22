package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.repository.querydsl.MovieRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) " +
            "from Movie m " +
            "left join Review r on m = r.movie " +
            "left join MovieImage mi on m = mi.movie "+
            "group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r) " +
            "from Movie m " +
            "left join MovieImage mi " +
            "on mi.movie = m " +
            "left join Review r " +
            "on r.movie = m " +
            "where m.mno = :mno " +
            "group by mi")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);
}
