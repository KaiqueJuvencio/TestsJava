package br.ce.wcaquino.servicos;


import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before //Roda antes de cada teste
	public void setup() {
		System.out.println("Before");
		service = new LocacaoService();
	}
	
	@After //Roda depois de cada teste
	public void tearDown() {
		System.out.println("After");
	}
	
	@BeforeClass //Roda antes de todos os testes
	public static void setupClass() {
		System.out.println("Before Class");		
	}
	
	@AfterClass //Roda depois de todos os testes
	public static void tearDownClass() {
		System.out.println("After Class");
	}
	
	@Test
	public void testeLocacao() throws Exception {
		//Cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Kaique");	
		List<Filme> filmes = new ArrayList<Filme>();
		Filme armagedonFilme = new Filme("Armagedon", 2, 6.0);
		Filme butterflyFilme = new Filme("Butterfly Effect", 5, 12.0);
		Filme avengersFilme = new Filme("Avengers", 7, 8.0);
		filmes.add(armagedonFilme);
		filmes.add(butterflyFilme);
		filmes.add(avengersFilme);
		
		//Acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//Verificacao
		erro.checkThat(locacao.getValor(), is(26.0)); // Faz com que o teste nao pare no primeiro assert errado
		erro.checkThat(locacao.getValor(), not(7.0));
		erro.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		erro.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));				
		
	}
	
	//So vai passar se for retornado uma exception. No caso tem a logica para retornar caso tenha estoque vazio 
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoFilmeSemEstoque() throws Exception {
		//Cenario	
		Usuario usuario = new Usuario("Kaique");		
		List<Filme> filmes = new ArrayList<Filme>();
		Filme armagedonFilme = new Filme("Armagedon", 0, 6.0);
		Filme butterflyFilme = new Filme("Butterfly Effect", 0, 12.0);
		Filme avengersFilme = new Filme("Avengers", 0, 8.0);
		filmes.add(armagedonFilme);
		filmes.add(butterflyFilme);
		filmes.add(avengersFilme);
		
		//Acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testLocacaoUsuarioVazio() {
		//Cenario
		Usuario usuario = new Usuario("Kaique");		
		List<Filme> filmes = new ArrayList<Filme>();
		Filme armagedonFilme = new Filme("Armagedon", 2, 6.0);
		Filme butterflyFilme = new Filme("Butterfly Effect", 5, 12.0);
		Filme avengersFilme = new Filme("Avengers", 7, 8.0);
		filmes.add(armagedonFilme);
		filmes.add(butterflyFilme);
		filmes.add(avengersFilme);
		
		//Acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.assertThat(e.getMessage(),is("Usu�rio vazio"));
			e.printStackTrace();
		}
		System.out.println("Forma robusta");
	}
	
	@Test
	public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		//Cenario
		Usuario usuario = new Usuario("Kaique");	

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		//Acao
		service.alugarFilme(usuario, null);
		
		System.out.println("Forma nova");
	}
}
