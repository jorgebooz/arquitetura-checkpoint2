package br.com.fiap.hotel.dto;

import br.com.fiap.hotel.model.RoomStatus;
import br.com.fiap.hotel.model.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotNull Integer number,
        @NotNull RoomType type,
        @Min(1) Integer capacity,
        @Positive BigDecimal dailyRate,
        @NotNull RoomStatus status
) {}

