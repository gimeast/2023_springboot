package com.gimeast.guestbook.repository.querydsl.impl;

import com.gimeast.guestbook.data.entity.Guestbook;
import com.gimeast.guestbook.data.dto.SearchStatus;
import com.gimeast.guestbook.data.entity.QGuestbook;
import com.gimeast.guestbook.repository.querydsl.GuestbookRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class GuestbookRepositoryCustomImpl extends QuerydslRepositorySupport implements GuestbookRepositoryCustom {

    public GuestbookRepositoryCustomImpl() {
        super(Guestbook.class);
    }


    @Override
    public Page<Guestbook> findBySearch(SearchStatus status, Pageable pageable) {
        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder condition = new BooleanBuilder();

        if (StringUtils.hasText(status.getKeyword()) && StringUtils.hasText(status.getType())) {
            if (status.getType().contains("t")) {
                condition.or(qGuestbook.title.likeIgnoreCase("%" + status.getKeyword() + "%"));
            }

            if (status.getType().contains("c")) {
                condition.or(qGuestbook.content.likeIgnoreCase("%" + status.getKeyword() + "%"));
            }

            if (status.getType().contains("w")) {
                condition.or(qGuestbook.writer.likeIgnoreCase("%" + status.getKeyword() + "%"));
            }
        }

        List<Guestbook> guestbookList = from(qGuestbook)
                .where(condition)
                .offset(pageable.getOffset())// page 번호
                .limit(pageable.getPageSize())// page size
                .orderBy(qGuestbook.gno.desc())
                .fetch();

        Long count = from(qGuestbook)
                .select(qGuestbook.count())
                .where(condition)
                .orderBy(qGuestbook.gno.desc())
                .fetchOne();

        return new PageImpl<>(guestbookList, pageable, count);
    }
}
