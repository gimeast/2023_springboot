package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 양방향 관계인 경우: foreign 키가 있는 주인 테이블인 Order entity 있는 member 필드에 의해 mapped 된거다~
    private List<Order> orders = new ArrayList<>();



}
