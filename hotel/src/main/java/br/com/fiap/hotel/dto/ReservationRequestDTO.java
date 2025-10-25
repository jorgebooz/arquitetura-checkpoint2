package br.com.fiap.hotel.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull Long roomId,
        @NotBlank String guestName,
        @NotNull @Future LocalDate checkInDate,
        @NotNull @Future LocalDate checkOutDate
) {}

