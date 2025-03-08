package pl.dom.gitInfo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class GitRestClient{

	private RestClient gitRestClient;
	
	public RestClient getRestClientForUser() {
		
		gitRestClient = RestClient.builder().
			requestFactory(new HttpComponentsClientHttpRequestFactory())
			.baseUrl("https://api.github.com/")
			.defaultHeader("Accept", "application/vnd.github+json")
			.build();
		return gitRestClient;
	}
}
