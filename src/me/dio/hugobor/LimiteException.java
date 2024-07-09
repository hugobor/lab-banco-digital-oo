package me.dio.hugobor;


/**
 * Exceção lançada quando é feito um saque/transfêrenci além do limite da conta.
 */
public class LimiteException extends Exception {
	
	private static final long serialVersionUID = 4166528049433503504L;

	public LimiteException() {
		super("Limite máximo para saque atingido.");
	}
	
	public LimiteException(String message) {
		super(message);
	}
}
