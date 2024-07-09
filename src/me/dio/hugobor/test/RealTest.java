package me.dio.hugobor.test;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import me.dio.hugobor.Real;

class RealTest {

	@Test
	void testCreate() {
		assertEquals(BigDecimal.ZERO.setScale(2), Real.of(0, 0).getValue());
		assertEquals(BigDecimal.ONE.setScale(2), Real.of(1, 0).getValue());
		assertEquals(BigDecimal.ONE.setScale(2), Real.of(0, 100).getValue());
		
		assertEquals(new BigDecimal("666.99"), Real.of(666, 99).getValue());
		assertEquals(new BigDecimal("-666.99"), Real.of(-666, 99).getValue());
		
		assertEquals(new BigDecimal("123456789.98"), Real.of(123456789, 98).getValue());
		assertEquals(new BigDecimal("-123456789.98"), Real.of(-123456789, 98).getValue());
		
		assertEquals(new BigDecimal("1.00"), Real.of("1").getValue());
		assertEquals(new BigDecimal("1.22"), Real.of(1.22).getValue());
		assertEquals(new BigDecimal("123456789.10"), Real.of(123456789.1).getValue());
		assertEquals(new BigDecimal("-123456789.10"), Real.of(-123456789.1).getValue());
		
		assertEquals(new BigDecimal("1.00"), Real.of(100).getValue());
		assertEquals(new BigDecimal("4.12"), Real.of(412).getValue());
		assertEquals(new BigDecimal("-666999.69"), Real.of(-66699969).getValue());
	}
	
	@Test
	void testToString() {
		assertEquals("R$ 0,00", Real.of(0, 0).toString());
		assertEquals("R$ 123,45", Real.of(123, 45).toString());
		assertEquals("R$ 1.234.567,45", Real.of(1234567, 45).toString());
		
		assertEquals("- R$ 1.234.567,45", Real.of("-1234567.45").toString());
		assertEquals("- R$ 123,00", Real.of("-123.00").toString());
	}
	
	@Test
	void testAdd() {
		assertEquals(Real.of("10000.00"), Real.of("0.00").add(Real.of("10000.00")));
		assertEquals(Real.of("600.12"), Real.of("0.12").add(Real.of("600")));
		assertEquals(Real.of("101.00"), Real.of("50.50").add(Real.of("50.50")));
		
		assertEquals(Real.of("123.45"), Real.of("23.40").add(Real.of("100.05")));
		assertEquals(Real.of("-100.69"), Real.of("0.00").add(Real.of("-100.69")));
		
		var reals = Real.of("0");
		for (int i = 0; i < 100; i++) {
			reals = reals.add(Real.of("0.01"));
		}
		assertEquals(Real.of(1,0), reals);
	}
	
	@Test
	void testSub() {
		assertEquals(Real.of(0), Real.of(100).sub(Real.of("1.00")));
		assertEquals(Real.of(-200), Real.of(100).sub(Real.of(300)));
		
		assertEquals(Real.of("123.49"), Real.of("200.00").sub(Real.of("76.51")));
		
		var reals = Real.of("1.00");
		for (int i = 0; i < 100; i++) {
			reals = reals.sub(Real.of("0.01"));
		}
		assertEquals(Real.of(0), reals);
	}
	
	@Test
	void testMontanteJurosCompostos() {
		Function<String, BigDecimal> dec = stringValue -> new BigDecimal(stringValue);

		var C = Real.of("500");
		var i = Real.percentoParaDecimal(dec.apply("0.5"));
		
		assertEquals(Real.of("502.50"), C.montanteJurosCompostos(i, 1));
		assertEquals(Real.of("505.01"), C.montanteJurosCompostos(i, 2));
		assertEquals(Real.of("512.63"), C.montanteJurosCompostos(i, 5));
		assertEquals(Real.of("563.58"), C.montanteJurosCompostos(i, 24));
		
		C = Real.of("1200.69");
		i = Real.percentoParaDecimal(dec.apply("0.59"));
		assertEquals(Real.of("1207.77"), C.montanteJurosCompostos(i, 1));
		assertEquals(Real.of("1214.90"), C.montanteJurosCompostos(i, 2));
		assertEquals(Real.of("1222.07"), C.montanteJurosCompostos(i, 3));
		assertEquals(Real.of("1229.28"), C.montanteJurosCompostos(i, 4));
		assertEquals(Real.of("1236.53"), C.montanteJurosCompostos(i, 5));
		assertEquals(Real.of("1288.51"), C.montanteJurosCompostos(i, 12));		
	}                         
	                          
	@Test                     
	void testPercentoParaDecimal() {
		Function<String, BigDecimal> dec = stringValue -> new BigDecimal(stringValue);
		
		assertEquals(dec.apply("1"), Real.percentoParaDecimal(dec.apply("100")));
		assertEquals(dec.apply("0.5"), Real.percentoParaDecimal(dec.apply("50")));
		assertEquals(dec.apply("0.15"), Real.percentoParaDecimal(dec.apply("15")));
		assertEquals(dec.apply("0.005"), Real.percentoParaDecimal(dec.apply("0.5")));
	}
}
