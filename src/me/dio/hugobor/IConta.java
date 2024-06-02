package me.dio.hugobor;


/**
 * Interface para contas.
 * Permite depositar, sacar e transferir dinheiro entre contas.
 * Use a função <code>transferirExtrato</code> para exibir informações da conta. Ela utiliza a função
 * <code>extrato</code> para gerar o extrato.
 */
public interface IConta {
	
	/** Saca o valor da conta. */
	void sacar(Real valor);
	
	/** Deposita o valor na conta. */
	void depositar(Real valor);
	
	/** Transfere o valor para outra conta. */
	void transferir(Real valor, IConta contaDestino);
	
	
	// Delegates
	default void sacar(double valor) { sacar(Real.of(valor)); }
	default void depositar(double valor) { depositar(Real.of(valor)); }
	default void transferir(double valor, IConta contaDestino) { transferir(Real.of(valor), contaDestino); }
	
	default void sacar(long valor) { sacar(Real.of(valor)); }
	default void depositar(long valor) { depositar(Real.of(valor)); }
	default void transferir(long valor, IConta contaDestino) { transferir(Real.of(valor), contaDestino); }
	
	default void sacar(String valor) { sacar(Real.of(valor)); }
	default void depositar(String valor) { depositar(Real.of(valor)); }
	default void transferir(String valor, IConta contaDestino) { transferir(Real.of(valor), contaDestino); }

	
	/**
	 * Função que gera um texto do extrato da conta.
	 * Mostra informações básicas da conta. Como o tipo de conta, nome do dono, saldo...
	 * Depois mostra uma lista de lançamentos.
	 */
	String extrato();
	
	
	/**
	 * Imprime o extrado da conta em <code>System.out</code>.
	 * Utiliza a função <code>extrato</code> para gerar o texto a ser impresso.
	 */
	default void imprimirExtrato() {
		System.out.println(extrato());
	}
}
