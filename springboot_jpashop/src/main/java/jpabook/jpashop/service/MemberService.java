package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.dto.MemberDto;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        // 중복회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 실무에서는 중복체크를 하여도 동시에 같은 이름으로 가입을 하게되면 중복회원가입이 가능할수 있으므로 db의 member table의 name컬럼에 unique 제약조건을 걸어줘야한다.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존해하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<MemberDto> findMembers() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDto> result = memberList.stream().map(o -> new MemberDto(o))
                .collect(Collectors.toList());
        return result;
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }





}
