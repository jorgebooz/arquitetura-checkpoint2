package br.com.fiap.hotel.repository;

import br.com.fiap.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumber(Integer number);
}

