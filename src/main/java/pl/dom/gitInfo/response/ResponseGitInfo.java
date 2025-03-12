package pl.dom.gitInfo.response;

public record ResponseGitInfo (
	String repoOwner,
	String repoName,
	String branchName,
	String branchSHA
)
{}
