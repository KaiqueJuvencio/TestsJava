package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoService {
	
	public String vPublic;
	protected String vProtegida;
	private String vPrivada;
	String vDefault;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}
				
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		double valorTotal = 0;
		for (Filme filme : filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
			valorTotal += filme.getPrecoLocacao();			
		}				
		
//		if(filmes.getEstoque() == 0) {
//			throw new FilmeSemEstoqueException();
//		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		return locacao;
	}
}