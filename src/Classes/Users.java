package Classes;

public class Users {

	private int id;
	private String username, email, password;

	public int getId() {
		return id;
	}
	
	public Users() {
		super();
	}

	public Users(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public Users(int id, String username, String email, String password) {
		
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Users(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Users(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}