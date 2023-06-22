package com.gimeast.board.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<D,E> {

    private List<D> dtoList;
    
    private int totalPage;//총 페이지 번호
    private int page;//현재 페이지 번호
    private int size;//목록 사이즈
    private int start, end;//시작 페이지 번호, 끝 페이지 번호
    private boolean prev, next;//이전 여부, 다음 여부
    private List<Integer> pageList;//페이지 번호 목록

    /**
     * Page<E>의 내용물 중 Entity객체를 DTO로 변환
     * @param result
     * @param fn
     */
    public PageResultDto(Page<E> result, Function<E, D> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    /**
     * 화면에 보낼 페이징
     * @param pageable
     */
    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;//0부터 시작
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int) (Math.ceil((page/10.0)) * 10);

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
