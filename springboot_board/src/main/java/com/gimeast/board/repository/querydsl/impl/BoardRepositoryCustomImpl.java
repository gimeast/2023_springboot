package com.gimeast.board.repository.querydsl.impl;

import com.gimeast.board.entity.Board;
import com.gimeast.board.entity.QBoard;
import com.gimeast.board.entity.QMember;
import com.gimeast.board.entity.QReply;
import com.gimeast.board.repository.querydsl.BoardRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BoardRepositoryCustomImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {


    public BoardRepositoryCustomImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;
        QMember qMember = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(qBoard);
        jpqlQuery.leftJoin(qMember).on(qMember.eq(qBoard.member));
        jpqlQuery.leftJoin(qReply).on(qReply.board.eq(qBoard));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoard, qMember.email, qReply.count());
        tuple.groupBy(qBoard);

        log.info("----------------------------------");
        log.info("tuple: " + tuple);
        log.info("----------------------------------");

        List<Tuple> result = tuple.fetch();
        log.info("----------------------------------");
        log.info("result: " + result);
        log.info("----------------------------------");


        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        QBoard qBoard = QBoard.board;
        QReply qReply = QReply.reply;
        QMember qMember = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(qBoard);
        jpqlQuery.leftJoin(qMember).on(qBoard.member.eq(qMember));
        jpqlQuery.leftJoin(qReply).on(qBoard.eq(qReply.board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoard, qMember, qReply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qBoard.bno.gt(0L);

        booleanBuilder.and(expression);

        if(StringUtils.hasText(type)) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(qBoard.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(qMember.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(qBoard.content.contains(keyword));
                        break;
                }
            }

            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(qBoard);

        this.getQuerydsl().applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();
        log.info("result: " + result);

        long count = tuple.fetchCount();
        log.info("count: " + count);

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count);
    }


}
