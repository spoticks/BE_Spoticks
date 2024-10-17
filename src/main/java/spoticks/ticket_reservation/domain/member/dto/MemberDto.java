package spoticks.ticket_reservation.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spoticks.ticket_reservation.domain.member.entity.Member;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpReq {

        @NotEmpty
        private String userName;

        @Setter
        @NotEmpty
        private String password;

        @NotEmpty
        private String memberName;

        @NotEmpty
        private String phoneNumber;

        @Builder
        public SignUpReq(String userName, String password, String memberName, String phoneNumber) {
            this.userName = userName;
            this.password = password;
            this.memberName = memberName;
            this.phoneNumber = phoneNumber;
        }

        public Member toEntity() {
            return Member.builder()
                    .userName(this.userName)
                    .password(this.password)
                    .memberName(this.memberName)
                    .phoneNumber(this.phoneNumber)
                    .build();
        }
    }

    @Getter
    public static class ModifyPhoneReq {

        private String phoneNumber;

    }

    @Getter
    public static class ModifyPasswordReq {

        private String password;
        private String newPassword;

    }

    @Getter
    public static class SingleReq {

        @NotEmpty
        private String input;

    }

    @Getter
    public static class Res {

        private final String userName;
        private final String memberName;
        private final String phoneNumber;

        public Res(Member member) {
            this.userName = member.getUserName();
            this.memberName = member.getMemberName();
            this.phoneNumber = member.getPhoneNumber();
        }

    }

}
