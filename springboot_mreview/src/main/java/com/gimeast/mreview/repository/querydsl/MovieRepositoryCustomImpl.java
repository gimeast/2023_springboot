package com.gimeast.mreview.repository.querydsl;

import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.QMovie;
import com.gimeast.mreview.entity.QMovieImage;
import com.gimeast.mreview.entity.QReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class MovieRepositoryCustomImpl extends QuerydslRepositorySupport implements MovieRepositoryCustom {

    public MovieRepositoryCustomImpl() {
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getSearchPage(String keyword, Pageable pageable) {
        QMovie movie = QMovie.movie;
        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;

        JPQLQuery<Movie> jpqlQuery = from(movie);
        jpqlQuery.leftJoin(movieImage).on(movie.eq(movieImage.movie));
        jpqlQuery.leftJoin(review).on(movie.eq(review.movie));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(movie, movieImage, review.grade.coalesce(0).avg(), review.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(StringUtils.hasText(keyword)) {
            booleanBuilder.and(movie.title.contains(keyword));
        }
        tuple.where(booleanBuilder);
        tuple.groupBy(movie);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        log.info("result: " + result);

        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count);
    }
}
