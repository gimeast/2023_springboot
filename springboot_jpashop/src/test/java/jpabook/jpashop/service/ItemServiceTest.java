package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    @Rollback(value = false)
    void 아이템_저장() throws Exception {
        //given
        Book item = new Book();
        item.setName("JPA 완전 정복");
        item.setPrice(45000);
        item.setStockQuantity(10000);
        item.setAuthor("김영한");
        item.setIsbn("1a123f");

        //when
        itemRepository.save(item);

        //then
        Assertions.assertEquals(item, itemRepository.findOne(item.getId()));
    }

}