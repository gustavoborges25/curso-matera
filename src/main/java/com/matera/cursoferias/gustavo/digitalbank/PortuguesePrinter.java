package com.matera.cursoferias.gustavo.digitalbank;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class PortuguesePrinter implements Printer {

	@Override
	public void print() {
		System.out.println("olá mundo");
	}

}
