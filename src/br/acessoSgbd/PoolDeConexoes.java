package br.acessoSgbd;

import java.util.ArrayList;

public class PoolDeConexoes {
	
	private static PoolDeConexoes mySelf;
	private ArrayList<Conexao> conexoes;
	
	private PoolDeConexoes() {
		conexoes = new ArrayList<Conexao>();
	}
	
	public static PoolDeConexoes getInstance() {
		
		if( mySelf == null ) {
			mySelf = new PoolDeConexoes();
		}
		
		return mySelf;
	}
	
	public synchronized Conexao getConexao() throws AcessoSgbdExecption {

		try {
			
			for( Conexao c : conexoes ) {

				if( !c.isOcupado() ) {
					c.reserva();
					return c;
				}
			}
			
			Conexao nova = new Conexao();
			nova.reserva();
			conexoes.add( nova );
			
			return nova;
		} catch (Exception e) {
			throw new AcessoSgbdExecption( e );
		}
	}
	
}

















