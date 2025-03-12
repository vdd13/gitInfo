package pl.dom.gitInfo.response;

public record ResponseGitBranch(
		String name, 
		Commit commit) 
{}
