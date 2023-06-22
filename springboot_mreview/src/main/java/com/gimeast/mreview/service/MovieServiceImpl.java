package com.gimeast.mreview.service;

import com.gimeast.mreview.dto.MovieDto;
import com.gimeast.mreview.dto.PageRequestDto;
import com.gimeast.mreview.dto.PageResultDto;
import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.MovieImage;
import com.gimeast.mreview.repository.MovieImageRepository;
import com.gimeast.mreview.repository.MovieRepository;
import com.gimeast.mreview.repository.ReviewRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
@Transactional
@Log4j2
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;


    public MovieServiceImpl(MovieRepository movieRepository, MovieImageRepository movieImageRepository, ReviewRepository reviewRepository) {
        this.movieRepository = movieRepository;
        this.movieImageRepository = movieImageRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Long register(MovieDto movieDto) {
        Map<String, Object> entityMap = dtoToEntity(movieDto);

        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> movieImageRepository.save(movieImage));

        return movie.getMno();
    }

    @Override
    public PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto) {
//        Pageable pageable = pageRequestDto.getPageable(Sort.by("mno").descending());
//        Page<Object[]> result = movieRepository.getSearchPage(pageable);
        Page<Object[]> result = movieRepository.getSearchPage(pageRequestDto.getKeyword(), pageRequestDto.getPageable(Sort.by("mno").descending()));
        Function<Object[], MovieDto> fn = (arr -> entitiesToDto((Movie) arr[0], Arrays.asList((MovieImage) arr[1]), (Double) arr[2], (Long) arr[3]));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public MovieDto getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);

        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDto(movie, movieImageList, avg, reviewCnt);
    }

    @Override
    public void remove(Long mno) {
        reviewRepository.deleteByMovieMno(mno);
        movieImageRepository.deleteByMovieMno(mno);
        movieRepository.deleteById(mno);
    }

    @Override
    public void modify(MovieDto movieDto) {
        Optional<Movie> findMovie = movieRepository.findById(movieDto.getMno());

        if (findMovie.isPresent()) {
            findMovie.get().changeTitle(movieDto.getTitle());//영화 제목 변경

            Map<String, Object> resultMap = dtoToEntity(movieDto);
            List<MovieImage> imgList = (List<MovieImage>) resultMap.get("imgList");

            movieImageRepository.deleteByMovieMno(movieDto.getMno());//기존 이미지 삭제
            imgList.forEach(img -> movieImageRepository.save(img));//영화 이미지 저장
        }
    }
}
