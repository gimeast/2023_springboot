package jpabook.jpashop.repository.querydsl;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderSearch;

import java.util.List;

public interface OrderSpringDataJpaRepositoryCustom {
    List<Order> findBySearchStatus(OrderSearch orderSearch);
}
