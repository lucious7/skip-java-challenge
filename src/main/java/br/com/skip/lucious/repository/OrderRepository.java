package br.com.skip.lucious.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.skip.lucious.entity.Order;


public interface OrderRepository  extends CrudRepository<Order, Long> {

	public Optional<Iterable<Order>> findByCustomerId(Long id);

}
