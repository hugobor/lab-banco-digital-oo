package me.dio.hugobor;

/**
 * Conta básica.
 */
public class ContaCorrente extends Conta {

	//Construtores
	private ContaCorrente(Cliente cliente) {
		super(cliente);
	}

	public static IConta forClient(Cliente cliente) {
		return new ContaCorrente(cliente);
	}
	
	public static IConta forNewClient(String nomeCliente) {
		return forClient(Cliente.of(nomeCliente));
	}
	

	@Override
	public String extratoCabecalho() {
		return "=== Extrato Conta Corrente ===";
	}
}
