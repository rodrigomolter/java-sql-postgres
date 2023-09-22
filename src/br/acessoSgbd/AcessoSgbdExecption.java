package br.acessoSgbd;

public class AcessoSgbdExecption extends Exception {

	private static final long serialVersionUID = 4164467655264696921L;

	public AcessoSgbdExecption( String msg ) {
		super( msg );
	}
	
	public AcessoSgbdExecption( Exception e ) {
		super( e );
	}
	
}