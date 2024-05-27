package me.dio.hugobor;

public class Cliente {

	private String nome;
	
	// Propriedades
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }


	// Construtores
	public Cliente() { super(); }
	
	public Cliente(String nome) {
		super();
		this.nome = nome;
	}

	
}
