package br.com.skip.lucious;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, 
						  DirtiesContextTestExecutionListener.class,
						  TransactionalTestExecutionListener.class, 
						  DbUnitTestExecutionListener.class })
public abstract class AbstractIntegrationTest {
	
	@LocalServerPort
	protected int port;
	
	@Value("${server.servlet.contextPath}")
	protected String prefix;
	
	protected TestRestTemplate restTemplate = new TestRestTemplate();

	protected HttpHeaders headers = new HttpHeaders();
	
	protected String createURL(String uri) {
		return "http://localhost:" + port + prefix + uri;
	}

}
