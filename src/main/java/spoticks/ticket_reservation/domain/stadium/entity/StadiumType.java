package spoticks.ticket_reservation.domain.stadium.entity;

import lombok.Getter;

@Getter
public enum StadiumType {

    SOCCER("축구",
            new Section("일반지정석", 14000),
            new Section("응원석", 16000),
            new Section("테이블석", 35000),
            new Section("프리미엄석", 42000)
            ),
    BASEBALL("야구",
            new Section("내야석", 12000),
            new Section("중앙지정석", 48000),
            new Section("테이블석", 35000),
            new Section("외야석", 7000)
    ),
    VOLLEYBALL("배구",
            new Section("R석", 20000),
            new Section("A석", 15000),
            new Section("일반석", 12000),
            new Section("테이블석", 25000)
    ),
    BASKETBALL("농구",
            new Section("R석", 25000),
            new Section("일반석", 15000),
            new Section("테이블석", 22000),
            new Section("프리미엄석", 48000)
    );

    private final String name;
    private final Section[] sections;

    StadiumType(String name, Section... sections) {
        this.name = name;
        this.sections = sections;
    }

    @Getter
    public static class Section {
        private final String seatPosition;
        private final int price;

        public Section(String seatPosition, int price) {
            this.seatPosition = seatPosition;
            this.price = price;
        }
    }
}
