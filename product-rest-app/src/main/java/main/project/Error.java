package main.project;

public class Error {
	
	private String message;
	
	public Error(String message) {
		if(message ==null) {
			message = "No Error information";
		}
		setMessage(message);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}