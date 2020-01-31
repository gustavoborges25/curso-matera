package com.matera.digitalbank.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContaResponseDTO {

    private Long idCliente;
    private Long idConta;
    private Integer numeroAgencia;
    private Long numeroConta;
    private String situacao;
    private BigDecimal saldo;

}
