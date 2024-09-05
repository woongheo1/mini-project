package project;

public class MemVO {

	private String knum;
	private String kid;
	private String kpw;
	private String kemail;
	private String deleteyn;
	private String insertdate;
	private String updatedate;
	
	//생성자
	public MemVO() {
		
	}

	public MemVO(String knum, String kid, String kpw, String kemail, String deleteyn, String insertdate,
			String updatedate) {

		this.knum = knum;
		this.kid = kid;
		this.kpw = kpw;
		this.kemail = kemail;
		this.deleteyn = deleteyn;
		this.insertdate = insertdate;
		this.updatedate = updatedate;
	}
	
	//setter, getter
	public String getKnum() {
		return knum;
	}

	public String getKid() {
		return kid;
	}

	public String getKpw() {
		return kpw;
	}

	public String getKemail() {
		return kemail;
	}

	public String getDeleteyn() {
		return deleteyn;
	}

	public String getInsertdate() {
		return insertdate;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setKnum(String knum) {
		this.knum = knum;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setKpw(String kpw) {
		this.kpw = kpw;
	}

	public void setKemail(String kemail) {
		this.kemail = kemail;
	}

	public void setDeleteyn(String deleteyn) {
		this.deleteyn = deleteyn;
	}

	public void setInsertdate(String insertdate) {
		this.insertdate = insertdate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	
	
}
