package br.acessoSgbd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ParametrosSQL {
	
	private static ParametrosSQL mySelf;
	private Properties parametros;
	
	public static ParametrosSQL getInstance() throws IOException {
		
		if( mySelf == null ) {
			mySelf = new ParametrosSQL();
		}
		
		return mySelf;
	}
	
	private ParametrosSQL() throws IOException {
		
		File arqParametros = new File( "ParametrosSQL.xml" );
		
		if( arqParametros.exists() ) {
			
			FileInputStream fis = new FileInputStream( arqParametros );
			parametros = new Properties();
			
			try {
				parametros.loadFromXML( fis );
			} finally {
				fis.close();
			}
		}
	}
	
	public void inicializaParametros() throws IOException {
		
		parametros = new Properties();
		parametros.setProperty("driverJDBC", "org.postgresql.Driver");
		parametros.setProperty("nome", "postgres");
		parametros.setProperty("senha", "1234");
		parametros.setProperty("endIpBanco", "localhost");
		parametros.setProperty("nroPorta", "5432");
		parametros.setProperty("databaseName", "pgIII");
		
		FileOutputStream fos = new FileOutputStream( "ParametrosSQL.xml" );

		try {
			parametros.storeToXML( fos, "Arquivo de Parâmetros para a disciplina de Programação III" );
		} finally {
			fos.close();
		}
	}
	
	public String getProperty( String propKey ) {
		return parametros.getProperty( propKey );
	}
	
}