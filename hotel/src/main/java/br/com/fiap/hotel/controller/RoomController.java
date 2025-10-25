package br.com.fiap.hotel.controller;

import br.com.fiap.hotel.dto.RoomRequestDTO;
import br.com.fiap.hotel.dto.RoomResponseDTO;
import br.com.fiap.hotel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@Valid @RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
