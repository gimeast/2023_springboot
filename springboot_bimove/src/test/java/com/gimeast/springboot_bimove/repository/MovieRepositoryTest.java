package com.gimeast.springboot_bimove.repository;

import com.gimeast.springboot_bimove.entity.Movie;
import com.gimeast.springboot_bimove.entity.Poster;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class MovieRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(MovieRepositoryTest.class);

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Rollback(value = false)
    void testInsert() throws Exception {
        //given
        logger.info("testInsert...................");

        Movie movie = Movie.builder()
                .title("극한직업")
                .build();

        movie.addPoster(Poster.builder().fname("극한직업포스터1.jpg").build());
        movie.addPoster(Poster.builder().fname("극한직업포스터2.jpg").build());

        //when
        movieRepository.save(movie);

        //then
        logger.info("mno: {}",movie.getMno());

    }

    @Test
    @Rollback(value = false)
    void testAddPoster() throws Exception {
        //given
        Optional<Movie> result = movieRepository.findById(3L);

        //when
        if (result.isPresent()) {
            Movie movie = result.get();
            movie.addPoster(Poster.builder().fname("극한직업포스터3.jpg").build());
        }
    }

    @Test
    @Rollback(value = false)
    void testRemovePoster() throws Exception {
        //given
        Optional<Movie> result = movieRepository.findById(3L);

        //when
        result.ifPresent(movie -> {
            movie.removePoster(4L);
            movieRepository.save(movie);
        });
    }

    @Test
    @Rollback(value = false)
    void insertMovies() throws Exception {
        //given
        IntStream.rangeClosed(10, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("세계명장" + i)
                    .build();

            movie.addPoster(Poster.builder().fname("세계명작"+i+"포스터1.jpg").build());
            movie.addPoster(Poster.builder().fname("세계명작"+i+"포스터2.jpg").build());

            movieRepository.save(movie);
        });
    }

    @Test
    void testPaging1() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        //when
        Page<Movie> result = movieRepository.findAll(pageable);

        //then
        result.getContent().forEach(movie -> {
            logger.info("mno: {}",movie.getMno());
            logger.info("title: {}",movie.getTitle());
            logger.info("size: {}",movie.getPosterList().size());
            logger.info("------------------------");
        });
    }

    @Test
    void testPaging2() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        //when
        Page<Movie> result = movieRepository.findAll2(pageable);

        //then
        result.getContent().forEach(movie -> {
            logger.info("mno: {}",movie.getMno());
            logger.info("title: {}",movie.getTitle());
            logger.info("size: {}",movie.getPosterList().size());
            logger.info("------------------------");
        });
    }

    @Test
    void testPaging3() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        //when
        Page<Object[]> result = movieRepository.findAll3(pageable);

        //then
        result.getContent().forEach(obj -> {
            logger.info(Arrays.toString(obj));
            logger.info("------------------------");
        });
    }
}