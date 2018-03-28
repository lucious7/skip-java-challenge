package br.com.skip.lucious.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.skip.lucious.entity.Cuisine;
import br.com.skip.lucious.entity.Store;
import br.com.skip.lucious.repository.CuisineRepository;
import br.com.skip.lucious.repository.StoreRepository;

@Service
public class CuisineService {

	@Autowired
	private CuisineRepository repo;

	@Autowired
	private StoreRepository storeRepo;

	public Cuisine add(Cuisine cuisine) {
		return repo.save(cuisine);
	}

	public Cuisine get(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Iterable<Cuisine> getAll() {
		return repo.findAll();
	}

	public Iterable<Cuisine> searchByName(String searchText) {
		return repo.findByNameContaining(searchText).orElse(new ArrayList<>());
	}

	public Iterable<Store> getStores(Long cuisineId) {
		return storeRepo.findByCuisineId(cuisineId).orElse(new ArrayList<>());
	}

}
