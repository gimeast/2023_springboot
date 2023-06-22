package com.gimeast.mreview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long reviewnum;
    private Long mno;
    private Long mid;
    private String email;
    private String nickname;
    private int grade;
    private String text;
    private LocalDateTime regDate, modDate;

}
