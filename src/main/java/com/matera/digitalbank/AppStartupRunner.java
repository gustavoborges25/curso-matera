package com.matera.digitalbank;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.matera.digitalbank.entity.Cliente;
import com.matera.digitalbank.repository.ClienteRepository;

@Component
public class AppStartupRunner implements ApplicationRunner {
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Cliente cliente1 = new Cliente("Gustavo", "07473955555", 123456789L, new BigDecimal("1000.00"),
				"logradouro", 2, "complemento", "bairro", "cidade", "pr", "cep");

		clienteRepository.save(cliente1);

		Cliente cliente2 = clienteRepository.findById(cliente1.getId()).orElse(null);
		System.out.println("2 >>> " + cliente2);
		Cliente cliente3 = clienteRepository.buscaPorCpf("07473955555").orElse(null);
		System.out.println("3 >>> " + cliente3);
		
		clienteRepository.delete(cliente1);
		Cliente cliente4 = clienteRepository.findById(cliente1.getId()).orElse(null);
		System.out.println("4 >>> " + cliente4);

		
		
	}

}
