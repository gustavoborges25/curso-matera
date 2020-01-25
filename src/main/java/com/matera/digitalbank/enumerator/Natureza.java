package com.matera.digitalbank.enumerator;

public enum Natureza {
	
	CREDITO("C"),
	DEBITO("D");
	
	private String codigo;
	
	private Natureza(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return this.codigo;
	}

}
