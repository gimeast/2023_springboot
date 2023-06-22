package com.gimeast.guestbook.repository;

import com.gimeast.guestbook.data.entity.Guestbook;
import com.gimeast.guestbook.repository.querydsl.GuestbookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, GuestbookRepositoryCustom, QuerydslPredicateExecutor<Guestbook> {
}
