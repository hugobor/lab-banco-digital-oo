package me.dio.hugobor;

public interface IConta {
	
	void sacar(Real valor);
	void depositar(Real valor);
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
	
	
	void imprimirExtrato();
}
