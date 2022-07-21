package softfocus.space.conference.module.today.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import softfocus.space.conference.module.today.entity.TodayElement;

import java.util.List;

public interface TodayElementRepository extends JpaRepository<TodayElement, Long>, QuerydslPredicateExecutor<TodayElement> {
    List<TodayElement> findByToday_Idx(Long today_idx, Sort sort);
}
