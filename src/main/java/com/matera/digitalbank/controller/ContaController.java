package com.matera.digitalbank.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matera.digitalbank.dto.request.LancamentoRequestDTO;
import com.matera.digitalbank.dto.request.TransferenciaRequestDTO;
import com.matera.digitalbank.dto.response.ComprovanteResponseDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.dto.response.ExtratoResponseDTO;
import com.matera.digitalbank.dto.response.ResponseDTO;
import com.matera.digitalbank.enumerator.TipoLancamento;
import com.matera.digitalbank.service.ContaService;

@RestController
@RequestMapping("/api/v1/contas")
public class ContaController extends ControllerBase {

	private final ContaService contaService;

	public ContaController(MessageSource messageSource, ContaService contaService) {
		super(messageSource);
		this.contaService = contaService;
	}

	@GetMapping
	public ResponseEntity<ResponseDTO<List<ContaResponseDTO>>> consultaTodos() {
		List<ContaResponseDTO> listaContas = contaService.consultaTodas();

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(listaContas));
	}

	@GetMapping(value = "/{id}/lancamentos", params = { "!dataInicial", "!dataFinal" })
	public ResponseEntity<ResponseDTO<ExtratoResponseDTO>> consultaLancamentosPorConta(@PathVariable("id") Long id) {
		ExtratoResponseDTO extratoCompleto = contaService.consultaExtratoCompleto(id);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(extratoCompleto));
	}

	@GetMapping(value = "/{id}/lancamentos", params = { "dataInicial", "dataFinal" })
	public ResponseEntity<ResponseDTO<ExtratoResponseDTO>> consultaLancamentosPorContaPorPeriodo(
			@PathVariable("id") Long id,
			@RequestParam(value = "dataInicial", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataInicial,
			@RequestParam(value = "dataFinal", required = true) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataFinal) {
		ExtratoResponseDTO extratoCompleto = contaService.consultaExtratoPorPeriodo(id, dataInicial, dataFinal);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(extratoCompleto));
	}

	@GetMapping("/{idConta}/lancamentos/{idLancamento}")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> consultaLancamentosPorId(
			@PathVariable("idConta") Long idConta, @PathVariable("idLancamento") Long idLancamento) {
		ComprovanteResponseDTO comprovanteLancamento = contaService.consultaComprovanteLancamento(idConta,
				idLancamento);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovanteLancamento));
	}

	@PostMapping("/{idConta}/lancamentos/{idLancamento}/estornar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> estornaLancamentosPorId(
			@PathVariable("idConta") Long idConta, @PathVariable("idLancamento") Long idLancamento) {
		ComprovanteResponseDTO comprovanteEstorno = contaService.estornaLancamento(idConta, idLancamento);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovanteEstorno));
	}

	@DeleteMapping("/{idConta}/lancamentos/{idLancamento}")
	public ResponseEntity<Void> removeEstornoPorId(@PathVariable("idConta") Long idConta,
			@PathVariable("idLancamento") Long idLancamento) {
		contaService.removeLancamentoEstorno(idConta, idLancamento);
		;

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/bloquear")
	public ResponseEntity<Void> bloqueiaConta(@PathVariable("id") Long id) {
		contaService.bloqueiaConta(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/desbloquear")
	public ResponseEntity<Void> desbloqueiaConta(@PathVariable("id") Long id) {
		contaService.desbloqueiaConta(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/depositar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaDeposito(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO,
				TipoLancamento.DEPOSITO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

	@PostMapping("/{id}/sacar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaSaque(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO,
				TipoLancamento.SAQUE);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

	@PostMapping("/{id}/pagar")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaPagamento(@PathVariable("id") Long id,
			@Valid @RequestBody LancamentoRequestDTO lancamentoRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaLancamento(id, lancamentoRequestDTO,
				TipoLancamento.PAGAMENTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

	@PostMapping("/{id}/transferir")
	public ResponseEntity<ResponseDTO<ComprovanteResponseDTO>> efetuaTransferencia(@PathVariable("id") Long id,
			@Valid @RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
		ComprovanteResponseDTO comprovante = contaService.efetuaTransferencia(id, transferenciaRequestDTO);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(comprovante));
	}

}
