package softfocus.space.conference.module.today.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.dto.FileGrapeDTO;

import javax.persistence.*;

@Entity @Table(name = "FILE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class File extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("파일 소유자")
    @ManyToOne
    @JoinColumn(name = "member_idx", referencedColumnName = "idx", nullable = false)
    private Member member;

    @Comment("원본 파일명")
    private String originalFilename;

    @Comment("확장자")
    private String extension;

    @Comment("파일명")
    private String name;

    @Comment("파일 경로")
    private String url;

    @Comment("파일 높이")
    private Integer height;

    @Comment("파일 넓이")
    private Integer width;

    @Comment("파일 사이즈")
    private Long size;
    public FileGrapeDTO toGrapeDTO() {
        return new FileGrapeDTO(
                this.originalFilename,
                "image",
                this.url,
                this.height,
                this.width
        );
    }

}
