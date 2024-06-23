package me.dio.hugobor.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import me.dio.hugobor.Cliente;
import me.dio.hugobor.ContaCorrente;
import me.dio.hugobor.ContaPoupanca;
import me.dio.hugobor.IConta;
import me.dio.hugobor.Lancamento;
import me.dio.hugobor.LimiteException;
import me.dio.hugobor.Real;

class ContasTest {
	
	@Test
	void testContasExtratos() {
		
		// Feito a partir da função main original
		
		Cliente venilton = new Cliente();
		venilton.setNome("Venilton");
		
		IConta cc = ContaCorrente.forClient(venilton);
		IConta poupanca = ContaPoupanca.forClient(venilton, new BigDecimal("0.5"));
		
		assertEquals("Venilton", venilton.getNome());
		assertEquals(Real.of("0.00"), cc.saldo());
		assertEquals(Real.of("0.00"), poupanca.saldo());
		
		cc.depositar(Real.of("100.00"));
		assertEquals(Real.of("100.00"), cc.saldo());
		cc.depositar(Real.of("5.25"));
		assertEquals(Real.of("105.25"), cc.saldo());
		
		try {
			cc.transferir(Real.of("50.00"), poupanca);
		} catch (LimiteException ignore) {
		}
		
		assertEquals(Real.of("55.25"), cc.saldo());
		assertEquals(Real.of("50.00"), poupanca.saldo());

		
		String extratoCC = cc.extrato();
		String extratoPoupanca = poupanca.extrato();
		
		assertTrue(extratoCC.contains("Conta Corrente"));
		assertTrue(extratoPoupanca.contains("Conta Poupança"));
		assertTrue(extratoCC.contains("Venilton"));
		assertTrue(extratoPoupanca.contains("Venilton"));
		assertTrue(extratoCC.contains("Saldo: R$ 55,25"));
		assertTrue(extratoPoupanca.contains("Saldo: R$ 50,00"));
		assertTrue(extratoPoupanca.contains("Rendimento Mensal: 0,5 %"));
	}
	
