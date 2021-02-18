package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		//O terceiro parâmetro é a margem de erro
		Assert.assertEquals(0.51, 0.50, 0.1);
		Assert.assertEquals(0.51, 0.51, 00);
		
		//int e Integer
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		//Strings
		Assert.assertEquals("bola", "bola");
		Assert.assertNotEquals("bola", "casa");
		//IgnoreCase faz não ser case sensitive
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");		
		Usuario u3 = null;	
		Assert.assertEquals(u1, u2); //Ele busca o metodo equals na classe Usuario por padrao. Soh da como igual porq implementamos
		
		//Compara se são da mesma instancia
		Assert.assertSame(u2, u2);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertNull(u3);
		Assert.assertNotNull(u2);
	}
}
