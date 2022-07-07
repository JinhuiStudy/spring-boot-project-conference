package softfocus.space.conference.module.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass /* JPA Entity 클래스들이 BaseTime 상속할 경우 필드들(createdDate, modifiedDate)도 칼럼으로 인식 */
@EntityListeners(AuditingEntityListener.class) /* BaseTime 클래스에 Auditing 기능을 포함 */
public abstract class BaseTime {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
