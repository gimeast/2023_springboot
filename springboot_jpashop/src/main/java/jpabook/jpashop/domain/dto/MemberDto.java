package jpabook.jpashop.domain.dto;

import jpabook.jpashop.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberDto {

    private Long id;

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;

    public MemberDto() {}

    public MemberDto(Member o) {
        this.id = o.getId();
        this.name = o.getName();
        this.city = o.getAddress().getCity();
        this.street = o.getAddress().getStreet();
        this.zipcode = o.getAddress().getZipcode();
    }

}
