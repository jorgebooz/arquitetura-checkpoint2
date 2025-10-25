package br.com.fiap.hotel.model;

import br.com.fiap.hotel.exceptions.ConflictException;

import java.util.Map;
import java.util.Set;

public class ReservationStatusTransition {

    private static final Map<ReservationStatus, Set<ReservationStatus>> ALLOWED_TRANSITIONS = Map.of(
            ReservationStatus.CREATED, Set.of(ReservationStatus.CHECKED_IN, ReservationStatus.CANCELED),
            ReservationStatus.CHECKED_IN, Set.of(ReservationStatus.CHECKED_OUT),
            ReservationStatus.CHECKED_OUT, Set.of(),
            ReservationStatus.CANCELED, Set.of()
    );

    public static void validateTransition(ReservationStatus current, ReservationStatus next) {
        Set<ReservationStatus> allowedNextStatus = ALLOWED_TRANSITIONS.get(current);

        if (!allowedNextStatus.contains(next)) {
            throw new ConflictException(
                    String.format(
                            "Transição de status inválida: %s → %s. Transições permitidas: %s",
                            current,
                            next,
                            allowedNextStatus.isEmpty() ? "Nenhuma" : allowedNextStatus
                    )
            );
        }
    }

    public static Set<ReservationStatus> getAllowedTransitions(ReservationStatus current) {
        return ALLOWED_TRANSITIONS.get(current);
    }
}