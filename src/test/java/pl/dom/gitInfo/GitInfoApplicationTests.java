package pl.dom.gitInfo;


import org.junit.jupiter.api.Test;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.List;

import org.hamcrest.Matchers;

@SpringBootTest
@PropertySource("application.properties")
class GitInfoApplicationTests {

	private final static Logger LOGGER = LoggerFactory.getLogger(GitInfoApplicationTests.class);
	
	@Value("${test.user}")
	private String user;
	
	
	@Test
	void getUserData() {
		LOGGER.info("Test for user " + user);		
		given()
			.when()
				.get("/git/allRepos/{user}", user)
			.then()
				.assertThat()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.time(lessThan(20000L))
				.body("", Matchers.instanceOf(List.class))
				.body("size()", greaterThan(2));
	}
	
}
