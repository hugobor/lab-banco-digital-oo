package me.dio.hugobor;

/**
 * Represente um cliente do banco.
 * O mesmo cliente pode ter mais de uma conta.
 */
public class Cliente {

	private String nome;
	
	// Propriedades
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }


	// Construtores
	public Cliente() { super(); }
	
	private Cliente(String nome) {
		super();
		this.nome = nome;
	}
	
	public static Cliente of(String nome) {
		return new Cliente(nome);
	}

	
	@Override
	public String toString() {
		return String.format("Cliente \"%s\"", nome);
	}
}
