package com.matera.digitalbank.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matera.digitalbank.dto.request.LancamentoRequestDTO;
import com.matera.digitalbank.dto.request.TransferenciaRequestDTO;
import com.matera.digitalbank.dto.response.ComprovanteResponseDTO;
import com.matera.digitalbank.dto.response.ResponseDTO;
import com.matera.digitalbank.enumerator.TipoLancamento;
import com.matera.digitalbank.service.ContaService;

@RestController
@RequestMapping("/api/v1/contas")
public class ContaController extends ControllerBase {

	private final ContaService contaService;

	public ContaController(ContaService contaService) {
		this.contaService = contaService;
	}

	@PostMapping("/{id}/depositar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaDeposito(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.DEPOSITO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

	@PostMapping("/{id}/sacar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaSaque(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.SAQUE);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}
	
	@PostMapping("/{id}/pagar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaPagamento(@PathVariable("id") Long id, @Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO, TipoLancamento.PAGAMENTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

	@PostMapping("/{id}/transferir")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaTransferencia(@PathVariable("id") Long id, @Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaTransferencia(id, transferenciaRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}
	
}
