package com.gimeast.mreview.service;

import com.gimeast.mreview.dto.MovieDto;
import com.gimeast.mreview.dto.MovieImageDto;
import com.gimeast.mreview.dto.PageRequestDto;
import com.gimeast.mreview.dto.PageResultDto;
import com.gimeast.mreview.entity.Movie;
import com.gimeast.mreview.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    default Map<String, Object> dtoToEntity(MovieDto movieDto) {
        Map<String, Object> entityMap = new HashMap<>();

        Movie movie = Movie.builder()
                .mno(movieDto.getMno())
                .title(movieDto.getTitle())
                .build();
        entityMap.put("movie", movie);

        List<MovieImageDto> movieImageDtoList = movieDto.getImageDtoList();

        if (movieImageDtoList != null && movieImageDtoList.size() > 0) {
            List<MovieImage> movieImageList = movieImageDtoList.stream().map(movieImageDto -> {
                MovieImage movieImage = MovieImage.builder()
                        .path(movieImageDto.getPath())
                        .imgName(movieImageDto.getImgName())
                        .uuid(movieImageDto.getUuid())
                        .movie(movie)
                        .build();
                return movieImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", movieImageList);
        }

        return entityMap;
    }

    default MovieDto entitiesToDto(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDto movieDto = MovieDto.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .modDate(movie.getModDate())
                .build();

        List<MovieImageDto> movieImageDtoList = movieImages.stream().map(movieImage ->
                MovieImageDto.builder()
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .uuid(movieImage.getUuid())
                    .build()
        ).collect(Collectors.toList());

        movieDto.setImageDtoList(movieImageDtoList);
        movieDto.setAvg(avg);
        movieDto.setReviewCnt(reviewCnt.intValue());

        return movieDto;
    }

    Long register(MovieDto movieDto);

    PageResultDto<MovieDto, Object[]> getList(PageRequestDto pageRequestDto);

    MovieDto getMovie(Long mno);

    void remove(Long mno);

    void modify(MovieDto movie);


}
