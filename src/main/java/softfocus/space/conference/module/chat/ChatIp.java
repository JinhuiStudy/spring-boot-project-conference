package softfocus.space.conference.module.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_IP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatIp extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("IP")
    @Column(nullable = false)
    private String ip;

    @Comment("사용자 이름")
    @Column(nullable = false)
    private String name;
}
