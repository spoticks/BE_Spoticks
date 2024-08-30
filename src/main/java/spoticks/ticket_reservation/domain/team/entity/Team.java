package spoticks.ticket_reservation.domain.team.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spoticks.ticket_reservation.domain.member.entity.Member;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Team {

    @Id
    @Column(name = "team_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String teamName;

    @JsonIgnore
    @ManyToMany(mappedBy = "teams")
    private Set<Member> users = new HashSet<>();

}