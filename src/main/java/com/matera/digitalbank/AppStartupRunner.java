package com.matera.digitalbank;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.matera.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.digitalbank.service.ClienteService;

@Component
public class AppStartupRunner implements ApplicationRunner {

	private final ClienteService clienteService;

	public AppStartupRunner(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ClienteRequestDTO cliente = ClienteRequestDTO.builder().bairro("bairro").cep("87005220").cidade("maringa")
				.cpf("00000000000").estado("PR").nome("Alfafa").numero(13).complemento("complemento")
				.logradouro("asidlsajndklja").telefone(1513L).rendaMensal(BigDecimal.TEN).build();

		clienteService.cadastra(cliente);

		ClienteRequestDTO cliente2 = ClienteRequestDTO.builder().bairro("11111").cep("222222").cidade("maringa")
				.cpf("11111111111").estado("PR").nome("Jose").numero(13).logradouro("asidlsajndklja").telefone(5555L)
				.rendaMensal(BigDecimal.TEN).build();

		clienteService.cadastra(cliente2);

		ClienteRequestDTO cliente3 = ClienteRequestDTO.builder().bairro("xxxxxx").cep("87005220").cidade("maringa")
				.cpf("22222222222").estado("PR").nome("Bujica").numero(13).logradouro("asidlsajndklja").telefone(6666L)
				.rendaMensal(BigDecimal.TEN).build();

		clienteService.cadastra(cliente3);
	}

}
