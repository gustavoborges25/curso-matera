package com.matera.digitalbank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "db_lancamento")
public class Lancamento extends EntidadeBase {
	
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
	
}
