package br.com.skip.lucious.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.skip.lucious.entity.Cuisine;
import br.com.skip.lucious.entity.Store;
import br.com.skip.lucious.repository.CuisineRepository;
import br.com.skip.lucious.repository.StoreRepository;

@RestController
@RequestMapping("/Cuisine")
public class CuisineController {
	
	@Autowired
	private CuisineRepository repo;
	
	@Autowired
	private StoreRepository storeRepo;
	
	@RequestMapping
	public ResponseEntity<Iterable<Cuisine>> listAll(){
		return new ResponseEntity<Iterable<Cuisine>>(repo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<Cuisine> getOne(@PathVariable("id") Long id){
		Cuisine cuisine = repo.findById(id).orElse(null);
		HttpStatus status = cuisine != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<Cuisine>(cuisine, status);
	}
	
	@RequestMapping("/search/{searchText}")
	public ResponseEntity<Iterable<Cuisine>> search(@PathVariable("searchText") String searchText){
		Iterable<Cuisine> cuisines = repo.findByNameContaining(searchText).orElse(new ArrayList<>());
		
		return new ResponseEntity<Iterable<Cuisine>>(cuisines, HttpStatus.OK);
		
	}
	
	@RequestMapping("/{cuisineId}/stores")
	public ResponseEntity<Iterable<Store>> listStores(@PathVariable("cuisineId") Long id){
		Iterable<Store> stores = storeRepo.findByCuisineId(id).orElse(new ArrayList<>());
				
		return new ResponseEntity<Iterable<Store>>(stores, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Cuisine> create(@RequestBody Cuisine cuisine, UriComponentsBuilder builder) {
		Cuisine saved = repo.save(cuisine);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Cuisine/{id}").build(saved.getId()));

		return new ResponseEntity<Cuisine>(saved, headers, HttpStatus.CREATED);
	}

}
