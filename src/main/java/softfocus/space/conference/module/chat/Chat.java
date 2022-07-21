package softfocus.space.conference.module.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.member.Member;

import javax.persistence.*;

@Entity
@Table(name = "CHATS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("채팅방 주인")
    @OneToOne
    @JoinColumn(name = "member_idx", referencedColumnName = "idx", nullable = false)
    private Member member;

    @Comment("메세지 보낸사람")
    @OneToOne
    @JoinColumn(name = "sender_idx", referencedColumnName = "idx")
    private ChatIp chatIp;

    @Comment("메세지")
    @Column(nullable = false)
    private String message;

    @Comment("채팅방 주인이 채팅했을시")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Host isHost;

}
