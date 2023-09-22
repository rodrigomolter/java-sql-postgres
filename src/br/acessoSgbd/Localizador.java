package br.acessoSgbd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Localizador<T extends Object> {
	
	private Class<?> classeBase;
	
	public Localizador( Class<?> classeBase ) {
		this.classeBase = classeBase;
	}
	
	public T findByPK( Object... key ) throws AcessoSgbdExecption {
		
		Object x = key[ 0 ];
		System.out.println( key.length );
		
		StringBuilder cmd = new StringBuilder();
		StringBuilder pks = new StringBuilder();
		
		cmd.append( "Select " );
		Field[] campos = classeBase.getDeclaredFields();
		
		for( Field campo : campos ) {
			cmd.append( campo.getName() );
			cmd.append( ", " );
			
			if( campo.isAnnotationPresent( PrimaryKey.class ) ) {
				pks.append( campo.getName() );
				pks.append( " = ? and " );
			}
		}
		
		cmd.delete( cmd.length() - 2, cmd.length() );
		if (pks.length() > 5)
		pks.delete( pks.length() - 5, pks.length() );
		
		cmd.append( " from " );
		Table tbl = classeBase.getAnnotation( Table.class );
		
		if( tbl != null ) {
			cmd.append( tbl.tableName() );
		} else {
			cmd.append( classeBase.getSimpleName() );
		}

		cmd.append( " where " );
		cmd.append( pks );
		cmd.append( ";" );
		
		Conexao cnx = PoolDeConexoes.getInstance().getConexao();
		
		try {
			try {
				PreparedStatement ps = cnx.prepareStatement( cmd.toString() );
				
				int i = 1;
				for( Object k : key ) {
					ps.setObject( i++, k );
				}
				
				ResultSet rs = ps.executeQuery();
				
				if( rs.next() ) {
					
					Constructor<?> construtor = classeBase.getConstructor( null );
					Object result = construtor.newInstance( null );
					
					preencheRegistro( result, rs, campos );
					
					return (T) result;
				} else {
					return null;
				}
			} catch ( Exception e ) {
				throw new AcessoSgbdExecption(e);
			}
		} finally {
			cnx.libera();
		}
	}

	private void preencheRegistro(Object result, ResultSet rs, Field[] campos) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {

		int i = 1;
		
		for( Field campo : campos ) {
			
			String setter = "set" + 
			   Character.toUpperCase( campo.getName().charAt( 0 ) ) +
			   campo.getName().substring( 1 );

			Method m = classeBase.getDeclaredMethod( setter, campo.getType() ); 
			m.invoke( result, rs.getObject( i++ ) );
		}
	}
//	public List<T> find(String clausulaWhere) {
//	return find(clausulaWhere, null);
//}
//
//public List<T> find(String clausulaWhere, Object... parametros) {
//	return find(clausulaWhere, null, parametros);
//}
//
//public List<T> find(String clausulaWhere, String orderBy, Object... parametros) {
//	return find(clausulaWhere, orderBy, null, parametros);
//}
//
//public List<T> find(String clausulaWhere, String orderBy, Integer limit, Object... parametros) {
//	return find(clausulaWhere, orderBy, limit, null, parametros);
//}

public List<T> find(String clausulaWhere, String orderBy, Integer limit, Integer offset, Object... parametros) {		
	Field[] campos = classeBase.getDeclaredFields();
	StringBuilder cmd = new StringBuilder();		
	List<Object> lista = new ArrayList<Object>();		
	cmd.append("SELECT * FROM " + classeBase.getAnnotation(Table.class).tableName());
	cmd.append(" WHERE " + clausulaWhere + " ORDER BY " + orderBy + " LIMIT " + limit + " OFFSET " + offset);				
	try {			
		Conexao cnx = PoolDeConexoes.getInstance().getConexao();		
		PreparedStatement ps = cnx.prepareStatement(cmd.toString());		
		ResultSet rs = ps.executeQuery();		
		
		while (rs.next()) {			    
			Constructor<?> construtor = classeBase.getConstructor( null );
			Object result = construtor.newInstance( null );				
			preencheRegistro( result, rs, campos );
			lista.add(result);
		}			

	} catch (Exception e) {}		
	return (List<T>) lista;
}	
}
