package pl.dom.gitInfo.model;

public class RepoDTO {

	private String repoOwner;
	private String repoName;
	private String branchName;
	private String branchSHA;

	public String getRepoOwner() {
		return repoOwner;
	}
	public void setRepoOwner(String repoOwner) {
		this.repoOwner = repoOwner;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchSHA() {
		return branchSHA;
	}
	public void setBranchSHA(String branchSHA) {
		this.branchSHA = branchSHA;
	}
}
