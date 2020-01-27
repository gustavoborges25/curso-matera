package com.matera.digitalbank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matera.digitalbank.dto.request.LancamentoRequestDTO;
import com.matera.digitalbank.dto.request.TransferenciaRequestDTO;
import com.matera.digitalbank.dto.response.ComprovanteResponseDTO;
import com.matera.digitalbank.dto.response.ContaResponseDTO;
import com.matera.digitalbank.dto.response.ExtratoResponseDTO;
import com.matera.digitalbank.entity.Cliente;
import com.matera.digitalbank.entity.Conta;
import com.matera.digitalbank.entity.Lancamento;
import com.matera.digitalbank.enumerator.Natureza;
import com.matera.digitalbank.enumerator.SituacaoConta;
import com.matera.digitalbank.enumerator.TipoLancamento;
import com.matera.digitalbank.exception.ServiceException;
import com.matera.digitalbank.repository.ContaRepository;
import com.matera.digitalbank.utils.DigitalBankUtils;

@Service
public class ContaService {

    @Value("${agencia.numeroMaximo:5}")
    private Integer numeroMaximoAgencia;

    private final ContaRepository contaRepository;
    private final LancamentoService lancamentoService;

    public ContaService(ContaRepository contaRepository, LancamentoService lancamentoService) {
        this.contaRepository = contaRepository;
        this.lancamentoService = lancamentoService;
    }

    @Transactional
    public ContaResponseDTO cadastra(Cliente cliente) {
        valida(cliente);

        Conta conta = Conta.builder().cliente(cliente)
                                     .numeroAgencia(new Random().nextInt(numeroMaximoAgencia) + 1)
                                     .numeroConta(cliente.getTelefone())
                                     .saldo(BigDecimal.ZERO)
                                     .situacao(SituacaoConta.ABERTA.getCodigo())
                                     .build();

        contaRepository.save(conta);

        return entidadeParaResponseDTO(conta);
    }

    @Transactional
	public ComprovanteResponseDTO efetuaLancamento(Long id, LancamentoRequestDTO lancamentoRequestDTO, Natureza natureza, TipoLancamento tipoLancamento) {
		Conta conta = findById(id);

		Lancamento lancamento = insereLancamento(lancamentoRequestDTO, conta, natureza, tipoLancamento);

		return lancamentoService.entidadeParaComprovanteResponseDTO(lancamento);
	}

	@Transactional
	public ComprovanteResponseDTO efetuaTransferencia(Long id, TransferenciaRequestDTO transferenciaRequestDTO) {
		Conta contaDebito = findById(id);

		Conta contaCredito = contaRepository.findByNumeroAgenciaAndNumeroConta(transferenciaRequestDTO.getNumeroAgencia(), transferenciaRequestDTO.getNumeroConta())
		                                    .orElseThrow(() -> new ServiceException("Conta de agência " + transferenciaRequestDTO.getNumeroAgencia() + " e número " + transferenciaRequestDTO.getNumeroConta().toString() + " não encontrada."));

		Lancamento lancamentoDebito = insereLancamento(new LancamentoRequestDTO(transferenciaRequestDTO.getValor(), transferenciaRequestDTO.getDescricao()), contaDebito, Natureza.DEBITO, TipoLancamento.TRANSFERENCIA);
		Lancamento lancamentoCredito = insereLancamento(new LancamentoRequestDTO(transferenciaRequestDTO.getValor(), transferenciaRequestDTO.getDescricao()), contaCredito, Natureza.CREDITO, TipoLancamento.TRANSFERENCIA);

		return lancamentoService.efetuaTransferencia(lancamentoDebito, lancamentoCredito);
	}

	public ExtratoResponseDTO consultaExtratoCompleto(Long id) {
		Conta conta = findById(id);

		List<ComprovanteResponseDTO> comprovantesResponseDTO = lancamentoService.consultaExtratoCompleto(conta);

		ExtratoResponseDTO extratoResponseDTO = new ExtratoResponseDTO();
		extratoResponseDTO.setConta(entidadeParaResponseDTO(conta));
		extratoResponseDTO.setLancamentos(comprovantesResponseDTO);

		return extratoResponseDTO;
	}

