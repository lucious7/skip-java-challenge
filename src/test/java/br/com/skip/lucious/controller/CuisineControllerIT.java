package br.com.skip.lucious.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.hamcrest.core.StringContains.containsString;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.skip.lucious.AbstractIntegrationTest;

@DatabaseSetup({"classpath:fixtures/cuisine.xml"})
public class CuisineControllerIT extends AbstractIntegrationTest {


	@Test
	public void testRetrieveOne() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURL("/Cuisine/1"), HttpMethod.GET,
				entity, String.class);

		String expected = "{id: 1, name: Pizza}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	@SuppressWarnings("rawtypes")
	public void testRetrieveAll() {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<List> response = restTemplate.exchange(createURL("/Cuisine/"), HttpMethod.GET,
				entity, List.class);

		assertThat(response.getBody().size(), is(4));
	}
	
	@Test
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testSearch() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<List> response = restTemplate.exchange(createURL("/Cuisine/search/nese"), HttpMethod.GET,
				entity, List.class);

		List<String> body = (List<String>) response.getBody().stream().map((r) -> r.toString()).collect(Collectors.toList());

		assertThat(body.size(), is(2));
		assertThat(body, hasItems(containsString("Japanese"), containsString("Chinese")));
	}
	
	@Test
	@DatabaseSetup("classpath:fixtures/store.xml")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void testRetriveByCuisine() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<List> response = restTemplate.exchange(createURL("/Cuisine/3/stores"), HttpMethod.GET,
				entity, List.class);

		List<String> body = (List<String>) response.getBody().stream().map((r) -> r.toString()).collect(Collectors.toList());

		assertThat(body.size(), is(2));
		assertThat(body, hasItems(containsString("Mr Miyagi"), containsString("The Great Sushi")));
	}
	
	@Test
	public void testCreateNew() throws JSONException {
		
		String posted = "{'name': 'Neo Cuisine'}".replace('\'', '"');
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>(posted, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createURL("/Cuisine/"), HttpMethod.POST,
				entity, String.class);
		
		String expected = "{id: 5, name:'Neo Cuisine'}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

}
