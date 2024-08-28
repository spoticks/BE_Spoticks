package spoticks.ticket_reservation.global.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class MultiResponseDto<T> {

    private final List<T> content;
    private PageInfo pageInfo;

    public MultiResponseDto(List<T> content, Page page) {
        this.content = content;
        this.pageInfo = PageInfo.builder()
                .page(page.getNumber() +1)
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public MultiResponseDto(List<T> content) {
        this.content = content;
    }

}