	public ExtratoResponseDTO consultaExtratoPorPeriodo(Long id, LocalDate dataInicial, LocalDate dataFinal) {
		Conta conta = findById(id);

		List<ComprovanteResponseDTO> comprovantesResponseDTO = lancamentoService.consultaExtratoPorPeriodo(dataInicial, dataFinal);

		ExtratoResponseDTO extratoResponseDTO = new ExtratoResponseDTO();
		extratoResponseDTO.setConta(entidadeParaResponseDTO(conta));
		extratoResponseDTO.setLancamentos(comprovantesResponseDTO);

		return extratoResponseDTO;
	}

	@Transactional
	public ComprovanteResponseDTO estornaLancamento(Long idConta, Long idLancamento) {
		return lancamentoService.estornaLancamento(idConta, idLancamento);
	}

	public ComprovanteResponseDTO consultaComprovanteLancamento(Long idConta, Long idLancamento) {
		return lancamentoService.consultaComprovanteLancamento(idConta, idLancamento);
	}

    public List<ContaResponseDTO> consultaTodas() {
	    List<Conta> contas = contaRepository.findAll();
	    List<ContaResponseDTO> contasResponseDTO = new ArrayList<>();

	    contas.forEach(conta -> contasResponseDTO.add(entidadeParaResponseDTO(conta)));

        return contasResponseDTO;
    }

	public ContaResponseDTO consultaContaPorIdCliente(Long idCliente) {
	    Conta conta = contaRepository.findByCliente_Id(idCliente)
	    							 .orElseThrow(() -> new ServiceException("Conta não encontrada para o cliente de ID " + idCliente + "."));

	    return entidadeParaResponseDTO(conta);
	}

	@Transactional
	public void bloqueiaConta(Long id) {
	    Conta conta = findById(id);

	    validaBloqueio(conta);

	    conta.setSituacao(SituacaoConta.BLOQUEADA.getCodigo());
	    contaRepository.save(conta);
	}

	@Transactional
	public void desbloqueiaConta(Long id) {
        Conta conta = findById(id);

        validaDesbloqueio(conta);

        conta.setSituacao(SituacaoConta.ABERTA.getCodigo());
        contaRepository.save(conta);
    }

	private Conta findById(Long id) {
		return contaRepository.findById(id)
							  .orElseThrow(() -> new ServiceException("Conta de ID " + id + " não encontrada."));
	}

    private void valida(Cliente cliente) {
        if (contaRepository.findByNumeroConta(cliente.getTelefone()).isPresent()) {
            throw new ServiceException("Já existe uma conta cadastrada com o número de telefone informado (" +
                                       cliente.getTelefone() + ").");
        }
    }

    private Lancamento insereLancamento(LancamentoRequestDTO lancamentoRequestDTO, Conta conta, Natureza natureza, TipoLancamento tipoLancamento) {
	    Lancamento lancamento = lancamentoService.efetuaLancamento(lancamentoRequestDTO, conta, natureza, tipoLancamento);

		atualizaSaldo(conta, lancamento.getValor(), natureza);

        return lancamento;
	}

    private void atualizaSaldo(Conta conta, BigDecimal valorLancamento, Natureza natureza) {
        BigDecimal saldo = DigitalBankUtils.calculaSaldo(natureza, valorLancamento, conta.getSaldo());

		conta.setSaldo(saldo);
		contaRepository.save(conta);
    }

    private void validaBloqueio(Conta conta) {
        if (SituacaoConta.BLOQUEADA.getCodigo().equals(conta.getSituacao())) {
            throw new ServiceException("Conta de ID " + conta.getId() + " já se encontra na situação Bloqueada.");
        }
    }

    private void validaDesbloqueio(Conta conta) {
        if (SituacaoConta.ABERTA.getCodigo().equals(conta.getSituacao())) {
            throw new ServiceException("Conta de ID " + conta.getId() + " já se encontra na situação Aberta.");
        }
    }

    private ContaResponseDTO entidadeParaResponseDTO(Conta conta) {
        return ContaResponseDTO.builder().idCliente(conta.getCliente().getId())
                                         .idConta(conta.getId())
                                         .numeroAgencia(conta.getNumeroAgencia())
                                         .numeroConta(conta.getNumeroConta())
                                         .saldo(conta.getSaldo())
                                         .situacao(conta.getSituacao())
                                         .build();
    }

}
