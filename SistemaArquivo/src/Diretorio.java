import java.util.Date;
import java.util.List;

public class Diretorio {

	private String nome;
	private Date dataCriacao;
	private Diretorio diretorioPai;
	private List<Arquivo> arquivos;
	private List<Diretorio> diretorios;
	
	public Diretorio(String nome, Date dataCriacao, Diretorio diretorioPai, List<Arquivo> arquivos,
			List<Diretorio> diretorios) {
		this.nome = nome;
		this.dataCriacao = dataCriacao;
		this.diretorioPai = diretorioPai;
		this.arquivos = arquivos;
		this.diretorios = diretorios;
	}
	
	public Diretorio getDiretorioPai() {
		return diretorioPai;
	}

	public void setDiretorioPai(Diretorio diretorioPai) {
		this.diretorioPai = diretorioPai;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<Diretorio> getDiretorios() {
		return diretorios;
	}

	public void setDiretorios(List<Diretorio> diretorios) {
		this.diretorios = diretorios;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Arquivo> getArquivos() {
		return arquivos;
	}

	public void setArquivos(List<Arquivo> arquivos) {
		this.arquivos = arquivos;
	}

}
