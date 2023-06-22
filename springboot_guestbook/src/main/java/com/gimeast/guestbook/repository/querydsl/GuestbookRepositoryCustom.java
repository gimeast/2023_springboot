package com.gimeast.guestbook.repository.querydsl;

import com.gimeast.guestbook.data.entity.Guestbook;
import com.gimeast.guestbook.data.dto.SearchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GuestbookRepositoryCustom {

    Page<Guestbook> findBySearch(SearchStatus status, Pageable pageable);

}
