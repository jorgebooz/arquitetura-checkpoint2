package br.com.fiap.hotel.controller;

import br.com.fiap.hotel.dto.ReservationRequestDTO;
import br.com.fiap.hotel.dto.ReservationResponseDTO;
import br.com.fiap.hotel.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationRequestDTO dto) {
        return ResponseEntity.ok(service.createReservation(dto));
    }

    @PostMapping("/{id}/checkin")
    public ResponseEntity<ReservationResponseDTO> checkIn(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkIn(id));
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<ReservationResponseDTO> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(service.checkOut(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponseDTO> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(service.cancel(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }
}

