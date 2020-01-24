package com.matera.digitalbank.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "db_transferencia")
public class Transferencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_lancamento_debito", nullable = false)
	private Lancamento lancamentoDebito;

	@OneToOne
	@JoinColumn(name = "id_lancamento_credito", nullable = false)
	private Lancamento lancamentoCredito;

	public Transferencia() {
	}
	
		
	public Transferencia(Long id, Lancamento lancamentoDebito, Lancamento lancamentoCredito) {
		this.id = id;
		this.lancamentoDebito = lancamentoDebito;
		this.lancamentoCredito = lancamentoCredito;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lancamento getIdLancamentoDebito() {
		return lancamentoDebito;
	}

	public void setIdLancamentoDebito(Lancamento lancamentoDebito) {
		this.lancamentoDebito = lancamentoDebito;
	}

	public Lancamento getIdLancamentoCredito() {
		return lancamentoCredito;
	}

	public void setIdLancamentoCredito(Lancamento lancamentoCredito) {
		this.lancamentoCredito = lancamentoCredito;
	}
	
	
}
