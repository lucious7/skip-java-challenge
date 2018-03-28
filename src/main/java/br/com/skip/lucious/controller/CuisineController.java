package br.com.skip.lucious.controller;

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
import br.com.skip.lucious.service.CuisineService;

@RestController
@RequestMapping("/Cuisine")
public class CuisineController {
	
	@Autowired
	private CuisineService service;
	
	@RequestMapping
	public ResponseEntity<Iterable<Cuisine>> listAll(){
		return new ResponseEntity<Iterable<Cuisine>>(service.getAll(), HttpStatus.OK);
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<Cuisine> getOne(@PathVariable Long id){
		Cuisine cuisine = service.get(id);
		HttpStatus status = cuisine != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		
		return new ResponseEntity<Cuisine>(cuisine, status);
	}
	
	@RequestMapping("/search/{searchText}")
	public ResponseEntity<Iterable<Cuisine>> search(@PathVariable String searchText){
		Iterable<Cuisine> cuisines = service.searchByName(searchText);
		
		return new ResponseEntity<Iterable<Cuisine>>(cuisines, HttpStatus.OK);
		
	}
	
	@RequestMapping("/{cuisineId}/stores")
	public ResponseEntity<Iterable<Store>> listStores(@PathVariable("cuisineId") Long id){
		Iterable<Store> stores = service.getStores(id);
				
		return new ResponseEntity<Iterable<Store>>(stores, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Cuisine> create(@RequestBody Cuisine cuisine, UriComponentsBuilder builder) {
		Cuisine saved = service.add(cuisine);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/Cuisine/{id}").build(saved.getId()));

		return new ResponseEntity<Cuisine>(saved, headers, HttpStatus.CREATED);
	}

}
