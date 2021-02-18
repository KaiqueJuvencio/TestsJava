package br.ce.wcaquino.servicos;


import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
//	@Before
	
	
	@Test
	public void testeLocacao() throws Exception {
		//Cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Kaique");		
		Filme filme = new Filme("Armagedon", 2, 6.0); 
		
		//Acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//Verificacao
		erro.checkThat(locacao.getValor(), is(6.0)); // Faz com que o teste nao pare no primeiro assert errado
		erro.checkThat(locacao.getValor(), not(7.0));
		erro.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));				
		
	}
	
	//So vai passar se for retornado uma exception. No caso tem a logica para retornar caso tenha estoque vazio 
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoFilmeSemEstoque() throws Exception {
		//Cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Kaique");		
		Filme filme = new Filme("Armagedon", 0, 6.0); 
		
		//Acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacaoUsuarioVazio() {
		//Cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Kaique");		
		Filme filme = new Filme("Armagedon", 1, 6.0); 
		
		//Acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertThat(e.getMessage(),is("Usuário vazio"));
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//Cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Kaique");	

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//Acao
		service.alugarFilme(usuario, null);
		
	}
}
