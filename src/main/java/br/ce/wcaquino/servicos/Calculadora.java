package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		int resultado = a + b;
		return resultado;
	}

	public int subtrarir(int a, int b) {
		int resultado = a - b;
		return resultado;
	}

	public int divide(int a, int b) throws NaoPodeDividirPorZeroException {		
		if(b == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		int resultado = a/b;
		return resultado;
	}
	
}
