package softfocus.space.conference.module.member;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softfocus.space.conference.module.member.dto.GpsDTO;
import softfocus.space.conference.module.member.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

import static softfocus.space.conference.module.member.QMember.member;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Streamable<Member> streamableExample(String name){
        return memberRepository.findByNicknameContaining(name).and(memberRepository.findByNicknameEquals(name));
    }

    public void tryExample(String name){
        try (var members = memberRepository.findByNickname(name)){
            members.forEach(
                    member -> System.out.println(member.toString())
            );
        }
    }

    public Iterable<Member> queryDslExample(String name) {
        Predicate predicate = member.nickname.eq(name);
        return memberRepository.findAll(predicate);
    }




    public MemberDTO getMember(String oauth_id){
        Optional<Member> optional = memberRepository.findByMemberOauth_OauthId(oauth_id);
        return optional.map(Member::toDTO).orElse(null);
    }

    public MemberDTO getMemberByIdx(Long idx){
        Optional<Member> optional = memberRepository.findByIdx(idx);
        return optional.map(Member::toDTO).orElse(null);
    }


    public List<MemberDTO> findAll(){
        return memberRepository.findAll().stream().map(Member::toDTO).toList();
    }

    @Transactional
    public void saveGps(GpsDTO gpsDTO,String oauthId){
        Optional<Member> optional= memberRepository.findByMemberOauth_OauthId(oauthId);
        if(optional.isPresent()){
            Member member = optional.get();

            member.setLat(Double.parseDouble(gpsDTO.getLat()));
            member.setLon(Double.parseDouble(gpsDTO.getLon()));
        }
    }
}
