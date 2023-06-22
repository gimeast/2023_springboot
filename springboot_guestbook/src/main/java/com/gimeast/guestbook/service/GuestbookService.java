package com.gimeast.guestbook.service;

import com.gimeast.guestbook.data.dto.GuestbookDto;
import com.gimeast.guestbook.data.dto.PageRequestDto;
import com.gimeast.guestbook.data.dto.PageResultDto;
import com.gimeast.guestbook.data.dto.SearchStatus;
import com.gimeast.guestbook.data.entity.Guestbook;

public interface GuestbookService {

    /**
     * DTO를 Entity로 변환해줌
     * @param dto
     * @return
     */
    default Guestbook dtoToEntity(GuestbookDto dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    /**
     * Entity를 Dto로 변환해줌
     * @param entity
     * @return
     */
    default GuestbookDto entityToDto(Guestbook entity) {
        GuestbookDto dto = GuestbookDto.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }

    /**
     * 글등록
     * @param guestbookDto
     * @return
     */
    Long register(GuestbookDto guestbookDto);

    /**
     * 1페이지 조회
     * @param requestDto
     * @return
     */
    PageResultDto<GuestbookDto, Guestbook> getList(PageRequestDto requestDto, SearchStatus status);

    /**
     * 상세페이지 조회
     * @param gno
     * @return
     */
    GuestbookDto read(Long gno);

    /**
     * 글 삭제
     * @param gno
     */
    void remove(Long gno);

    /**
     * 글 수정
     * @param guestbookDto
     */
    void modify(GuestbookDto guestbookDto);
    
}
