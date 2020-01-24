package com.matera.digitalbank.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "db_estorno")
public class Estorno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_lancamento_original", nullable = false)
	private Lancamento lancamentoOriginal;

	@OneToOne
	@JoinColumn(name = "id_lancamento_estorno", nullable = false)
	private Lancamento lancamentoEstorno;

	public Estorno() {
	}
	
	public Estorno(Long id, Lancamento lancamentoOriginal, Lancamento lancamentoEstorno) {
		this.id = id;
		this.lancamentoOriginal = lancamentoOriginal;
		this.lancamentoEstorno = lancamentoEstorno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lancamento getIdLancamentoOriginal() {
		return lancamentoOriginal;
	}

	public void setIdLancamentoOriginal(Lancamento lancamentoOriginal) {
		this.lancamentoOriginal = lancamentoOriginal;
	}

	public Lancamento getIdLancamentoEstorno() {
		return lancamentoEstorno;
	}

	public void setIdLancamentoEstorno(Lancamento lancamentoEstorno) {
		this.lancamentoEstorno = lancamentoEstorno;
	}


}
