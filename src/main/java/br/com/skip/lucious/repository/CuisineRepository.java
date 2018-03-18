package br.com.skip.lucious.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.skip.lucious.entity.Cuisine;

public interface CuisineRepository extends CrudRepository<Cuisine, Long> {
	
	public Optional<Iterable<Cuisine>> findByNameContaining(String name);

}
