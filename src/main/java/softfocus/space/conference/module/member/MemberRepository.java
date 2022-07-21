package softfocus.space.conference.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.util.Streamable;

import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, Integer>, QuerydslPredicateExecutor<Member> {
    Streamable<Member> findByNicknameContaining(String nickname);
    Streamable<Member> findByNicknameEquals(String nickname);
    Stream<Member> findByNickname(String nickname);

    Optional<Member> findByEmail(String email);
}
