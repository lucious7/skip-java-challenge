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

import br.com.skip.lucious.entity.Product;
import br.com.skip.lucious.entity.Store;
import br.com.skip.lucious.repository.ProductRepository;
import br.com.skip.lucious.repository.StoreRepository;

@RestController
@RequestMapping("/Store")
public class StoreController {

	@Autowired
	private StoreRepository repo;

	@Autowired
	private ProductRepository productRepo;

	@GetMapping
	public ResponseEntity<Iterable<Store>> listAll() {
		return new ResponseEntity<Iterable<Store>>(repo.findAll(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Store> getOne(@PathVariable("id") Long id) {
		Store store = repo.findById(id).orElse(null);
		HttpStatus status = store != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		return new ResponseEntity<Store>(store, status);
	}

	@RequestMapping("/search/{searchText}")
	public ResponseEntity<Iterable<Store>> search(@PathVariable("searchText") String searchText) {
		Iterable<Store> stores = repo.findByNameContaining(searchText).orElse(new ArrayList<>());

		return new ResponseEntity<Iterable<Store>>(stores, HttpStatus.OK);

	}

	@RequestMapping("/{storeId}/products")
	public ResponseEntity<Iterable<Product>> listProducts(@PathVariable("storeId") Long id) {
		Iterable<Product> products = productRepo.findByStoreId(id).orElse(new ArrayList<>());

		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Store> create(@RequestBody Store store, UriComponentsBuilder builder) {
		Store saved = repo.save(store);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Store/{id}").build(saved.getId()));

		return new ResponseEntity<Store>(saved, headers, HttpStatus.CREATED);
	}

}
