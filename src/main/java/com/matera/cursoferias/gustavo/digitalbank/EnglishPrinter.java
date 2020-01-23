package com.matera.cursoferias.gustavo.digitalbank;

import org.springframework.stereotype.Component;

@Component
public class EnglishPrinter implements Printer {

	@Override
	public void print() {
		System.out.println("hello word");
	}

}
