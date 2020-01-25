package com.matera.digitalbank.service;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.entity.Cliente;
import com.matera.digitalbank.entity.Conta;
import com.matera.digitalbank.enumerator.SituacaoConta;
import com.matera.digitalbank.exception.ServiceException;
import com.matera.digitalbank.repository.ContaRepository;

@Service
public class ContaService {

	@Value("${agencia.numeroMaximo:5}")
	private Integer numeroMaximoAgencia;

	private final ContaRepository contaRepository;

	public ContaService(ContaRepository contaRepository) {
		this.contaRepository = contaRepository;
	}

	public ContaResponseDTO cadastra(Cliente cliente) {
		valida(cliente);

		Conta conta = Conta.builder().cliente(cliente).numeroAgencia(new Random().nextInt(numeroMaximoAgencia) + 1)
				.numeroConta(cliente.getTelefone()).saldo(BigDecimal.ZERO).situacao(SituacaoConta.ABERTA.getCodigo())
				.build();

		contaRepository.save(conta);

		return entidadeParaResponseDTO(cliente, conta);
	}

	private void valida(Cliente cliente) {
		if (contaRepository.findByNumeroConta(cliente.getTelefone()).isPresent()) {
			throw new ServiceException("Já existe uma conta cadastrada com o numero de telefone informado ("
					+ cliente.getTelefone() + ")");
		}
	}

	private ContaResponseDTO entidadeParaResponseDTO(Cliente cliente, Conta conta) {
		return ContaResponseDTO.builder().idCliente(cliente.getId()).idConta(conta.getId())
				.numeroAgencia(conta.getNumeroAgencia()).numeroConta(conta.getNumeroConta()).saldo(conta.getSaldo())
				.situacao(conta.getSituacao()).build();
	}
}
