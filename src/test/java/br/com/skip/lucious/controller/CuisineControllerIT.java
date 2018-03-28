package br.com.skip.lucious.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.skip.lucious.AbstractIntegrationTest;

@DatabaseSetup("classpath:fixtures/cuisine.xml")
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
	public void testRetrieveAll() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<List> response = restTemplate.exchange(createURL("/Cuisine/"), HttpMethod.GET,
				entity, List.class);

		assertThat(response.getBody().size(), is(4));
	}

}
