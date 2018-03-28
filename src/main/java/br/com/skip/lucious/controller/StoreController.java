package br.com.skip.lucious.controller;

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
import br.com.skip.lucious.service.StoreService;

@RestController
@RequestMapping("/Store")
public class StoreController {

	@Autowired
	private StoreService service;

	@GetMapping
	public ResponseEntity<Iterable<Store>> listAll() {
		return new ResponseEntity<Iterable<Store>>(service.getAll(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Store> getOne(@PathVariable Long id) {
		Store store = service.get(id);
		HttpStatus status = store != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		return new ResponseEntity<Store>(store, status);
	}

	@RequestMapping("/search/{searchText}")
	public ResponseEntity<Iterable<Store>> search(@PathVariable String searchText) {
		Iterable<Store> stores = service.searchByName(searchText);

		return new ResponseEntity<Iterable<Store>>(stores, HttpStatus.OK);

	}

	@RequestMapping("/{storeId}/products")
	public ResponseEntity<Iterable<Product>> listProducts(@PathVariable("storeId") Long id) {
		Iterable<Product> products = service.getProducts(id);

		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Store> create(@RequestBody Store store, UriComponentsBuilder builder) {
		Store saved = service.add(store);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Store/{id}").build(saved.getId()));

		return new ResponseEntity<Store>(saved, headers, HttpStatus.CREATED);
	}

}
