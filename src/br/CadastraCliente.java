package br;

import java.util.List;

import br.acessoSgbd.AcessoSgbdExecption;
import br.acessoSgbd.Localizador;
import br.feevale.Cliente;

public class CadastraCliente {

	public static void main(String[] args) throws AcessoSgbdExecption {

		Localizador<Cliente> localiza = new Localizador<Cliente>(Cliente.class);		
		List<Cliente> c = localiza.find("TpPessoa = 'F'", "Nome", 6, 2, 1);
		int i = 0;
		if (c != null && c.size() > 0) {
			System.out.println("\nNúmero de registros retornados: " + c.size());
			for (Cliente cliente : c) {
				System.out.println(++i + ") NOME: " + cliente.getNome() + " / ID: " + cliente.getIdCliente());
			}
		}
	}
}
