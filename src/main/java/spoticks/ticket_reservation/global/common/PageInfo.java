package spoticks.ticket_reservation.global.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageInfo {

    private int page;
    private int size;
    private long totalElements;
    private long totalPages;

}
