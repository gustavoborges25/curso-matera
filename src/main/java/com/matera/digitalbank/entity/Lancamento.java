package com.matera.digitalbank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "db_lancamento")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_conta")
	private Conta conta;

	@Column(length = 50, nullable = false)
	private String codigoAutenticacao;

	@Column(nullable = false)
	private LocalDateTime dataHora;

	@Column(precision = 20, scale = 2, nullable = false)
	private BigDecimal valor;
	
	@Column(length = 1, nullable = false)
	private String natureza;
	
	@Column(length = 50)
	private String descricao;
	
	public Lancamento() {
	}

	public Lancamento(Long id, Conta conta, String codigoAutenticacao, LocalDateTime dataHora, BigDecimal valor,
			String natureza, String descricao) {
		super();
		this.id = id;
		this.conta = conta;
		this.codigoAutenticacao = codigoAutenticacao;
		this.dataHora = dataHora;
		this.valor = valor;
		this.natureza = natureza;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getCodigoAutenticacao() {
		return codigoAutenticacao;
	}

	public void setCodigoAutenticacao(String codigoAutenticacao) {
		this.codigoAutenticacao = codigoAutenticacao;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", codigoAutenticacao=" + codigoAutenticacao
				+ ", dataHora=" + dataHora + ", valor=" + valor + ", natureza=" + natureza + ", descricao=" + descricao
				+ "]";
	}
	
	
	
}
