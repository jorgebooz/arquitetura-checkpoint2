package br.com.fiap.hotel.service;

import br.com.fiap.hotel.exceptions.ConflictException;
import br.com.fiap.hotel.model.*;
import br.com.fiap.hotel.repository.ReservationRepository;
import br.com.fiap.hotel.repository.RoomRepository;
import br.com.fiap.hotel.dto.ReservationRequestDTO;
import br.com.fiap.hotel.dto.ReservationResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO dto) {
        // Validação das datas
        if (!dto.checkInDate().isBefore(dto.checkOutDate())) {
            throw new IllegalArgumentException("A data de check-in deve ser anterior à data de check-out.");
        }

        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado."));

        if (room.getStatus() == RoomStatus.INATIVO) {
            throw new IllegalStateException("O quarto está inativo.");
        }

        if (!isRoomAvailable(room.getId(), dto.checkInDate(), dto.checkOutDate())) {
            throw new ConflictException(
                    String.format(
                            "O quarto %d já possui reserva no período de %s a %s.",
                            room.getNumber(),
                            dto.checkInDate(),
                            dto.checkOutDate()
                    )
            );
        }

        Reservation reservation = new Reservation(
                room,
                dto.guestName(),
                dto.checkInDate(),
                dto.checkOutDate()
        );

        reservationRepository.save(reservation);
        return new ReservationResponseDTO(reservation);
    }

    @Transactional
    public ReservationResponseDTO checkIn(Long id) {
        Reservation reservation = findByIdOrThrow(id);

        ReservationStatusTransition.validateTransition(reservation.getStatus(), ReservationStatus.CHECKED_IN);

        if (LocalDate.now().isBefore(reservation.getCheckInDate())) {
            throw new ConflictException(
                    "Check-in só pode ser realizado a partir da data de check-in prevista: " + reservation.getCheckInDate()
            );
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        return new ReservationResponseDTO(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationResponseDTO checkOut(Long id) {
        Reservation reservation = findByIdOrThrow(id);

        ReservationStatusTransition.validateTransition(reservation.getStatus(), ReservationStatus.CHECKED_OUT);

        if (LocalDate.now().isBefore(reservation.getCheckOutDate())) {
            throw new ConflictException(
                    "Check-out antecipado. Data de check-out prevista: " + reservation.getCheckOutDate()
            );
        }

        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        reservation.calculateTotalValue();
        return new ReservationResponseDTO(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationResponseDTO cancel(Long id) {
        Reservation reservation = findByIdOrThrow(id);

        ReservationStatusTransition.validateTransition(reservation.getStatus(), ReservationStatus.CANCELED);

        reservation.setStatus(ReservationStatus.CANCELED);
        return new ReservationResponseDTO(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDTO> listAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationResponseDTO::new)
                .toList();
    }

    private Reservation findByIdOrThrow(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada."));
    }

    private boolean isRoomAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> overlappingReservations =
                reservationRepository.findOverlappingReservations(roomId, checkIn, checkOut);
        return overlappingReservations.isEmpty();
    }
}