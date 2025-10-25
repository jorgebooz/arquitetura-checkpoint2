package br.com.fiap.hotel.dto;

import br.com.fiap.hotel.model.Room;
import br.com.fiap.hotel.model.RoomStatus;
import br.com.fiap.hotel.model.RoomType;

import java.math.BigDecimal;

public record RoomResponseDTO(
        Long id,
        Integer number,
        RoomType type,
        Integer capacity,
        BigDecimal dailyRate,
        RoomStatus status
) {
    public RoomResponseDTO(Room r) {
        this(r.getId(), r.getNumber(), r.getType(), r.getCapacity(), r.getDailyRate(), r.getStatus());
    }
}

