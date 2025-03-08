package pl.dom.gitInfo.controlers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import pl.dom.gitInfo.model.RepoDTO;
import pl.dom.gitInfo.services.GitService;

@RestController
@RequestMapping("git")
public class GitController {

	@Autowired
	GitService gitService;

	@GetMapping("allRepos/{user}")
	public ResponseEntity<List<RepoDTO>> getGitRepos(@PathVariable String user) {
		return gitService.getReposAndBranches(user);
	}
	
    
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleHTTPClientError(HttpClientErrorException e){
        if (e.getStatusCode() == HttpStatus.FORBIDDEN){
        	return new ResponseEntity<Map<String, Object>>(
            		Map.of("status",HttpStatus.FORBIDDEN.name(), "message", "API rate limit exceeded"), HttpStatus.FORBIDDEN);
	    } else if (e.getStatusCode() == HttpStatus.NOT_FOUND){
	    	return new ResponseEntity<Map<String, Object>>(
	        		Map.of("status",HttpStatus.NOT_FOUND.name(), "message", "User not found"), HttpStatus.NOT_FOUND);
	    } else {
	    	return new ResponseEntity<Map<String, Object>>(
	        		Map.of("status",HttpStatus.INTERNAL_SERVER_ERROR.name(), "message", e.getClass()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
    }
}

