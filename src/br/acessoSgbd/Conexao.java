package br.acessoSgbd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {
	
	private Connection conection;
	private boolean ocupado;
	
	public Conexao() throws IOException, ClassNotFoundException, SQLException {
		ParametrosSQL p = ParametrosSQL.getInstance();
		Class.forName( p.getProperty( "driverJDBC" ) );
		System.out.println( "Driver jdbc importado");
		
		String url = "jdbc:postgresql://" + p.getProperty( "endIpBanco" ) + ":" + p.getProperty( "nroPorta" ) + "/" + p.getProperty( "databaseName" );

		conection = DriverManager.getConnection( url, p.getProperty( "nome" ), p.getProperty( "senha" ) );
		System.out.println( "Conexão com Postgres foi estabelecida" );
	}
	
	public void reserva() {
		setOcupado( true );
	}
	
	public void libera() {
		setOcupado( false );
	}
	
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
	
	public boolean isOcupado() {
		return ocupado;
	}
	
	public Connection getConection() {
		return conection;
	}
	
	public void beginTransaction() {
		
	}
	
	public void commit() {
		
	}
	
	public void rollback() {
		
	}
	
	public void execute( String cmd ) throws SQLException {
		Statement st = conection.createStatement();
		st.execute( cmd );
	}
	
	public ResultSet executeQuery( String cmd ) {
		return null;
	}
	
	public PreparedStatement prepareStatement( String cmd ) throws SQLException {
		return conection.prepareStatement( cmd );
	}
	
	public void close() {
		
	}
	
}