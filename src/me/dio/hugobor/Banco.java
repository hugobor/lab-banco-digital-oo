package me.dio.hugobor;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe banco.
 * Só tem um tipo de blanco ainda.
 */
public class Banco {
	
	public static final Banco bancoCentral = new Banco("Banco Central");

	private String nome;
	private List<Conta> contas = new ArrayList<>();

	private Banco(String nome) {
		this.nome = nome;
	}
	
	public String getNome() { return nome; }

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

}
