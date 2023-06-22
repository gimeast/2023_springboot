package jpabook.jpashop.repository.querydsl;

import jpabook.jpashop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSpringDataJpaRepository extends JpaRepository<Order, Long>, OrderSpringDataJpaRepositoryCustom {

}
