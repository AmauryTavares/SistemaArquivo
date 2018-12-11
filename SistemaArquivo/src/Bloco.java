
public class Bloco {

	private Arquivo arquivo;
	private int inicio;
	private int tamanho;
	private int proximaParteArquivo;
	
	public Bloco(Arquivo arquivo, int inicio, int tamanho, int proximaParteArquivo) {

		this.arquivo = arquivo;
		this.inicio = inicio;
		this.tamanho = tamanho;
		this.proximaParteArquivo = proximaParteArquivo;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getProximaParteArquivo() {
		return proximaParteArquivo;
	}

	public void setProximaParteArquivo(int proximaParteArquivo) {
		this.proximaParteArquivo = proximaParteArquivo;
	}

}
