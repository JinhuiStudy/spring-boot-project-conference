package softfocus.space.conference.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.util.Streamable;

import java.util.Optional;
import java.util.stream.Stream;

public interface MemberOauthRepository extends JpaRepository<MemberOauth, Integer>, QuerydslPredicateExecutor<Member> {
    Optional<MemberOauth> findByOauthId(String id);
}
