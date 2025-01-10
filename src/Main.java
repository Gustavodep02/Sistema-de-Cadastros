import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Arquivos arq = new Arquivos();
        Scanner sc = new Scanner(System.in);
        arq.criaArquivoForm();
        int opt=0;
        while(opt !=9) {
            System.out.println("1 - Cadastrar o usuário");
            System.out.println("2 - Listar todos usuários cadastrados");
            System.out.println("3 - Cadastrar nova pergunta no formulário");
            System.out.println("4 - Deletar pergunta do formulário");
            System.out.println("5 - Pesquisar usuário por nome ou idade ou email");

            opt = sc.nextInt();

            switch (opt) {
                case 1:
                    arq.salvarPessoa();
                    break;
                case 2:
                    arq.listaPessoas();
                    break;
                case 3:
                    arq.adicionaPergunta();
                    break;
                case 4:
                    arq.deletaPergunta();
                    break;
                case 5:
                    arq.pesquisaPessoa();
                    break;
                case 9:
                    System.out.println("Finalizando...");
                default:
                    System.out.println("Opção inválida, tente novamente");
                    break;

            }
        }
        }
    }