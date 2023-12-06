package model;

public class Register {
	private String username; 
	private String password; 
	private String adminCode; 
	private String mold; 


	public Register() {
	}
   
	public Register(String username, String password, String adminCode, String mold) {
		super();
		this.username = username;
		this.password = password;
		this.adminCode = adminCode;
		this.mold = mold;
	}
    
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminCode() {
		return this.adminCode;
	}

	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}

	public String getMold() {
		return this.mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}
	
	
	
}
