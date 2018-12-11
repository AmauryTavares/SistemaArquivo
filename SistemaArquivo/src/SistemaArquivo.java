import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SistemaArquivo {
	
	private String diretorioAbsoluto = "";
	private Diretorio diretorioCorrente;
	private int qtBlocosLivres;
	private HashMap<Arquivo,Integer> listaArquivos = new HashMap<>();
	private Bloco disco[];
	
	public void gerenciadorArquivo(String raiz, int tamanhoBloco, int tamanhoDisco) {
		
		qtBlocosLivres = tamanhoDisco;
		disco = new Bloco[tamanhoDisco];
		Scanner in = new Scanner(System.in);
		diretorioAbsoluto += raiz + ":\\";
		String comando = "";

		diretorioCorrente = new Diretorio(raiz, new Date(), null, new ArrayList<>(), new ArrayList<>());
		
		do {
			String comando2 = ""; // nome arquivo ou diretorio
			
			System.out.print("\n" + diretorioAbsoluto);
			
			String linhaComando = in.nextLine();
			comando = (linhaComando.split(" "))[0];
			
			if ((linhaComando.split(" ")).length > 1) {
				comando2 = (linhaComando.split(" "))[1];
			}
			
			switch(comando.split(" ")[0]) {
				case "cd":
						if (comando2 != null && comando2 != "") {
							trocarDiretorio(comando2);
						} else {
							System.out.println("Nome do arquivo nao especificado, tente novamente!");
						}
					break;
					
				case "create":
						if (comando2 != null && comando2 != "") {
							criarArquivo(comando2, tamanhoBloco);
						} else {
							System.out.println("Nome do arquivo nao especificado, tente novamente!");
						}
						break;
						
				case "delete":
						if (comando2 != null && comando2 != "") {							
							deletarArquivo(diretorioCorrente, comando2, tamanhoBloco);
						} else {
							System.out.println("Nome do arquivo nao especificado, tente novamente!");
						}
						break;
					
				case "createdir":
						if (comando2 != null && comando2 != "") {							
							criarDiretorio(comando2);
						} else {
							System.out.println("Nome do diretorio nao especificado, tente novamente!");
						}
					break;
					
				case "deletedir":
						if (comando2 != null && comando2 != "") {							
							deletarDiretorio(comando2, tamanhoBloco, diretorioCorrente);
						} else {
							System.out.println("Nome do diretorio nao especificado, tente novamente!");
						}
					break;
					
				case "dir":
						listarDiretorio();
					break;
					
				default:
					System.out.println("Comando invalido, tente novamente!");
					break;
					
			}
		} while(!(comando.equals("exit")));
		
		in.close();
		
	}
	
	private void criarArquivo(String nomeArquivo, int tamanhoBloco) {
		Random rand = new Random();
		int tamanho = rand.nextInt(50) + tamanhoBloco;
		
		Arquivo novoArquivo = new Arquivo(nomeArquivo, new Date(), tamanho);
		
		boolean arquivoDuplicado = false;

		for (Arquivo arquivo : diretorioCorrente.getArquivos()) {
			if (arquivo.getNome().equals(nomeArquivo)) {
				System.out.println("Arquivo com nome duplicado!");
				arquivoDuplicado = true;
				break;
			}
		}
		
		if (!arquivoDuplicado) {	// cria arquivo apenas se nao for arquivo duplicado
			
			infoArquivo(novoArquivo);
			
			int qtBlocosNecessarios = tamanho / tamanhoBloco;
			
			if (tamanho % tamanhoBloco > 0) {	// parte com fragmentacao interna
				qtBlocosNecessarios++;
			}
			
			if (qtBlocosLivres >= qtBlocosNecessarios) {
				diretorioCorrente.getArquivos().add(novoArquivo);	// adiciona arquivo no diretorio									
			
				int posicaoLivre = 0;

				int ultimoBlocoAlocado = -1;
				for (int i = 0; i < qtBlocosNecessarios; i++) {
					for (int j = posicaoLivre; j < disco.length; j++) {
						if (disco[j] == null) {
							if (i != qtBlocosNecessarios - 1) { // nao eh o ultimo
								disco[j] = new Bloco(novoArquivo, i * tamanhoBloco, tamanhoBloco, 0);
							} else if (tamanho % tamanhoBloco == 0) {
								disco[j] = new Bloco(novoArquivo, i * tamanhoBloco, tamanhoBloco, -1);
							} else {
								disco[j] = new Bloco(novoArquivo, i * tamanhoBloco, tamanho % tamanhoBloco, -1);
							}
							
							if (ultimoBlocoAlocado != -1) {	//adiona a referencia ao bloco alocado anterior
								disco[ultimoBlocoAlocado].setProximaParteArquivo(j);
							}
							
							posicaoLivre = j;
							
							if (i == 0) {	// adiciona apenas no primeiro bloco
								listaArquivos.put(novoArquivo, posicaoLivre);
							}
							
							ultimoBlocoAlocado = j;
							posicaoLivre++;
							
							
							break;
						}
					}
				}
				
				qtBlocosLivres -= qtBlocosNecessarios;
				
				infoDisco(tamanhoBloco);			
		
			} else {
				System.out.println("### Não ha espaco livre no disco! ###");
			}
		}
		
	}
	
	private void deletarArquivo(Diretorio diretorio, String nomeArquivo, int tamanhoBloco) {
		
		boolean removido = false;
		
		for (Arquivo arquivo : diretorio.getArquivos()) {
			if (arquivo.getNome().equals(nomeArquivo)) {
				diretorio.getArquivos().remove(arquivo);
				
				int posicao = listaArquivos.get(arquivo);
				
				while (posicao != -1) {
					int proximaPosicao = disco[posicao].getProximaParteArquivo();
					disco[posicao] = null;
					posicao = proximaPosicao;
				}
				
				int qtBlocosNecessarios = arquivo.getTamanho() / tamanhoBloco;
				
				if (arquivo.getTamanho() % tamanhoBloco > 0) {	// parte com fragmentacao interna
					qtBlocosNecessarios++;
				}
				
				qtBlocosLivres += qtBlocosNecessarios;

				infoDisco(tamanhoBloco);
				removido = true;
				
				System.out.println("Arquivo " + arquivo.getNome() + " foi removido!");
				
				break;
			}
		}

		if (!removido) {
			System.out.println("Arquivo nao existente, tente novamente!");
		}
		
	}
	
	private void criarDiretorio(String nomeDir) {
		boolean diretorioDuplicado = false;

		for (Diretorio diretorio : diretorioCorrente.getDiretorios()) {
			if (diretorio.getNome().equals(nomeDir)) {
				System.out.println("Diretorio com nome duplicado!");
				diretorioDuplicado = true;
				break;
			}
		}
		
		if (!diretorioDuplicado) {
			diretorioCorrente.getDiretorios().add(new Diretorio(nomeDir, new Date(), diretorioCorrente, new ArrayList<>(), new ArrayList<>()));
			
			System.out.println("Diretorio " + nomeDir + " foi criado!");
		}
		
	}
	
	private void trocarDiretorio(String nomeDir) {
		if (nomeDir.equals("..")) {
			if (diretorioCorrente.getDiretorioPai() != null) {
				String caminho[] = diretorioAbsoluto.split("\\\\");
				
				diretorioAbsoluto = "";

				for (int i = 0; i < caminho.length - 1; i++) {
					diretorioAbsoluto += caminho[i] + "\\";
				}
				
				diretorioCorrente = diretorioCorrente.getDiretorioPai();
			}
			
		} else {
			
			boolean encontrado = false;
			
			for (Diretorio diretorio : diretorioCorrente.getDiretorios()) {
				if (diretorio.getNome().equals(nomeDir)) {
					diretorioCorrente = diretorio;
					diretorioAbsoluto += nomeDir + "\\";
					
					encontrado = true;
					break;
				}
			}
			
			if (!encontrado) {
				System.out.println("Diretorio " + nomeDir + " nao existe, tente novamente!");
			}
		}
		
	}
	
private void deletarDiretorio(String nomeDir, int tamanhoBloco, Diretorio diretorioCorrente) {
		
		boolean removido = false;
		
		for (Diretorio diretorio : diretorioCorrente.getDiretorios()) {
			if (diretorio.getNome().equals(nomeDir)) {
				while (diretorio.getDiretorios().size() != 0) {
					deletarDiretorio(diretorio.getDiretorios().get(0).getNome(), tamanhoBloco, diretorio);
				}
				
				while (diretorio.getArquivos().size() != 0) {
					Arquivo arquivo = diretorio.getArquivos().get(0);
					deletarArquivo(diretorio, arquivo.getNome(), tamanhoBloco);
				}
					
				diretorioCorrente.getDiretorios().remove(diretorio);
				
				System.out.println("Diretorio " + nomeDir + " foi removido!");
				removido = true;
				
				break;
			}
		}

		if (!removido) {
			System.out.println("Diretorio nao existente, tente novamente!");
		}
		
	}

	@SuppressWarnings("deprecation")
	private void listarDiretorio() {
		for (Diretorio diretorio : diretorioCorrente.getDiretorios()) {
			System.out.println(diretorio.getDataCriacao().getDate() + "/" + (diretorio.getDataCriacao().getMonth() + 1) + 
					"/" + (diretorio.getDataCriacao().getYear() + 1900) + "	" + diretorio.getDataCriacao().getHours() + 
					":" + diretorio.getDataCriacao().getMinutes() + ":" + diretorio.getDataCriacao().getSeconds() + "	<DIR>		" + diretorio.getNome());
		}
		
		for (Arquivo arquivo : diretorioCorrente.getArquivos()) {
			System.out.println(arquivo.getDataCriacao().getDate() + "/" + (arquivo.getDataCriacao().getMonth() + 1) + 
					"/" + (arquivo.getDataCriacao().getYear() + 1900) + "	" + arquivo.getDataCriacao().getHours() + 
					":" + arquivo.getDataCriacao().getMinutes() + ":" + arquivo.getDataCriacao().getSeconds() + "		" + arquivo.getTamanho() + "(MB)	" + arquivo.getNome());
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void infoArquivo(Arquivo novoArquivo) {
		System.out.println("\n######## Informacao Arquivo ###########");
		System.out.println("Nome: " + novoArquivo.getNome());
		System.out.println("Data de criacao: " + novoArquivo.getDataCriacao().getDate() + "/" + (novoArquivo.getDataCriacao().getMonth() + 1) + 
				"/" + (novoArquivo.getDataCriacao().getYear() + 1900) + " " + novoArquivo.getDataCriacao().getHours() + 
				":" + novoArquivo.getDataCriacao().getMinutes() + ":" + novoArquivo.getDataCriacao().getSeconds());
		System.out.println("Tamanho: " + novoArquivo.getTamanho() + "(MB)");
		System.out.println("#######################################\n");
	}

	private void infoDisco(int tamanhoBloco) {
		System.out.println("\n######## Informacao Disco ###########");
		
		for (int i = 0; i < disco.length; i++) {
			if (disco[i] != null) {
				System.out.println("ID bloco: " + i + "		Nome arquivo: " + disco[i].getArquivo().getNome() + "	Posicao no arquivo: " + disco[i].getInicio() + "(MB)	Tamanho do bloco: " +
			disco[i].getTamanho() + "(MB)	Proximo bloco: " + disco[i].getProximaParteArquivo());
			}
		}
		
		System.out.println("\n## Quantidade de blocos livres: " + qtBlocosLivres + "(" + qtBlocosLivres * tamanhoBloco + " MB) ##");
		
		System.out.println("#######################################\n");
	}
	
}
