package me.dio.hugobor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata para os tipos de Conta.
 * Implementa a inferface de {@link Conta#Conta(Cliente)} (saque, depósito, tranferência,
 * extrato, etc…).
 * As subclasses filhas precisam sobrescrever as funções {@code extrato…} para implementar a
 * funcionalidade da função  {@link IConta#extrato()}.
 * Somente {@link Conta#extratoCabecalho()} precisa ser sobrescrita explicitamente.
 * Funções de saque tratam o caso de limite (lançam exceção).
 */
public abstract class Conta implements IConta {
	
	private static final int AGENCIA_PADRAO = 1;
	private static int SEQUENCIAL = 1;

	protected final int agencia;
	protected final int numero;
	
	protected Real saldo;
	protected Cliente cliente;
	
	protected List<Lancamento> lancamentos;

	
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
		
		this.lancamentos = new ArrayList<>();
		
		Banco.bancoCentral.getContas().add(this);
	}

	
	// Interface IConta
	@Override
	public void sacar(Real valor) throws LimiteException {
		BigDecimal valorDec = valor.getValue();
		BigDecimal limiteDec = this.limite().getValue();
		if (limiteDec.compareTo(valorDec) < 0) {
			var extra = Real.of(limiteDec.subtract(valorDec).abs());
			throw new LimiteException(String.format("Valor de saque ultrapassou o limite em %s.", extra));
		} 
		
		saldo = saldo.sub(valor);
	
		lancamentos.addLast(new Lancamento.Saque(this, valor));
	}

	@Override
	public void depositar(Real valor) {
		saldo = saldo.add(valor);
		
		lancamentos.addLast(new Lancamento.Deposito(this, valor));
	}

	@Override
	public void transferir(Real valor, IConta contaDestino) throws LimiteException {
		this.sacar(valor);
		contaDestino.depositar(valor);
		
		lancamentos.removeLast(); // Remove saque para trocar por transferência. Continua como depósito no destino.
		lancamentos.addLast(new Lancamento.Transferencia(this, contaDestino, valor));		
	}
	
	@Override
	public Real saldo() {
		return saldo;
	}
	
	@Override
	public Real limite() {
		return saldo();
	}
	
	@Override
	public List<Lancamento> lancamentos() {
		return List.copyOf(lancamentos);
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
		buildString.append(String.format("Limite: %s%n", limite()));
		
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
		var buildString = new StringBuilder();
		String newLine = String.format("%n").intern();
		
		lancamentos().forEach(lancamento -> {
			buildString
				.append(lancamento.toString())
				.append(".")
				.append(newLine);
		});
		
		return buildString.toString();
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
