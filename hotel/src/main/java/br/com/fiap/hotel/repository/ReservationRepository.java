package br.com.fiap.hotel.repository;

import br.com.fiap.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
        SELECT r FROM Reservation r 
        WHERE r.room.id = :roomId 
        AND r.status <> br.com.fiap.hotel.model.ReservationStatus.CANCELED
        AND (
            (r.checkInDate < :checkOutDate AND r.checkOutDate > :checkInDate)
        )
    """)
    List<Reservation> findOverlappingReservations(
            @Param("roomId") Long roomId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );
}