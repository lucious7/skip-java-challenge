package br.com.skip.lucious.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.skip.lucious.entity.Order;
import br.com.skip.lucious.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public Order add(Order order) {
		return repo.save(order);
	}

	public Order get(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Iterable<Order> getAll() {
		return repo.findAll();
	}

	public Iterable<Order> getByCustomer(Long id) {
		return repo.findByCustomerId(id).orElse(new ArrayList<>());
	}

}
