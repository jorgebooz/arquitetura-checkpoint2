package br.com.fiap.hotel.service;

import br.com.fiap.hotel.dto.RoomRequestDTO;
import br.com.fiap.hotel.dto.RoomResponseDTO;
import br.com.fiap.hotel.model.Room;
import br.com.fiap.hotel.model.RoomStatus;
import br.com.fiap.hotel.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomResponseDTO create(RoomRequestDTO dto) {
        roomRepository.findByNumber(dto.number()).ifPresent(r -> {
            throw new IllegalStateException("Já existe um quarto com este número.");
        });

        Room room = new Room(
                dto.number(),
                dto.type(),
                dto.capacity(),
                dto.dailyRate(),
                RoomStatus.ATIVO
        );

        roomRepository.save(room);
        return new RoomResponseDTO(room);
    }

    public List<RoomResponseDTO> listAll() {
        return roomRepository.findAll()
                .stream()
                .map(RoomResponseDTO::new)
                .toList();
    }

    public RoomResponseDTO getById(Long id) {
        Room room = findByIdOrThrow(id);
        return new RoomResponseDTO(room);
    }

    @Transactional
    public RoomResponseDTO update(Long id, RoomRequestDTO dto) {
        Room room = findByIdOrThrow(id);

        // Atualiza apenas os campos permitidos
        room.setType(dto.type());
        room.setCapacity(dto.capacity());
        room.setDailyRate(dto.dailyRate());
        room.setStatus(dto.status());

        return new RoomResponseDTO(roomRepository.save(room));
    }

    @Transactional
    public void delete(Long id) {
        Room room = findByIdOrThrow(id);
        roomRepository.delete(room);
    }

    private Room findByIdOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado."));
    }
}
