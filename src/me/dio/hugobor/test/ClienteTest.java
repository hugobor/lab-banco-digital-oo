package me.dio.hugobor.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import me.dio.hugobor.Cliente;

class ClienteTest {

	@Test
	void testNewClient() {
		var client = new Cliente();
		client.setNome("Hugo");
		assertEquals("Hugo", client.getNome());
		
		var client2 = new Cliente("Bilobinho");
		assertEquals("Bilobinho", client2.getNome());	
	}

}
