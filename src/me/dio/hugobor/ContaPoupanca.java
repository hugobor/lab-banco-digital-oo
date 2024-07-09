package me.dio.hugobor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ContaPoupanca extends Conta {
	
	// TODO Adicional conta especial (limite)
	// TODO Adicionar objeto Agência
	// TODO Colocar mais coisa no cliente

	/** Taxa de rendimento da poupança. % */
	private BigDecimal rendimentoMes = new BigDecimal("0.5");
	
	
	// Construtores
	private ContaPoupanca(Cliente cliente) {
		super(cliente);
	}
	
	private ContaPoupanca(Cliente cliente, BigDecimal rendimentoMes) {
		super(cliente);
		this.rendimentoMes = rendimentoMes;
	}
	
	public static IConta forClient(Cliente cliente) {
		return new ContaPoupanca(cliente);
	}
	
	public static IConta forClient(Cliente cliente, BigDecimal rendimentoMes) {
		return new ContaPoupanca(cliente, rendimentoMes);
	}
	
	public static IConta forNewClient(String nomeCliente) {
		return forClient(Cliente.of(nomeCliente));
	}
	
	public static IConta forNewClient(String nomeCliente, BigDecimal rendimentoMes) {
		return forClient(Cliente.of(nomeCliente), rendimentoMes);
	}	
	
	
	// Propriedades
	public BigDecimal getRendimentoMes() { return rendimentoMes; }
	public void setRendimentoMes(BigDecimal rendimentoMes) { this.rendimentoMes = rendimentoMes; }
	
	
	public String rendimentoMesToString() {
		var formater = new DecimalFormat();
		formater.applyLocalizedPattern("#0,0#");
		
		return formater.format(rendimentoMes) + " %";
	}
	
	
	// Overrides de Conta
	@Override
	public String extratoCabecalho() {
		return "=== Extrato Conta Poupança ===";
	}

	@Override
	protected String extratoInfoExtra() {
		
		var formater = new DecimalFormat();
		formater.applyLocalizedPattern("0,00");
		
		return String.format("Rendimento Mensal: %s%n", rendimentoMesToString()); 
	}

	/**
	 * Rendimento em um mês.
	 */
	public Real calculaRendimentoMes() {
		BigDecimal rendimento = saldo.getValue().multiply(Real.percentoParaDecimal(rendimentoMes));
		
		return Real.of(rendimento);
	}

	
	/**
	 * Montante do rendimento acumulado em {@code m} mêses.
	 * M = C·(1 + i)^t 
	 */
	public Real montanteRendimentoMeses(int m) {
		return saldo.montanteJurosCompostos(Real.percentoParaDecimal(rendimentoMes), m); 
	}
	
	
	@Override
	public String toString() {
		return String.format("Conta Poupança de %s", cliente.toString());
	}
}
