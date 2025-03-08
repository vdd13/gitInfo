package pl.dom.gitInfo.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.dom.gitInfo.configuration.GitRestClient;
import pl.dom.gitInfo.model.RepoDTO;

@Service
public class GitService {

	@Autowired
	GitRestClient gitClient;
	
	public ResponseEntity<List<RepoDTO>> getReposAndBranches(String user) {
		return new ResponseEntity<List<RepoDTO>>(getReposFromGit(user), HttpStatus.OK);
	}

	private List<RepoDTO> getReposFromGit(String user) {
		List<RepoDTO> reposDTO = new ArrayList<>();
		JSONArray reposArray = new JSONArray(getReposOfUser(user));
		reposArray.forEach(repo -> getBranchesForRepos((JSONObject)repo, reposDTO));	
		return reposDTO;
	}

	private List<RepoDTO> getBranchesForRepos(JSONObject repo, List<RepoDTO> reposDTO) {
		String repoName = repo.get("name").toString();
		String repoOwner = repo.getJSONObject("owner").getString("login");
		String branchesGitResponse = getBranchesOfRepo(repoName);
		JSONArray branchesArray = new JSONArray(branchesGitResponse);
		
		return setReposAndBranchesData(reposDTO, repoName, repoOwner, branchesArray);
	}

	private List<RepoDTO> setReposAndBranchesData(List<RepoDTO> reposDTO, String repoName, String repoOwner, JSONArray branchesArray) {
		for(int i = 0; i < branchesArray.length(); i++) {
			JSONObject branch = (JSONObject) branchesArray.get(i);
			
			RepoDTO branchOfRepo = new RepoDTO();
			branchOfRepo.setRepoName(repoName);
			branchOfRepo.setRepoOwner(repoOwner);
			branchOfRepo.setBranchName(branch.get("name").toString());
			branchOfRepo.setBranchSHA(branch.getJSONObject("commit").get("sha").toString());
			
			reposDTO.add(branchOfRepo);
		}
		return reposDTO;
	}
	
	public String getReposOfUser(String user) {
		return gitClient.getRestClientForUser()
				.get()
				.uri("https://api.github.com/users/{user}/repos", user)
				.retrieve()
				.body(String.class);
	}
	
	public String getBranchesOfRepo(String repoName){
		return gitClient.getRestClientForUser()
				.get()
				.uri("https://api.github.com/repos/vdd13/{repoName}/branches", repoName)
				.retrieve()
				.body(String.class);
	}
}
