package pl.dom.gitInfo;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@PropertySource("application.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GitInfoApplicationTests {

	@Value("${test.user}")
	private String user;
	
	static ClientAndServer mockServer;

	@BeforeAll
	static void beforeAll() {
		mockServer = startClientAndServer();
	}
	
	@AfterAll
	static void afterAll() {
		mockServer.stop();
	}
	
	@BeforeEach
	void setUp() {
		mockServer.reset();
	}
	
	@Test 
	void getUserReposAndBranches() {
		mockServer.when(
				request()
					.withMethod("GET")
					.withPath("http://localhost:8080/git/allRepos/"+user))
				.respond(response()
					.withStatusCode(200)
					.withHeader("Content-type", "application/vnd.github+json"));
		
	}
}
