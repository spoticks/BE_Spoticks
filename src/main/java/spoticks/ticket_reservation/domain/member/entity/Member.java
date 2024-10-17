package spoticks.ticket_reservation.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.member.dto.MemberDto;
import spoticks.ticket_reservation.domain.team.entity.Team;
import spoticks.ticket_reservation.global.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "member_team",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;

    @Builder
    public Member(String userName, String password, String memberName, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.memberName = memberName;
        this.phoneNumber = phoneNumber;
        this.status = MemberStatus.ACTIVE;
        this.teams = new HashSet<>();
        this.roles = new ArrayList<>();
    }

    public void updateMemberInfo(MemberDto.ModifyPhoneReq dto) {
        this.phoneNumber = dto.getPhoneNumber();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void withdrawal() {
        this.status = MemberStatus.WITHDRAWAL;
    }

}
