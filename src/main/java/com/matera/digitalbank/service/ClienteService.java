package com.matera.digitalbank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matera.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.digitalbank.dto.response.ClienteResponseDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.entity.Cliente;
import com.matera.digitalbank.exception.ServiceException;
import com.matera.digitalbank.repository.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	private final ContaService contaService;
	
	public ClienteService(ClienteRepository clienteRepository, ContaService contaService) {
		this.clienteRepository = clienteRepository;
		this.contaService = contaService;
	}
	
	@Transactional
	public ContaResponseDTO cadastra(ClienteRequestDTO clienteRequestDTO) {
		valida(clienteRequestDTO);
		
		Cliente cliente = requestDTOparaEntidade(clienteRequestDTO, new Cliente());
		clienteRepository.save(cliente);
		
		return contaService.cadastra(cliente);
	}
	
	public ClienteResponseDTO consulta(Long id) {
		Cliente cliente = findById(id);
		return entidadeParaResponseDTO(cliente);
	}

	public List<ClienteResponseDTO> consultaTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteResponseDTO> clientesResponseDTO = new ArrayList<ClienteResponseDTO>();
		
		clientes.forEach(cliente -> clientesResponseDTO.add(entidadeParaResponseDTO(cliente)));
		
		return clientesResponseDTO;
	}
	
	public void atualiza(Long id, ClienteRequestDTO clienteRequestDto) {
		Cliente cliente = findById(id);
		Cliente clienteAtualizado = requestDTOparaEntidade(clienteRequestDto, cliente);
		clienteRepository.save(clienteAtualizado);
	}

	private Cliente findById(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new ServiceException("Cliente não encontrado"));
	}

	private ClienteResponseDTO entidadeParaResponseDTO(Cliente cliente) {
		return ClienteResponseDTO.builder()
				.id(cliente.getId())
				.bairro(cliente.getBairro())
				.cep(cliente.getCep())
				.cidade(cliente.getCidade())
				.complemento(cliente.getComplemento())
				.cpf(cliente.getCpf())
				.estado(cliente.getEstado())
				.logradouro(cliente.getLogradouro())
				.nome(cliente.getNome())
				.numero(cliente.getNumero())
				.rendaMensal(cliente.getRendaMensal())
				.telefone(cliente.getTelefone())
				.build();
	}

	private void valida(ClienteRequestDTO clienteRequestDTO) {
		if (clienteRepository.findByCpf(clienteRequestDTO.getCep()).isPresent()) {
			throw new ServiceException("Já existe um cliente cadastrado com CPF informado (" + clienteRequestDTO.getCpf() + ")");
		}
	}

	private Cliente requestDTOparaEntidade(ClienteRequestDTO clienteRequestDTO, Cliente cliente) {
		cliente.setBairro(clienteRequestDTO.getBairro());
		cliente.setCep(clienteRequestDTO.getCep());
		cliente.setCidade(clienteRequestDTO.getCidade());
		cliente.setComplemento(clienteRequestDTO.getComplemento());
		cliente.setCpf(clienteRequestDTO.getCpf());
		cliente.setEstado(clienteRequestDTO.getEstado());
		cliente.setLogradouro(clienteRequestDTO.getLogradouro());
		cliente.setNome(clienteRequestDTO.getNome());
		cliente.setNumero(clienteRequestDTO.getNumero());
		cliente.setRendaMensal(clienteRequestDTO.getRendaMensal());
		cliente.setTelefone(clienteRequestDTO.getTelefone());

		return cliente;
	}
	
}
