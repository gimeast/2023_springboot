package com.gimeast.guestbook.service.impl;

import com.gimeast.guestbook.data.dto.GuestbookDto;
import com.gimeast.guestbook.service.GuestbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;
    
    @Test
    @Rollback(value = false)
    void 글등록() throws Exception {
        //given


        //when
        GuestbookDto guestbookDto = GuestbookDto.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        //then
        System.out.println(guestbookService.register(guestbookDto));
        
    }
}