	@Test
	void testLimiteException() {
		Cliente hugo = Cliente.of("Hugo");
		IConta cc = ContaCorrente.forClient(hugo);
		IConta poup = ContaPoupanca.forClient(hugo);
		
		cc.depositar("100.00");
		assertDoesNotThrow(() -> cc.sacar("50.00"));
		assertEquals(Real.of("50.00"), cc.saldo());
		
		assertThrows(LimiteException.class, () -> cc.sacar("50.01"));
		assertEquals(Real.of("50.00"), cc.saldo());
		
		assertThrows(LimiteException.class, () -> cc.sacar("100.00"));
		assertEquals(Real.of("50.00"), cc.saldo());
		
		assertThrows(LimiteException.class, () -> cc.sacar("1000000.00"));
		assertEquals(Real.of("50.00"), cc.saldo());
		
		assertDoesNotThrow(() -> cc.sacar("50.00"));
		assertEquals(Real.of("0.00"), cc.saldo());
		
		cc.depositar("1000.00");
		assertDoesNotThrow(() -> cc.transferir("600.00", poup));
		assertEquals(Real.of("400.00"), cc.saldo());
		assertEquals(Real.of("600.00"), poup.saldo());
		
		assertThrows(LimiteException.class, () -> cc.transferir("400.01", poup));
		assertEquals(Real.of("400.00"), cc.saldo());
		assertEquals(Real.of("600.00"), poup.saldo());
		
		assertThrows(LimiteException.class, () -> cc.transferir("1000.00", poup));
		assertEquals(Real.of("400.00"), cc.saldo());
		assertEquals(Real.of("600.00"), poup.saldo());
		
		assertDoesNotThrow(() -> cc.transferir("400.00", poup));
		assertEquals(Real.of("0.00"), cc.saldo());
		assertEquals(Real.of("1000.00"), poup.saldo());		
	}
	
	
	@Test
	void testPoupanca() {
		IConta blabu = (ContaPoupanca.forNewClient("Blabu", new BigDecimal("0.5")));
		ContaPoupanca blabuPoupanca = (ContaPoupanca)blabu;
		
		assertEquals(new BigDecimal("0.5"), blabuPoupanca.getRendimentoMes());
		assertEquals("0,5 %", blabuPoupanca.rendimentoMesToString());
		
		blabu.depositar(Real.of("500.00"));
		assertEquals(Real.of("2.50"), blabuPoupanca.calculaRendimentoMes());

		// M = C·(1 + i)^t 
		assertEquals(Real.of("502.50"), blabuPoupanca.montanteRendimentoMeses(1));
		assertEquals(Real.of("505.01"), blabuPoupanca.montanteRendimentoMeses(2));
		assertEquals(Real.of("507.54"), blabuPoupanca.montanteRendimentoMeses(3));
		assertEquals(Real.of("510.08"), blabuPoupanca.montanteRendimentoMeses(4));
		assertEquals(Real.of("512.63"), blabuPoupanca.montanteRendimentoMeses(5));
		assertEquals(Real.of("530.84"), blabuPoupanca.montanteRendimentoMeses(12));
		assertEquals(Real.of("563.58"), blabuPoupanca.montanteRendimentoMeses(24));

		try {
			blabu.sacar(Real.of("500.00"));
		} catch (LimiteException ingnore) {
		}
		
		blabu.depositar(Real.of("1200.69"));
		blabuPoupanca.setRendimentoMes(new BigDecimal("0.59"));
		assertEquals("0,59 %", blabuPoupanca.rendimentoMesToString());
		
		assertEquals(Real.of("7.08"), blabuPoupanca.calculaRendimentoMes());
		assertEquals(Real.of("1207.77"), blabuPoupanca.montanteRendimentoMeses(1));
		assertEquals(Real.of("1214.90"), blabuPoupanca.montanteRendimentoMeses(2));
		assertEquals(Real.of("1222.07"), blabuPoupanca.montanteRendimentoMeses(3));
		assertEquals(Real.of("1229.28"), blabuPoupanca.montanteRendimentoMeses(4));
		assertEquals(Real.of("1236.53"), blabuPoupanca.montanteRendimentoMeses(5));
		assertEquals(Real.of("1288.51"), blabuPoupanca.montanteRendimentoMeses(12));
		assertEquals(Real.of("1382.76"), blabuPoupanca.montanteRendimentoMeses(24));
		assertEquals(Real.of("1592.43"), blabuPoupanca.montanteRendimentoMeses(48));
		assertEquals(Real.of("2432.25"), blabuPoupanca.montanteRendimentoMeses(12*10));
	}

	@Test
	void lancamentosTest() {
		IConta hugo = ContaCorrente.forNewClient("Hugo");
		IConta garfield = ContaCorrente.forNewClient("Garfield");
		IConta snoopy = ContaPoupanca.forNewClient("Snoopy");
		
		hugo.depositar("5000.00");
		assertDoesNotThrow(() -> hugo.transferir("500.00", garfield));
		assertDoesNotThrow(() -> hugo.transferir("1000.00", snoopy));
		assertDoesNotThrow(() -> hugo.transferir("250.00", snoopy));
		
		assertEquals(4, hugo.lancamentos().size());
		assertEquals(1, garfield.lancamentos().size());
		assertEquals(2, snoopy.lancamentos().size());
		
		Function<Lancamento, Integer> extractLancamento = lancamento -> lancamento.getQuantidade().getValue().intValue();
		assertEquals(List.of(5000, 500, 1000, 250), hugo.lancamentos().stream().map(extractLancamento).toList());
		assertEquals(List.of(500), garfield.lancamentos().stream().map(extractLancamento).toList());
		assertEquals(List.of(1000, 250), snoopy.lancamentos().stream().map(extractLancamento).toList());
	}
	
}
