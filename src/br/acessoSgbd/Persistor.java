package br.acessoSgbd;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Persistor {
	
	private Object regTabela;
	
	public Persistor( Object regTabela ) {
		this.regTabela = regTabela;
	}

	public void insere() throws AcessoSgbdExecption {
		
		Field[] campos = regTabela.getClass().getDeclaredFields();
		
		StringBuilder cmd = new StringBuilder();
		cmd.append( "insert into " );
		
		Table tblInfo = regTabela.getClass().getAnnotation( Table.class );
		
		if( tblInfo != null ) {
			cmd.append( tblInfo.tableName() );
		} else {
			cmd.append( regTabela.getClass().getSimpleName() );
		}
		cmd.append( " ( " );
		
		int qtAtributos = 0;
		
		for( Field atributo : campos ) {
			cmd.append( atributo.getName() );
			cmd.append( "," );
			qtAtributos++;
		}

		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " ) values ( " );
		
		for( int i = 0; i < qtAtributos; i++ ) {
			cmd.append( "?," );
		}

		cmd.delete( cmd.length() - 1, cmd.length() );
		cmd.append( " );" );
		
		System.out.println( cmd.toString() );
		
		Conexao cnx = PoolDeConexoes.getInstance().getConexao();
		
		try {
			
			PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
			int nrPrm = 1;
			
			for( Field atributo : campos ) {
				
				String nomeGetter = "get" + Character.toUpperCase( atributo.getName().charAt( 0 ) ) + atributo.getName().substring( 1 );
				System.out.println( nomeGetter );

				try {
					Method mtd = regTabela.getClass().getDeclaredMethod( nomeGetter, null );
					
					Object vl = mtd.invoke( regTabela, null );
					ps.setObject( nrPrm++, vl );
					
				} catch ( Exception e) {
					throw new AcessoSgbdExecption( "Não encontrei o método: " + nomeGetter );
				}
			}
			
			System.out.println( ps.toString() );
			ps.execute();
		} catch (SQLException e) {
			throw new AcessoSgbdExecption( e );
		} finally {
			cnx.libera();
		}
	}
	
	public void altera() {
		
	}
	
	public void exclui() {
		
	}
	
}









