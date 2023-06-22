package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    
    @Test
    void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("실전 JPA", 35000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order savedOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, savedOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, savedOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(35000 * orderCount, savedOrder.getTotalPrice(), "주문한 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문한 수량만큼 재고가 줄어야 한다.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가", "123-123"));
        em.persist(member);
        return member;
    }
    private Member createMember2() {
        Member member = new Member();
        member.setName("회원2");
        member.setAddress(new Address("서울","강가", "123-123"));
        em.persist(member);
        return member;
    }

    @Test()
    void 주문상품_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("실전 JPA", 35000, 10);

        int orderCount = 11;

        //when
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount),
                "재고 수량 부족 예외가 발생해야 한다."); //then
    }

    @Test
    void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("실전 JPA", 35000, 10);

        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

         //then
        Order savedOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCEL, savedOrder.getStatus(), "주문 상태가 CANCEL 이어야 한다");

        Item savedItem = itemRepository.findOne(item.getId());
        assertEquals(10, savedItem.getStockQuantity(), "주문 취소시 원래의 재고 수량으로 돌아가야한다.");
    }

    @Test
    void 주문조회() throws Exception {
        //given
        Member member = createMember();
        Member member2 = createMember2();
        Item item = createBook("실전 JPA", 35000, 10);

        int orderCount = 3;
        //when
        Long orderId1 = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId1);

        //then
        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(orderSearch.getOrderStatus().CANCEL);
        orderSearch.setMemberName("회원1");

        List<Order> orders = orderService.findOrders(orderSearch);
        assertEquals(1, orders.size(), "검색조건에 맞는 주문의 개수가 나와야한다");
    }


}