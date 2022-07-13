package softfocus.space.conference.module.member;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import static softfocus.space.conference.module.member.QMember.member;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Streamable<Member> streamableExample(String name){
        return memberRepository.findByNameContaining(name).and(memberRepository.findByNameEquals(name));
    }

    public void tryExample(String name){
        try (var members = memberRepository.findByName(name)){
            members.forEach(
                    member -> System.out.println(member.toString())
            );
        }
    }

    public Iterable<Member> queryDslExample(String name) {
        Predicate predicate = member.nickname.eq(name);
        return memberRepository.findAll(predicate);
    }


}
