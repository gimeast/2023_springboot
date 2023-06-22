package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable // 내장타입!
@Getter // 값타입은 변경 불가능하게 설계해야 하며 setter를 제거하고 생성자에서 값을 모두 초기화해서 변 경불가능한 클래스를 만들어야한다.
@ToString
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /**
     * JPA 스펙상 리플렉션이나 프록시같은 기술을 써야하는데
     * 그 때문에 기본 생성자를 참조하기 때문에 기본 생성자를 만들어줘야 하고
     * 엔티티나 내장타입 같은경우는 public으로 기본생성자를 정의하면 오사용을 할 수 있기때문에 protected로 바꿔놓으면 그나마 더 안전하다.
     */
    protected Address() {}

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
