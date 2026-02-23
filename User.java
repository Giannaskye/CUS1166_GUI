import java.time.LocalDateTime;

public abstract class User {
	protected String ID;
	protected LocalDateTime time;

	public User(String ID) {
		this.ID = ID;
		this.time = LocalDateTime.now();
	}

	// returns user ID
	public String getID() {
		return ID;
	}

	// returns time stamp of user transaction
	public LocalDateTime getTime() {
		return time;
	}
	//Converts user objects to strings within text file
	public abstract String fileText();

}
