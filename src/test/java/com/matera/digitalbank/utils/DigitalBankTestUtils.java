package com.matera.digitalbank.utils;

import java.math.BigDecimal;

import com.matera.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.enumerator.SituacaoConta;

public class DigitalBankTestUtils {

	public static ClienteRequestDTO criaClienteRequestDTO() {
		return ClienteRequestDTO.builder().bairro("bairro").cep("87005220").cidade("maringa").cpf("00000000000")
				.estado("PR").nome("Alfafa").numero(13).complemento("complemento").logradouro("asidlsajndklja")
				.telefone(1513L).rendaMensal(BigDecimal.TEN).build();
	}

	public static ContaResponseDTO criaContaResponseDTO() {
		return ContaResponseDTO.builder().idCliente(1L).idConta(2L).numeroAgencia(1234).numeroConta(102030L)
				.saldo(BigDecimal.ZERO).situacao(SituacaoConta.ABERTA.getCodigo()).build();
	}

}
