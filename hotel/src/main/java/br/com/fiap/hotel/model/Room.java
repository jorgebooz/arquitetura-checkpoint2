package br.com.fiap.hotel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = "number")
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O número do quarto é obrigatório.")
    @Column(nullable = false, unique = true)
    private Integer number;

    @NotNull(message = "O tipo do quarto é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomType type;

    @NotNull(message = "A capacidade é obrigatória.")
    @Min(value = 1, message = "A capacidade mínima é 1 hóspede.")
    @Column(nullable = false)
    private Integer capacity;

    @NotNull(message = "O valor da diária é obrigatório.")
    @Positive(message = "O valor da diária deve ser positivo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RoomStatus status = RoomStatus.ATIVO;

    public Room() {}

    public Room(Integer number, RoomType type, Integer capacity, BigDecimal dailyRate, RoomStatus status) {
        this.number = number;
        this.type = type;
        this.capacity = capacity;
        this.dailyRate = dailyRate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
