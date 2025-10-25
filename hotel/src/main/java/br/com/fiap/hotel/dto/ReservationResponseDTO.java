package br.com.fiap.hotel.dto;

import br.com.fiap.hotel.model.Reservation;
import br.com.fiap.hotel.model.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponseDTO(
        Long id,
        Long roomId,
        String guestName,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        ReservationStatus status,
        BigDecimal totalValue
) {
    public ReservationResponseDTO(Reservation r) {
        this(
                r.getId(),
                r.getRoom().getId(),
                r.getGuestName(),
                r.getCheckInDate(),
                r.getCheckOutDate(),
                r.getStatus(),
                r.getTotalValue()
        );
    }
}

