package br.com.fiap.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @NotBlank(message = "O nome do hóspede é obrigatório.")
    @Column(nullable = false, length = 100)
    private String guestName;

    @NotNull(message = "A data de check-in prevista é obrigatória.")
    @Future(message = "A data de check-in deve ser futura.")
    @Column(nullable = false)
    private LocalDate checkInDate;

    @NotNull(message = "A data de check-out prevista é obrigatória.")
    @Future(message = "A data de check-out deve ser futura.")
    @Column(nullable = false)
    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.CREATED;

    @Column(name = "total_value", precision = 10, scale = 2)
    private BigDecimal totalValue;

    public Reservation() {}

    public Reservation(Room room, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.CREATED;
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    // 💡 Método auxiliar — usado no check-out
    public void calculateTotalValue() {
        if (room != null && checkInDate != null && checkOutDate != null) {
            long days = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            if (days > 0) {
                this.totalValue = room.getDailyRate().multiply(BigDecimal.valueOf(days));
            }
        }
    }
}
