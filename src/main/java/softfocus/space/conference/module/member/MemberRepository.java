package softfocus.space.conference.module.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.util.Streamable;

import java.util.stream.Stream;

public interface MemberRepository extends JpaRepository<Member, Integer>, QuerydslPredicateExecutor<Member> {
    Streamable<Member> findByNameContaining(String name);
    Streamable<Member> findByNameEquals(String name);
    Stream<Member> findByName(String name);
}
