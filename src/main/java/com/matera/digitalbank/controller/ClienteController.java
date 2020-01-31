package com.matera.digitalbank.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matera.digitalbank.dto.request.ClienteRequestDTO;
import com.matera.digitalbank.dto.response.ClienteResponseDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.dto.response.ResponseDTO;
import com.matera.digitalbank.service.ClienteService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/clientes")
@Slf4j
public class ClienteController extends ControllerBase {

	private final ClienteService clienteService;

	public ClienteController(MessageSource messageSource, ClienteService clienteService) {
		super(messageSource);
		this.clienteService = clienteService;
	}

	@PostMapping
	public ResponseEntity<ResponseDTO<ContaResponseDTO>> cadastra(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
		log.debug("Iniciando POST em /api/v1/clientes com request {}", clienteRequestDTO);
		ContaResponseDTO contaResponseDTO = clienteService.cadastra(clienteRequestDTO);
		log.debug("Finalizando POST em /api/v1/clientes com response {}", contaResponseDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(contaResponseDTO));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> atualiza(@PathVariable("id") Long id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
		log.debug("Iniciando PUT em /api/v1/clientes/{} com request {}", id, clienteRequestDTO);
		clienteService.atualiza(id, clienteRequestDTO);		
		log.debug("Finalizando PUT em /api/v1/clientes/{}");
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<ClienteResponseDTO>> consultaPorId(@PathVariable("id") Long id) {
		log.debug("Iniciando GET em /api/v1/clientes/{}", id);
		ClienteResponseDTO clienteResponseDto = clienteService.consulta(id);
		log.debug("Finalizando GET em /api/v1/clientes/{} com response {}", id, clienteResponseDto);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(clienteResponseDto));
	}

	@GetMapping
	public ResponseEntity<ResponseDTO<List<ClienteResponseDTO>>> consultaTodos() {
		log.debug("Iniciando GET em /api/v1/clientes");
		List<ClienteResponseDTO> listaClientes = clienteService.consultaTodos();
		log.debug("Finalizando GET em /api/v1/clientes com response {}", Arrays.toString(listaClientes.toArray()));

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(listaClientes));
	}

	@GetMapping("/{id}/conta")
	public ResponseEntity<ResponseDTO<ContaResponseDTO>> consultaContaPorId(@PathVariable("id") Long id) {
		log.debug("Iniciando GET em /api/v1/clientes/{}/conta", id);
		ContaResponseDTO contaResponseDto = clienteService.consultaContaPorIdCliente(id);
		log.debug("Finalizando GET em /api/v1/clientes/{}/conta com response {}", id, contaResponseDto);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(contaResponseDto));
	}

}
