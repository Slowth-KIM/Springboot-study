package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {

    private final MemberRepository memberRepository;

  //  @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } // 같은 객체로 만드는 과정, 직접 new X , dependency injection

    /**
     * 회원 가입
     * @param member
     * @return id
     */
    public Long join(Member member){
        // 같은 이름을 가진 회원 X
        long start = System.currentTimeMillis();

        try {
            validataDuplicateMember(member); // 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join =  " + timeMs + "ms");
        }
    }

    private void validataDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                        throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    /**
     * 전체 회원 조회
     */
    public List<Member> findMember(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
