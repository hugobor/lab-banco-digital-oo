package me.dio.hugobor;

import java.time.LocalDateTime;

abstract public class Lancamento {
	
	protected final LocalDateTime timestamp;
	protected final IConta de;
	protected final IConta para;
	protected final Real quantidade;
	
	// Propriedades
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public IConta getDe() {	return de; }
	public IConta getPara() { return para; }
	public Real getQuantidade() { return quantidade; }
	
	
	protected Lancamento(IConta de, IConta para, Real quantidade) {
		super();
		timestamp = LocalDateTime.now();
		this.de = de;
		this.para = para;
		this.quantidade = quantidade;
	}
	
	
	public static class Deposito extends Lancamento {
		protected Deposito(IConta para, Real quantidade) {
			super(null, para, quantidade);
		}
		
		@Override
		public String toString() {
			return String.format("Depósito de %s às %s", quantidade.toString(), timestamp.toString());
		}
	}
	
	public static class Saque extends Lancamento {
		protected Saque(IConta de, Real quantidade) {
			super(de, null, quantidade);
		}
		
		@Override
		public String toString() {
			return String.format("Saque de %s às %s", quantidade.toString(), timestamp.toString());
		}
	}
	
	public static class Transferencia extends Lancamento {
		protected Transferencia(IConta de, IConta para, Real quantidade) {
			super(de, para, quantidade);
		}
		
		@Override
		public String toString() {
			return String.format("Transferência de %s de %s para %s às %s",
					quantidade.toString(), de.toString(), para.toString(), timestamp.toString());
		}
	}	
}
