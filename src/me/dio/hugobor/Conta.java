package me.dio.hugobor;


/**
 * Classe abstrata para os tipos de Conta.
 * Implementa a inferface de contas (saque, depósito, tranferência,
 * extrato).
 * As subclasses filhas precisam sobrescrever as funções <code>extrato…</code> para implementar a
 * funcionalidade da função <code>extrato</code>.
 * Somente <code>extratoCabecalho</code> precisa ser sobrescrita explicitamente.
 */
public abstract class Conta implements IConta {
	
	private static final int AGENCIA_PADRAO = 1;
	private static int SEQUENCIAL = 1;

	protected final int agencia;
	protected final int numero;
	
	protected Real saldo;
	protected Cliente cliente;

	
	// Propriedades
	public int getAgencia() { return agencia; }
	public int getNumero() { return numero;	}
	public Real getSaldo() { return saldo; }
	

	// Construtores
	protected Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = SEQUENCIAL++;
		this.cliente = cliente;
		this.saldo = Real.of("0.00");
	}

	
	// Interface IConta
	@Override
	public void sacar(Real valor) {
		saldo = saldo.sub(valor);
	}

	@Override
	public void depositar(Real valor) {
		saldo = saldo.add(valor);
	}

	@Override
	public void transferir(Real valor, IConta contaDestino) {
		this.sacar(valor);
		contaDestino.depositar(valor);
	}

	/**
	 * Exibe um cabeçalho para o extrato. Deve conter o nome do tipo de conta.
	 */
	abstract protected String extratoCabecalho();

	/**
	 * Exibe informações comuns para todos os tipos de conta: nome do cliente, agência, número e saldo.
	 */
	protected String extratoInfosComuns() {
		var buildString = new StringBuilder();
		
		buildString.append(String.format("Titular: %s%n", cliente.getNome()));
		buildString.append(String.format("Agencia: %d%n", agencia));
		buildString.append(String.format("Numero: %d%n", numero));
		buildString.append(String.format("Saldo: %s%n", saldo));
		
		return buildString.toString();
	}
	
	/**
	 * Exibe informações específicas para o tipo de conta no extrato.
	 */	
	protected String extratoInfoExtra() {
		return "";
	}
	
	/**
	 * Mostra todos os créditos e débitos da conta. 
	 */
	public String extratoLancamentos() {
		return "";
	}
	
	
	@Override
	public String extrato() {

		var buildString = new StringBuilder();
		String newLine = String.format("%n").intern();
		
		buildString
			.append(extratoCabecalho()).append(newLine)
			.append(extratoInfosComuns())
			.append(extratoInfoExtra())
			.append("=== Extrato ===").append(newLine)
			.append(extratoLancamentos())
			.append("=== Fim ===").append(newLine);
		
		return buildString.toString();
	}
}
