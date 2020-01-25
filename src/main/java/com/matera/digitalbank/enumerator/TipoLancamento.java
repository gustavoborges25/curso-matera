package com.matera.digitalbank.enumerator;

public enum TipoLancamento {

	DEPOSITO("D"),
	SAQUE("S"),
	TRANFERENCIA("T"),
	PAGAMENTO("P"),
	ESTORNO("E");
	
	private String codigo;
	
	private TipoLancamento(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
}
