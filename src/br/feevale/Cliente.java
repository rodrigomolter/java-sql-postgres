package br.feevale;

import java.sql.Timestamp;

import br.acessoSgbd.PrimaryKey;
import br.acessoSgbd.Table;

@Table(tableName="Clientes")
public class Cliente {

	@PrimaryKey
	private Integer idCliente;
	private String Nome;
	private String tpPessoa;
	private String tpSexo;
	private Timestamp dtNascimento;
	private Double valorlimcredito;
	private byte[] imagem;

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String Nome) {
		this.Nome = Nome;
	}

	public String getTpPessoa() {
		return tpPessoa;
	}

	public void setTpPessoa(String tpPessoa) {
		this.tpPessoa = tpPessoa;
	}

	public String getTpSexo() {
		return tpSexo;
	}

	public void setTpSexo(String tpSexo) {
		this.tpSexo = tpSexo;
	}

	public Timestamp getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Timestamp dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public Double getValorlimcredito() {
		return valorlimcredito;
	}

	public void setValorlimcredito(Double valorlimcredito) {
		this.valorlimcredito = valorlimcredito;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}

















