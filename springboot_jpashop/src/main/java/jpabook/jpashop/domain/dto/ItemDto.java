package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    public ItemDto() {}

    public ItemDto(Item o) {
        this.id = o.getId();
        this.name = o.getName();
        this.price = o.getPrice();
        this.stockQuantity = o.getStockQuantity();
    }
}
