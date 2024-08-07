package spoticks.ticket_reservation.domain.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.global.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "member_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private String memberName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String userName, String password, String memberName, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.memberName = memberName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = MemberStatus.ACTIVE;
    }

}
