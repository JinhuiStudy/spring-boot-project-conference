package softfocus.space.conference.module.today.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import softfocus.space.conference.module.today.entity.File;

public interface FileRepository extends JpaRepository<File, Long>, QuerydslPredicateExecutor<File> {
}
