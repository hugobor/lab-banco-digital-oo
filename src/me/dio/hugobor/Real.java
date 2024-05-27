package me.dio.hugobor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

public class Real {
	
	private final BigDecimal value;
	
	
	// Propriedades
	public BigDecimal getValue() { return value; };
	
	
	// Construtores
	private Real() {
		value = BigDecimal.ZERO.setScale(2);
	}
	
	private Real(BigDecimal value) {
		this.value = value.setScale(2);
	}
	
	public static Real of(long centavos) {
		return new Real(BigDecimal.valueOf(centavos, 2));
	}

	public static Real of(long reais, long centavos) {
		long inCents = Math.abs(reais) * 100 + Math.abs(centavos);
		inCents = inCents * (reais < 0 ? -1L : 1L);
				
		return Real.of(inCents);
	}
	
	public static Real of(double reais) {
		var decimal = BigDecimal.valueOf(reais);
		return new Real(decimal.setScale(2));
	}
	
	public static Real of(String reais) {
		var decimal = new BigDecimal(reais);
		return new Real(decimal.setScale(2));
	}

	// Overrides
	@Override
	public String toString() {
		var strBuilder = new StringBuilder();
		var decimalValue = value;
		
		if (value.compareTo(BigDecimal.ZERO) < 0) {
			strBuilder.append("- R$ ");
			decimalValue = value.abs();
		} else {
			strBuilder.append("R$ ");
		}
		
		var formater = new DecimalFormat();
		formater.applyLocalizedPattern("#.##0,00");
		strBuilder.append(formater.format(decimalValue));
		
		return strBuilder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Real other = (Real) obj;
		return Objects.equals(value, other.value);
	}
	
	
	// Métodos
	public Real add(Real other) {
		return new Real(this.getValue().add(other.getValue()));
	}
	
	public Real sub(Real other) {
		return new Real(this.getValue().subtract(other.getValue()));
	}
}
