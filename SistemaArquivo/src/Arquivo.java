import java.util.Date;

public class Arquivo {

	private String nome;
	private Date dataCriacao;
	private int tamanho;

	public Arquivo(String nome, Date dataCriacao, int tamanho) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.tamanho = tamanho;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

}
