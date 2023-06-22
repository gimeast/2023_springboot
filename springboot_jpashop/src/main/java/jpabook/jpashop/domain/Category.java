package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item", // ManyToMany 테이블의 경우 @JoinTable 로 중간 테이블을 생성해서 맵핑한다.(실무에서는 사용하지말자!)
            joinColumns = @JoinColumn(name = "category_id"), // "category_item"의 foreign key 생성
            inverseJoinColumns = @JoinColumn(name = "item_id") // "category_item"의 foreign key 생성
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 편의 메서드==//
    public void addChildCategory(Category category) {
        this.child.add(category);
        category.setParent(this);
    }



}
