package jpabook.jpashop.repository.querydsl.impl;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.querydsl.OrderSpringDataJpaRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;


public class OrderSpringDataJpaRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderSpringDataJpaRepositoryCustom {

    public OrderSpringDataJpaRepositoryCustomImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> findBySearchStatus(OrderSearch orderSearch) {
        QOrder qOrder = QOrder.order;
        QMember qMember = QMember.member;

        List<Order> orderList = from(qOrder)
                .join(qOrder.member, qMember).fetchJoin()
                .where(orderSearch.getOrderStatus() == null ? null : qOrder.status.eq(orderSearch.getOrderStatus()),
                        !StringUtils.hasText(orderSearch.getMemberName()) ? null : qMember.name.likeIgnoreCase("%"+orderSearch.getMemberName()+"%"))
                .limit(1000)
                .fetch();

        return orderList;
    }


}
