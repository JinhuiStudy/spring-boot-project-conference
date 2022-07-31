package softfocus.space.conference.module.today.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import softfocus.space.conference.module.today.entity.Today;

import java.time.LocalDate;
import java.util.Optional;

public interface TodayRepository extends JpaRepository<Today, Long>, QuerydslPredicateExecutor<Today> {
    Optional<Today> findByDayAndMember_Idx(LocalDate day, Integer member_idx);
}
