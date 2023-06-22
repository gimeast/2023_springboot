package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY) // 양방향 관계인 경우: foreign(@JoinColumn) 키가 있는 주인 테이블인 Order entity 의 delivery 필드에 의해 mapped 된거다~
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // 기본은 ORDINAL => 1, 2, 3 ... 형식이다. 중간에 하나가 추가되면 숫자가 밀릴수가 있다.. 되도록 STRING으로 사용하자~!
    private DeliveryStatus status;




}
