package me.dio.hugobor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;


/**
 * Classe que representa números Reais em formato decimal.
 * User <code>Real.of</code> para criar um Real.
 * Reais negativos são possíveis. Eles representam dívidas.
 */
public class Real {
	
	private final BigDecimal value;
	
	/**
	 * Objeto para arredondamento. Usar o ABNT 5891, arrendonda para o número par mais próximo quando
	 * precisa arrendodar o 5. Não sei se esse é o correto para arredondar reais.
	 */
	public static final RoundingMode rounding = RoundingMode.HALF_EVEN;

	
	// Propriedades
	public BigDecimal getValue() { return value; };
	
	
	// Construtores
	private Real() {
		value = BigDecimal.ZERO.setScale(2);
	}
	
	private Real(BigDecimal value) {
		this.value = value.setScale(2, Real.rounding);
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
	
	public static Real of(BigDecimal toBeRounded) {
		return new Real(toBeRounded);
	}

	
	// Overrides
	@Override
	public String toString() {
		var strBuilder = new StringBuilder();
		var decimalValue = value;
		
		if (value.compareTo(BigDecimal.ZERO) < 0) {
			strBuilder.append("- ");
			decimalValue = value.abs();
		}
		
		var formater = new DecimalFormat();
		formater.applyLocalizedPattern("R$ #.##0,00");
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
	/**
	 * Soma reais.
	 */
	public Real add(Real other) {
		return new Real(this.getValue().add(other.getValue()));
	}
	
	/**
	 * Subtrai reais.
	 */
	public Real sub(Real other) {
		return new Real(this.getValue().subtract(other.getValue()));
	}
	
	
	/**
	 * Calcula juros compostos.
	 * Fórmula: M = C·(1 + i)^n
	 * M = montante.
	 * C = capital aplicadao. É o valor que recebe o método ({@code this}).
	 * i = taxa de juros (% em decimal, use a função estática {@code percentParaDecimal}).
	 * n = períodos: mêses, anos, semanas, etc. Deve ser de acordo com a taxa de juros.
	 */
	public Real montanteJurosCompostos(BigDecimal i, int n) {
		BigDecimal C = this.getValue();
		BigDecimal M = C.multiply( (BigDecimal.ONE.add(i)).pow(n) );
		
		return Real.of(M);
	}
	
	
	
	// Funções auxiliáres
	
	/**
	 * Valor de percentagem para decimal.
	 * Ex. 100% -> 1.00;
	 * 50% -> 0.5;
	 * 0.13% -> 0.0013
	 */
	static public BigDecimal percentoParaDecimal(BigDecimal percento) {
		return percento.divide(BigDecimal.valueOf(100));
	}
	
}
