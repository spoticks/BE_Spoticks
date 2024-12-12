package spoticks.ticket_reservation.domain.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spoticks.ticket_reservation.domain.member.entity.Member;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    public static class SignUpReq {

        @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{5,25}$", message = "아이디는 소문자 영어와 숫자를 포함하여 5~25글자여야 합니다.")
        private String userName;

        @Setter
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*])[a-zA-Z\\d!@#$%&*]{8,20}$",
                message = "비밀번호는 영문, 숫자, 특수문자 포함 8~20글자여야 합니다.")
        private String password;

        @Pattern(regexp = "^[가-힣a-zA-Z]{2,12}$", message = "이름은 2~12 글자의 한글, 영문만 입력 가능합니다.")
        private String memberName;

        @Pattern(regexp = "^010\\d{8}$", message = "연락처는 010 으로 시작하는 11자리 숫자만 입력 가능합니다.")
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
