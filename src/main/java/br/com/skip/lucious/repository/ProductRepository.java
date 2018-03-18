package br.com.skip.lucious.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.skip.lucious.entity.Product;


public interface ProductRepository  extends CrudRepository<Product, Long> {

	public Optional<Iterable<Product>> findByStoreId(Long id);

	public Optional<Iterable<Product>> findByNameContaining(String searchText);

}
