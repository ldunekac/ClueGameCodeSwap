package clueGame;

public class BadConfigFormatException extends RuntimeException {
	
	public BadConfigFormatException(String msg) {
		super(msg);
	}

	public String toString() {
		return super.getMessage();				
	}

}
