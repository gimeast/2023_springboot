package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private  MovieImageRepository movieImageRepository;

    @Test
    @Rollback(value = false)
    void 영화등록() throws Exception {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie...." + i)
                    .build();
            System.out.println("----------------------------------");

            movieRepository.save(movie);
            System.out.println("mno : " + movie.getMno());
            int count = (int) ((Math.random() * 5) + 1);
            System.out.println("count : " + count);

            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }
            System.out.println("======================================");
        });

    }

    @Test
    void 영화목록() throws Exception {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        //when
        Page<Object[]> listPage = movieRepository.getListPage(pageRequest);

        //then
        listPage.forEach(p -> {
            System.out.println(Arrays.toString(p));
        });

    }

    @Test
    void 상세화면() throws Exception {
        //given
        Long mno = 94L;
        //when
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        System.out.println(result);

        //then
        result.forEach(obj -> System.out.println(Arrays.toString(obj)));

    }



}