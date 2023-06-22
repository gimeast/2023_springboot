package com.gimeast.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {

    private Long bno;
    private String title;
    private String content;
    private String memberEmail;
    private String memberName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private Long replyCount;



}
