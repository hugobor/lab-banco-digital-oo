package me.dio.hugobor;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		Cliente venilton = new Cliente();
		venilton.setNome("Venilton");
		
		IConta cc = ContaCorrente.forClient(venilton);
		IConta poupanca = ContaPoupanca.forClient(venilton, new BigDecimal("0.5"));

		cc.depositar(100.0);
		cc.transferir(100.0, poupanca);
		
		cc.imprimirExtrato();
		poupanca.imprimirExtrato();
	}

}
