package com.gimeast.guestbook.repository;

import com.gimeast.guestbook.data.dto.GuestbookDto;
import com.gimeast.guestbook.data.dto.PageRequestDto;
import com.gimeast.guestbook.data.dto.PageResultDto;
import com.gimeast.guestbook.data.dto.SearchStatus;
import com.gimeast.guestbook.data.entity.Guestbook;
import com.gimeast.guestbook.data.entity.QGuestbook;
import com.gimeast.guestbook.service.GuestbookService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Autowired
    private GuestbookService guestbookService;

    @Test
    @Rollback(value = false)
    void 더미_데이터_생성() throws Exception {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestBook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content" + i)
                    .writer("user" + (i%10))
                    .build();
            guestbookRepository.save(guestBook);
        });

    }

    @Test
    void 수정_테스트() throws Exception {
        //given
        더미_데이터_생성();

        //when
        Guestbook guestbook = guestbookRepository.findById(1L).orElseThrow(() ->
                new IllegalArgumentException("해당 글은 존재하지 않습니다."));

        guestbook.changeGuestbook("change title...", "change content...");

        //then
        Assertions.assertEquals("change title...", guestbookRepository.findById(1L).get().getTitle());
    }

    @Test
    void 조건_리스트_조회1() throws Exception {
        //given
        더미_데이터_생성();

        //when
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        builder.and(exTitle);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        //then
        System.out.println("=================================");
        AtomicInteger count = new AtomicInteger();
        result.stream().forEach(guestbook -> {
            count.getAndIncrement();
            System.out.println(guestbook);
        });
        System.out.println(count);
        System.out.println("=================================");

    }

    @Test
    void 조건_리스트_조회2() throws Exception {
        //given
        더미_데이터_생성();

        //when
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        //then
        System.out.println("=================================");
        AtomicInteger count = new AtomicInteger();
        result.stream().forEach(guestbook -> {
            count.getAndIncrement();
            System.out.println(guestbook);
        });
        System.out.println(count);
        System.out.println("=================================");

    }
    

    @Test
    void 조건_리스트_조회3() throws Exception {
        //given
        더미_데이터_생성();

        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<Guestbook> result = guestbookRepository.findBySearch(new SearchStatus("t","1"), pageable);

        //then
        System.out.println("=================================");
        AtomicInteger count = new AtomicInteger();
        result.stream().forEach(guestbook -> {
            count.getAndIncrement();
            System.out.println(guestbook);
        });
        System.out.println(count);
        System.out.println("=================================");

    }

    @Test
    void 페이징_목록조회() throws Exception {
        //given
        더미_데이터_생성();

        //when
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDto<GuestbookDto, Guestbook> resultDto = guestbookService.getList(pageRequestDto, new SearchStatus("t","1"));

        System.out.println("PREV : " + resultDto.isPrev());
        System.out.println("NEXT : " + resultDto.isNext());
        System.out.println("TOTAL : " + resultDto.getTotalPage());

        //then
        System.out.println("===========================================");
        for(GuestbookDto guestbookDto : resultDto.getDtoList()) {
            System.out.println(guestbookDto);
        }
        System.out.println("===========================================");
        resultDto.getPageList().forEach(i -> System.out.println(i));

    }







}
