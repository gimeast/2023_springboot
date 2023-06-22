package com.gimeast.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDto {

    private int page;
    private int size;

    private String type;
    private String keyword;

    /**
     * 기본값 셋팅
     */
    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
    }

    /**
     * 셋팅된 Pageable객체 생성
     * @param sort
     * @return
     */
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page -1, size, sort);
    }

}
