package jpabook.jpashop.service;

import jpabook.jpashop.domain.dto.BookDto;
import jpabook.jpashop.domain.dto.ItemDto;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemService {

    public final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<ItemDto> findItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> result = items.stream().map(o -> new ItemDto(o)).collect(Collectors.toList());
        return result;
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }


    @Transactional
    public void updateItem(BookDto bookDto) {
        Item findItem = itemRepository.findOne(bookDto.getId());
        findItem.change(bookDto.getName(), bookDto.getPrice(), bookDto.getStockQuantity());
//        findItem.setName(bookDto.getName());
//        findItem.setPrice(bookDto.getPrice());
//        findItem.setStockQuantity(bookDto.getStockQuantity());
    }
}
