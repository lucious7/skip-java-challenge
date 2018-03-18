package br.com.skip.lucious.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.skip.lucious.entity.Store;


public interface StoreRepository  extends CrudRepository<Store, Long> {

	public Optional<Iterable<Store>> findByCuisineId(Long id);

	public Optional<Iterable<Store>> findByNameContaining(String searchText);

}
