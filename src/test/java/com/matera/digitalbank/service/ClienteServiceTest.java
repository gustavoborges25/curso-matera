package com.matera.digitalbank.service;

import static com.matera.digitalbank.utils.DigitalBankTestUtils.criaClienteRequestDTO;
import static com.matera.digitalbank.utils.DigitalBankTestUtils.criaContaResponseDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.matera.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.entity.Cliente;
import com.matera.digitalbank.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ContaService contaService;

	@InjectMocks
	private ClienteService clienteService;

	@Test
	public void cadastraClienteComSucesso() {
		ClienteRequestDTO mockClienteRequestDTO = criaClienteRequestDTO();
		ContaResponseDTO mockContaResponseDTO = criaContaResponseDTO();
		
		when(clienteRepository.findByCpf(eq(mockClienteRequestDTO.getCpf()))).thenReturn(Optional.empty());
		when(contaService.cadastra(any(Cliente.class))).thenReturn(mockContaResponseDTO);
		
		ContaResponseDTO contaResponseDTO = clienteService.cadastra(mockClienteRequestDTO);
		
		verify(clienteRepository).findByCpf(eq(mockClienteRequestDTO.getCpf()));
		verify(clienteRepository).save(any(Cliente.class));
		verify(contaService).cadastra(any(Cliente.class));
		verifyNoMoreInteractions(clienteRepository);
		verifyNoMoreInteractions(contaService);

		assertEquals(mockContaResponseDTO, contaResponseDTO);
	}

}
