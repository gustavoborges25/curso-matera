package com.matera.digitalbank;

import org.springframework.stereotype.Component;

@Component
public class PortuguesePrinter implements Printer {

	@Override
	public void print() {
		System.out.println("olá mundo");
	}

}
