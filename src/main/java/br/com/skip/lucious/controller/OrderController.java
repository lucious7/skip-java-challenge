package br.com.skip.lucious.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.skip.lucious.entity.Order;
import br.com.skip.lucious.repository.OrderRepository;

@RestController
@RequestMapping("/Order")
public class OrderController {

	@Autowired
	private OrderRepository repo;

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOne(@PathVariable("id") Long id) {
		Order order = repo.findById(id).orElse(null);
		HttpStatus status = order != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		return new ResponseEntity<Order>(order, status);
	}

	@GetMapping("/customer")
	public ResponseEntity<Iterable<Order>> listByCustomer() {
		Long id = 1L;
		Iterable<Order> orders = repo.findByCustomerId(id).orElse(new ArrayList<>());
		
		return new ResponseEntity<Iterable<Order>>(orders, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Order> create(@RequestBody Order order, UriComponentsBuilder builder) {
		Order saved = repo.save(order);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Order/{id}").build(saved.getId()));

		return new ResponseEntity<Order>(saved, headers, HttpStatus.CREATED);
	}

}
