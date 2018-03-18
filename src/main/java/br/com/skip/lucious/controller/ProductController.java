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
import br.com.skip.lucious.repository.ProductRepository;

@RestController
@RequestMapping("/Product")
public class ProductController {

	@Autowired
	private ProductRepository repo;

	@GetMapping
	public ResponseEntity<Iterable<Product>> listAll() {
		return new ResponseEntity<Iterable<Product>>(repo.findAll(), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getOne(@PathVariable("id") Long id) {
		Product product = repo.findById(id).orElse(null);
		HttpStatus status = product != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;

		return new ResponseEntity<Product>(product, status);
	}

	@RequestMapping("/search/{searchText}")
	public ResponseEntity<Iterable<Product>> search(@PathVariable("searchText") String searchText) {
		Iterable<Product> products = repo.findByNameContaining(searchText).orElse(new ArrayList<>());

		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<Product> create(@RequestBody Product product, UriComponentsBuilder builder) {
		Product saved = repo.save(product);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Product/{id}").build(saved.getId()));

		return new ResponseEntity<Product>(saved, headers, HttpStatus.CREATED);
	}

}
