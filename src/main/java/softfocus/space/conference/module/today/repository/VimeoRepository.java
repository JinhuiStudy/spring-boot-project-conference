package softfocus.space.conference.module.today.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import softfocus.space.conference.module.today.entity.Vimeo;

public interface VimeoRepository extends JpaRepository<Vimeo, Long>, QuerydslPredicateExecutor<Vimeo> {
    void deleteByEndPoint(String videoEndPoint);
}
