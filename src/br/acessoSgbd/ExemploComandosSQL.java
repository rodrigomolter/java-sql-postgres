package br.acessoSgbd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import br.feevale.Cliente;

public class ExemploComandosSQL {
	
	public static void main(String[] args) throws IOException, AcessoSgbdExecption {
		
		ParametrosSQL p = ParametrosSQL.getInstance();
		
		Conexao cnx = PoolDeConexoes.getInstance().getConexao();
		
		try {
			
			// 1 - Importar o driver JDBC do banco
			try {
				Connection conexao = cnx.getConection(); 
				System.out.println( "Conexão com Postgres foi estabelecida" );
				
				try {
					
//					criaIndiceEmClientes( conexao );
					insereClientes( conexao );
					//alteraCliente( conexao );
					//excluiCliente( conexao );
					//executaSelect( conexao );
					
				} finally {
					conexao.close();
					System.out.println( "Conexao com o banco foi finalizada");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			cnx.libera();
		}
		
	}

	private static void executaSelect(Connection conexao) throws SQLException {

		String cmd = "Select dsNome, idCliente, tpPessoa, tpSexo from clientes;";
		
		Statement st = conexao.createStatement();
		ResultSet rs = st.executeQuery( cmd );
		
		if( rs.next() ) {
			
			while( !rs.isAfterLast() ) {
				
				String nome = rs.getString( 1 ); // acessando pela ordem no select
				Integer codigo = rs.getInt( "idCliente" ); // acessando pelo nome do campo
				String tpPessoa = rs.getString( 3 );
				String tpSexo = rs.getString( "tpSexo" );
				
				System.out.println( codigo + " - " + nome + " : " + tpPessoa + " " + tpSexo );
				
				rs.next();
			}
			
			
		} else {
			System.out.println( "Comando select não retornou registros." );
		}
	}

	private static void excluiCliente(Connection conexao) throws SQLException {
		String comando = "Delete from  Clientes Where idCliente = ?;";	
		
		PreparedStatement ps = conexao.prepareStatement( comando );

		ps.setInt( 1, 3 );
		
		ps.execute();		
		
	}

	private static void alteraCliente(Connection conexao) throws SQLException {
		String comando = "Update Clientes set Nome = ? Where idCliente = ?;";	
		
		PreparedStatement ps = conexao.prepareStatement( comando );

		ps.setString( 1, "José Rico Fonzuelo" );
		ps.setInt( 2, 3 );

		
		ps.execute();		
		
	}
	private static void insereClientes(Connection conexao) throws SQLException {

		String comando = "insert into Clientes ( idCliente, Nome, tpPessoa, tpSexo, dtNascimento ) values ( ?, ?, ?, ?, ? )";

		Calendar c = Calendar.getInstance();
		c.set( 2001, 6, 27 );
		
		PreparedStatement ps = conexao.prepareStatement( comando );
		ps.setInt( 1, 6 );
		ps.setString( 2, "Rodrigo Mattos" );
		ps.setString( 3, "F" );
		ps.setString( 4, "M" );
		ps.setTimestamp( 5, new Timestamp( c.getTimeInMillis() ) );
		
		ps.execute();		
	}

	private static void criaIndiceEmClientes( Connection conexao ) throws SQLException {

		String comando = "create index ClientesPorNome on clientes( dsNome );";
		
		Statement st = conexao.createStatement();
		st.execute( comando );
		
		System.out.println( "indice criado..." );
	}
}





















