package me.dio.hugobor;

import java.math.BigDecimal;

/**
 * Conta especial. Pode realizar saques além do limite de saldo utilizando o limite de cheque especial.
 * A propriedade {@link ContaEspecial#limiteChequeEspecial} define o saque máximo além do saldo.
 * A propriedade {@link ContaEspecial#taxaEspecial} define os juros mensais quando o limite especial é utilizado, Em %..
 */
public class ContaEspecial extends Conta {
	
	private Real limiteChequeEspecial = Real.of("1000.00");
	private BigDecimal taxaEspecial = new BigDecimal("15");

	// Propriedades
	public Real getLimiteChequeEspecial() { return limiteChequeEspecial; }
	public void setLimiteChequeEspecial(Real limiteChequeEspecial) { this.limiteChequeEspecial = limiteChequeEspecial; }
	public BigDecimal getTaxaEspecial() { return taxaEspecial; }
	public void setTaxaEspecial(BigDecimal taxaEspecial) { this.taxaEspecial = taxaEspecial; }
	
	
	// Construtores
	private ContaEspecial(Cliente cliente) {
		super(cliente);
	}
	
	private ContaEspecial(Cliente cliente, Real limiteChequeEspecial) {
		super(cliente);
		this.limiteChequeEspecial = limiteChequeEspecial;
	}

	
	private ContaEspecial(Cliente cliente, BigDecimal taxaEspecial) {
		super(cliente);
		this.taxaEspecial = taxaEspecial;
	}
	
	private ContaEspecial(Cliente cliente, Real limiteChequeEspecial, BigDecimal taxaEspecial) {
		super(cliente);
		this.limiteChequeEspecial = limiteChequeEspecial;
		this.taxaEspecial = taxaEspecial;
	}	
	
	public static IConta forClient(Cliente cliente) {
		return new ContaEspecial(cliente);
	}
	
	public static IConta forClient(Cliente cliente, Real limiteChequeEspecial) {
		return new ContaEspecial(cliente, limiteChequeEspecial);
	}
	
	public static IConta forClient(Cliente cliente, BigDecimal taxaEsecial) {
		return new ContaEspecial(cliente, taxaEsecial);
	}
	
	public static IConta forClient(Cliente cliente, Real limiteChequeEspecial, BigDecimal taxaEspecial) {
		return new ContaEspecial(cliente, limiteChequeEspecial, taxaEspecial);
	}
	
	public static IConta forNewClient(String nomeCliente) {
		return forClient(Cliente.of(nomeCliente));
	}
	
	public static IConta forNewClient(String nomeCliente, Real limiteChequeEspecial) {
		return forClient(Cliente.of(nomeCliente), limiteChequeEspecial);
	}
	
	public static IConta forNewClient(String nomeCliente, BigDecimal taxaEspecial) {
		return forClient(Cliente.of(nomeCliente), taxaEspecial);
	}
	
	public static IConta forNewClient(String nomeCliente, Real limiteChequeEspecial, BigDecimal taxaEspecial) {
		return forClient(Cliente.of(nomeCliente), limiteChequeEspecial, taxaEspecial);
	}
	
	
	//Overrides
	
	/**
	 * O limite da conta especial é o soldo + cheque especial.
	 */
	@Override
	public Real limite() {
		return saldo().add(limiteChequeEspecial);
	}
	
	@Override
	protected String extratoCabecalho() {
		return "=== Extrato Conta Especial ===";
	}
	
	@Override
	protected String extratoInfoExtra() {
		return String.format("Limite de Total Cheque Especial: %s%n", limiteChequeEspecial.toString());
	}
	
	@Override
	public String toString() {
		return String.format("Conte Especial de %s", cliente.toString());
	}
	
	
	//Métodos
	
	/**
	 * Se esta conta está utilizando o limite. O saldo é negativo.
	 */
	public boolean emDivida() {
		return saldo.getValue().compareTo(BigDecimal.ZERO) < 0; 
	}
	
	/**
	 * Valor do limite em utilização. 0 se não estiver utlizando o limite.
	 */
	public Real limiteUtilizado() {
		if (! emDivida()) return Real.of(0);
		
		return Real.of(saldo().getValue().negate());
	}
	
	public Real dividaMeses(int meses) {
		BigDecimal i = Real.percentoParaDecimal(taxaEspecial);
		int n = meses;
		return limiteUtilizado().montanteJurosCompostos(i, n);
	}
	
}
