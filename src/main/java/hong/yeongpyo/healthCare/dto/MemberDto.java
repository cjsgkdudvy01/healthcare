package hong.yeongpyo.healthCare.dto;

public class MemberDto {
	private  int memberno;
	private  String memberid;
	private  String membernick;
	private  String memberpassword;
	private  int membertall;
	private  int membercheck;
	private  int memberphone1;
	private  int memberphone2;
	private  int memberphone3;
	private  String memberemail;
	
	public MemberDto() {}

	public MemberDto(int memberno, String memberid, String membernick, String memberpassword, int membertall,
			int membercheck, int memberphone1, int memberphone2, int memberphone3, String memberemail) {
		super();
		this.memberno = memberno;
		this.memberid = memberid;
		this.membernick = membernick;
		this.memberpassword = memberpassword;
		this.membertall = membertall;
		this.membercheck = membercheck;
		this.memberphone1 = memberphone1;
		this.memberphone2 = memberphone2;
		this.memberphone3 = memberphone3;
		this.memberemail = memberemail;
	}

	public int getMemberno() {
		return memberno;
	}

	public void setMemberno(int memberno) {
		this.memberno = memberno;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getMembernick() {
		return membernick;
	}

	public void setMembernick(String membernick) {
		this.membernick = membernick;
	}

	public String getMemberpassword() {
		return memberpassword;
	}

	public void setMemberpassword(String memberpassword) {
		this.memberpassword = memberpassword;
	}

	public int getMembertall() {
		return membertall;
	}

	public void setMembertall(int membertall) {
		this.membertall = membertall;
	}

	public int getMembercheck() {
		return membercheck;
	}

	public void setMembercheck(int membercheck) {
		this.membercheck = membercheck;
	}

	public int getMemberphone1() {
		return memberphone1;
	}

	public void setMemberphone1(int memberphone1) {
		this.memberphone1 = memberphone1;
	}

	public int getMemberphone2() {
		return memberphone2;
	}

	public void setMemberphone2(int memberphone2) {
		this.memberphone2 = memberphone2;
	}

	public int getMemberphone3() {
		return memberphone3;
	}

	public void setMemberphone3(int memberphone3) {
		this.memberphone3 = memberphone3;
	}

	public String getMemberemail() {
		return memberemail;
	}

	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}
}
