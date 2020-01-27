package com.matera.digitalbank.utils;

import java.math.BigDecimal;

import com.matera.digitalbank.enumerator.Natureza;

public class DigitalBankUtils {

	public static BigDecimal calculaSaldo(Natureza natureza, BigDecimal valor, BigDecimal saldoAtual) {
		BigDecimal saldoFinal;

		if (Natureza.DEBITO.equals(natureza)) {
			saldoFinal = saldoAtual.subtract(valor);
		} else {
			saldoFinal = saldoAtual.add(valor);
		}

		return saldoFinal;
	}

}
