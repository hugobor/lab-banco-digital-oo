package me.dio.hugobor;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		
		Cliente venilton = new Cliente();
		venilton.setNome("Venilton");
		
		IConta cc = ContaCorrente.forClient(venilton);
		IConta poupanca = ContaPoupanca.forClient(venilton, new BigDecimal("0.5"));

		cc.depositar(100.0);
		try {
			cc.transferir(100.0, poupanca);
		} catch (LimiteException exc) {
			System.err.println(exc.getMessage());
		}
		
		cc.imprimirExtrato();
		poupanca.imprimirExtrato();
		
		cc.depositar(Real.of("55.00"));
		try {
			cc.sacar(Real.of("100.01"));
		} catch (LimiteException exc) {
			System.err.println(exc.getMessage());
		}
		
		try {
			cc.transferir(Real.of("55.01"), poupanca);
		} catch (LimiteException exc) {
			System.err.println(exc.getMessage());
		}
		
		cc.imprimirExtrato();
		
		
		IConta hugo = ContaCorrente.forNewClient("Hugo");
		IConta garfield = ContaCorrente.forNewClient("Garfield");
		IConta snoopy = ContaPoupanca.forNewClient("Snoopy");
		
		hugo.depositar("5000.00");
		try {
			hugo.transferir("500.00", garfield);
			hugo.transferir("1000.00", snoopy);
			hugo.transferir("250.00", snoopy);
		} catch (LimiteException exc) {
			System.err.println(exc.getMessage());
		}
		
		hugo.imprimirExtrato();
		garfield.imprimirExtrato();
		snoopy.imprimirExtrato();
	}

}
