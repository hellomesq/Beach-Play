package br.com.fiap.beach_play_api.controller;

import br.com.fiap.beach_play_api.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations") // definindo as rotas
public class ReservationController {

	private List<Reservation> repository = new ArrayList<>(List.of(
			new Reservation(1L, 1, LocalDate.of(2025, 3, 01), LocalTime.of(10, 00))
	// coloquei esses mocks de teste para essa lista
	));

	// Método Get - Retorna as reservas cadastradas.
	@GetMapping
	public List<Reservation> index() {
		return repository; // retorna a lista criada
	}

	// Método Post - Para criar uma reserva.
	@PostMapping
	public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
		// recebeu a requisição com requestbody e converteu para o objeto reservation
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
		if (res.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(res.get());
	}
}