package com.matera.digitalbank.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.matera.digitalbank.dto.response.ErroResponseDTO;
import com.matera.digitalbank.dto.response.ResponseDTO;
import com.matera.digitalbank.exception.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ControllerBase {

	private final MessageSource messageSource;

	public ControllerBase(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ResponseDTO<Object>> handleException(ServiceException exception) {
		String mensagemErro = messageSource.getMessage(exception.getCodigoErro(), exception.getParametros(),
				LocaleContextHolder.getLocale());
		ErroResponseDTO erro = new ErroResponseDTO(exception.getCodigoErro() + ": " + mensagemErro);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErro(erro));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO<Object>> handleException(MethodArgumentNotValidException exception) {
		List<ErroResponseDTO> erros = new ArrayList<>();
		BindingResult bindingResult = exception.getBindingResult();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String campo = fieldError.getField();
			String mensagem = String.format("%s: %s", campo, fieldError.getDefaultMessage());
			erros.add(new ErroResponseDTO(campo, mensagem));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.comErros(erros));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO<Object>> handleException(Exception exception) {
		log.error("Erro não esperado ao processar a requisição.", exception);
		String mensagemErro = messageSource.getMessage("DB-99", null, LocaleContextHolder.getLocale());
		ErroResponseDTO erro = new ErroResponseDTO("DB-99: " + mensagemErro);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.comErro(erro));
	}

}
