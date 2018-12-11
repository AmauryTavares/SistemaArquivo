import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		SistemaArquivo sistemaArquivo = new SistemaArquivo();
		Scanner in = new Scanner(System.in);
		String raiz;
		int tamanhoBloco, tamanhoDisco;
		
		
		System.out.print("Digite o nome do diretorio raiz: ");
		raiz = in.nextLine();
		
		System.out.print("Digite o tamanho do bloco (MB): ");
		tamanhoBloco = in.nextInt();
		
		System.out.print("Digite o tamanho do disco (Blocos): ");
		tamanhoDisco = in.nextInt();
		
		sistemaArquivo.gerenciadorArquivo(raiz, tamanhoBloco, tamanhoDisco);
		
		in.close();
	}

}
