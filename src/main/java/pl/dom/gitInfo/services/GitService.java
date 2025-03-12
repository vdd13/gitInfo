package pl.dom.gitInfo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.dom.gitInfo.configuration.GitRestClient;
import pl.dom.gitInfo.response.ResponseGitInfo;
import pl.dom.gitInfo.response.ResponseGitBranch;
import pl.dom.gitInfo.response.ResponseGitRepo;

@Service
public class GitService {

	@Autowired
	GitRestClient gitClient;
	
	public ResponseEntity<List<ResponseGitInfo>> getReposAndBranches(String user) {
		return new ResponseEntity<List<ResponseGitInfo>>(getReposFromGit(user), HttpStatus.OK);
	}

	private List<ResponseGitInfo> getReposFromGit(String user) {
		List<ResponseGitInfo> responseGitData = new ArrayList<>();
		ResponseGitRepo[] reposArray = getReposOfUser(user);
		Arrays.asList(reposArray).forEach(repo -> getBranchesForRepos(repo, responseGitData));
		return responseGitData;
	}

	private List<ResponseGitInfo> getBranchesForRepos(ResponseGitRepo repo, List<ResponseGitInfo> responseGitData) {
		ResponseGitBranch[] branchesGitResponse = getBranchesOfRepo(repo.name(), repo.owner().login());
		Arrays.asList(branchesGitResponse).forEach(branch -> 
			responseGitData.add(new ResponseGitInfo(repo.owner().login(), repo.name(), branch.name(), branch.commit().sha())));
		return responseGitData;
	}

	public ResponseGitRepo[] getReposOfUser(String user) {
		return (ResponseGitRepo[])gitClient.getRestClientForUser()
				.get()
				.uri("https://api.github.com/users/{user}/repos", user)
				.retrieve()
				.body(ResponseGitRepo[].class);
	}
	
	public ResponseGitBranch[] getBranchesOfRepo(String repoName, String user){
		return gitClient.getRestClientForUser()
				.get()
				.uri("https://api.github.com/repos/{user}/{repoName}/branches", user, repoName)
				.retrieve()
				.body(ResponseGitBranch[].class);
	}
}
