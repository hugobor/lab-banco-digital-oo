package me.dio.hugobor;

public class LimiteException extends Exception {
	
	private static final long serialVersionUID = 4166528049433503504L;

	public LimiteException() {
		super("Limite máximo para saque atingido.");
	}
	
	public LimiteException(String message) {
		super(message);
	}
}
