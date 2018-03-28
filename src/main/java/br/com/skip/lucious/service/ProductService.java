package br.com.skip.lucious.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.skip.lucious.entity.Product;
import br.com.skip.lucious.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	public Product add(Product product) {
		return repo.save(product);
	}

	public Product get(Long id) {
		return repo.findById(id).orElse(null);
	}

	public Iterable<Product> getAll() {
		return repo.findAll();
	}

	public Iterable<Product> searchByName(String searchText) {
		return repo.findByNameContaining(searchText).orElse(new ArrayList<>());
	}

}
