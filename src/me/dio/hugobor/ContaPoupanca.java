package me.dio.hugobor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ContaPoupanca extends Conta {
	
	// TODO Adicional conta especial (limite)
	// TODO Adicionar objeto Agência
	// TODO Colocar mais coisa no cliente
	// TODO Adicionar extrato de lançamento

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
		return forClient(new Cliente(nomeCliente));
	}
	
	public static IConta forNewClient(String nomeCliente, BigDecimal rendimentoMes) {
		return forClient(new Cliente(nomeCliente), rendimentoMes);
	}	
	
	
	// Propriedades
	public BigDecimal getRendimentoMes() { return rendimentoMes; }
	public void setRendimentoMes(BigDecimal rendimentoMes) { this.rendimentoMes = rendimentoMes; }
	
	/**
	 * Valor decimal do rendimento.
	 */
	public BigDecimal getRendimentoMesPercento() {
		return rendimentoMes.divide(BigDecimal.valueOf(100));
	}
	
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
		var rendimento = saldo.getValue().multiply(getRendimentoMesPercento());
		
		return Real.of(rendimento);
	}

	
	/**
	 * Montante do rendimento acumulado em <code>m</code> mêses.
	 * M = C·(1 + i)^t 
	 */
	public Real montanteRendimentoMeses(int m) {
		BigDecimal C = saldo.getValue();
		BigDecimal i = getRendimentoMesPercento();
		int t = m;
		
		BigDecimal M = C.multiply( (BigDecimal.ONE.add(i)).pow(t) );
		
		return Real.of(M); //of faz arredondamento
	}
}
