package com.matera.digitalbank.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "db_conta")
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;

	@Column(precision = 4, nullable = false)
	private Integer numeroAgencia;

	@Column(precision = 12, nullable = false)
	private Long numeroConta;

	@Column(precision = 20, scale = 2, nullable = false)
	private BigDecimal saldo;

	@Column(length = 1, nullable = false)
	private String situacao;

	@OneToMany(mappedBy = "conta")
	private List<Lancamento> lancamentos;
		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(Integer numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public Long getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Long numeroConta) {
		this.numeroConta = numeroConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Conta(Long id, Cliente cliente, Integer numeroAgencia, Long numeroConta, BigDecimal saldo, String situacao) {
		this.id = id;
		this.cliente = cliente;
		this.numeroAgencia = numeroAgencia;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
		this.situacao = situacao;
	}

	public Conta() {
	}

	@Override
	public String toString() {
		return "Conta [id=" + id + ", numeroAgencia=" + numeroAgencia + ", numeroConta="
				+ numeroConta + ", saldo=" + saldo + ", situacao=" + situacao + "]";
	}
		
}
