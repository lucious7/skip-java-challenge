package br.com.skip.lucious.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.skip.lucious.entity.Product;
import br.com.skip.lucious.entity.Store;
import br.com.skip.lucious.repository.ProductRepository;
import br.com.skip.lucious.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	private StoreRepository repo;

	@Autowired
	private ProductRepository productRepo;

	public Store add(Store store) {
		return repo.save(store);
	}

	public Store get(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Iterable<Store> getAll() {
		return repo.findAll();
	}

	public Iterable<Store> searchByName(String searchText) {
		return repo.findByNameContaining(searchText).orElse(new ArrayList<>());
	}

	public Iterable<Product> getProducts(Long storeId) {
		return productRepo.findByStoreId(storeId).orElse(new ArrayList<>());
	}

}
