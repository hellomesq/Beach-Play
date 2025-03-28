package br.com.fiap.beach_play_api.controller;

import br.com.fiap.beach_play_api.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // Permite requisições do frontend
@RestController
@RequestMapping("/reservations") // definindo as rotas
public class ReservationController {

    private List<Reservation> repository = new ArrayList<>(List.of(
            new Reservation(1L, 1, LocalDate.of(2025, 3, 1), LocalTime.of(10, 0))
    ));

    // Método Get - Retorna as reservas cadastradas.
    @GetMapping
    public List<Reservation> index() {
        return repository;
    }

    // Método Post - Para criar uma reserva.
    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        System.out.println("Cadastrando a reserva na quadra: " + reservation.getQuadra());
        repository.add(reservation);
        return ResponseEntity.status(201).body(reservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> get(@PathVariable Long id) {
        System.out.println("Buscando reserva " + id);
        var res = repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return res.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
