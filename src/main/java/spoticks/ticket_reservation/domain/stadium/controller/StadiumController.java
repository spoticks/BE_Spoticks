package spoticks.ticket_reservation.domain.stadium.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spoticks.ticket_reservation.domain.stadium.entity.Stadium;
import spoticks.ticket_reservation.domain.stadium.service.StadiumService;

@RestController
@RequiredArgsConstructor
public class StadiumController {

    private final StadiumService stadiumService;

    @GetMapping("/teams/{teamId}/stadium")
    public ResponseEntity getTeamHomeInfo(@PathVariable long teamId) {
        Stadium response = stadiumService.findById(teamId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
