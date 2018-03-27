package br.com.skip.lucious.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.skip.lucious.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public Optional<Customer> findByEmail(String email);
}
