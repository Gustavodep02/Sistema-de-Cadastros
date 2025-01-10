import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Arquivos {
    public void criaArquivoForm(){
        File file = new File("formulario.txt");

        if(!file.exists()){

        try(FileWriter fw = new FileWriter(file);BufferedWriter bw = new BufferedWriter(fw)){
            bw.write("1 - Qual o seu nome completo?\r2 - Qual seu email de contato?\r3 - Qual sua idade?\r4 - Qual sua altura?\r");
            bw.flush();

        }catch(IOException e){
            e.printStackTrace();
        }
        }
    }
    public void salvarPessoa(){
        File file = new File("formulario.txt");
        try(FileReader fr = new FileReader(file);BufferedReader br = new BufferedReader(fr)){
            String linha;
            Pessoa p = new Pessoa();
            int pergunta = 1;
            while((linha = br.readLine())!=null){
                System.out.println(linha);
                p = montaPessoa(p,pergunta);
                pergunta++;
            }
            criaArquivoPessoa(p);
            System.out.println("Pessoa Cadastrada!\n");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public Pessoa montaPessoa(Pessoa p,int pergunta) throws Exception {
        Scanner sc = new Scanner(System.in);
        switch(pergunta){
            case 1:
                String nome = sc.nextLine();
                if(nome.length() <10){
                    throw new Exception("Nome deve possuir no minimo 10 caracteres");
                }
                p.nome = nome;
                break;
            case 2:
                String email = sc.nextLine();
                if(!email.contains("@")){
                    throw new Exception("Email nao contem @");
                }
                verificaEmail(email);
                p.email = email;
                break;
            case 3:
                int idade = sc.nextInt();
                if (idade<18){
                    throw new Exception("Idade deve ser maior que 18");
                }
                p.idade = idade;
                break;
            case 4:
                String altura = sc.nextLine();
                if(!altura.contains(",")){
                    throw new Exception("Altura deve ser preenchida com vírgulas ");
                }
                altura = altura.replace(",",".");
                double alt = Double.parseDouble(altura);
                p.altura = alt;
                break;
        }
        return p;
    }

    public void verificaEmail(String email) throws Exception{
        File dir = new File("./pessoas");
        File[] arquivos = dir.listFiles();

        if(arquivos != null){
            int tam = arquivos.length;
            for(int i= 0; i< tam; i++){
                File arquivo = arquivos[i];
                try(FileReader fr = new FileReader(arquivo); BufferedReader br = new BufferedReader(fr)){
                    String linha= br.readLine();
                    while( linha != null ) {
                        if (linha.contains(email)) {
                            throw new Exception("Email ja cadastrado");
                        }
                        linha = br.readLine();
                    }
                }
            }

        }
    }

    public void criaArquivoPessoa(Pessoa p ){
        File file = new File("./pessoas");
        if(!file.exists()){
            file.mkdir();
        }
        File[] arquivos = file.listFiles();
        int valorFinal = 0;
        String regex="[0-9]";
        Pattern pattern = Pattern.compile(regex);

        for (File arquivo : arquivos) {
            String nome=arquivo.getName();
            Matcher matcher = pattern.matcher(nome);
            while(matcher.find()){
                 int valor = Integer.parseInt(matcher.group());
                 if(valor > valorFinal){
                     valorFinal = valor;
                 }
            }
        }
        valorFinal++;
        String arqPessoa = valorFinal+"-"+p.nome.toUpperCase().trim();
        File filePessoa = new File("./pessoas/"+arqPessoa+".txt");
        try(FileWriter fw = new FileWriter(filePessoa);BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(p.nome);
            bw.newLine();
            bw.write(p.email);
            bw.newLine();
            bw.write(String.valueOf(p.idade));
            bw.newLine();
            bw.write(String.valueOf(p.altura));
            bw.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public void listaPessoas(){
        File file = new File("./pessoas");
        if(!file.exists()){
            file.mkdir();
        }
        File[] arquivos = file.listFiles();
        int atual = 1;
        for (File arquivo : arquivos) {
            try(FileReader fr = new FileReader(arquivo);BufferedReader br = new BufferedReader(fr)){
                String linha= br.readLine();;
                System.out.println(atual + " - "+linha);
                atual++;
            }catch (IOException e){
                e.printStackTrace();
            }
            System.out.println("\n");
        }
    }
    public void adicionaPergunta(){
        File file = new File("formulario.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite a nova pergunta");
        String pergunta = sc.nextLine();
        try(FileWriter fw = new FileWriter(file,true); BufferedWriter bw = new BufferedWriter(fw)){
            int num = verificaPergunta();
            bw.write(num+" - "+pergunta);
            bw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Pergunta adicionada!\n");
    }

    public int verificaPergunta() {
        File file = new File("formulario.txt");
        int num = 5;
        try(FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)){

            String linha;
            while((linha = br.readLine()) != null){
                String[] partes = linha.split(" - ");
                if (partes.length > 0) {
                    int valorInt = Integer.parseInt(partes[0].trim());
                    if (valorInt > num) {
                        num = valorInt;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return num;
    }

    public void deletaPergunta(){
        File file = new File("formulario.txt");
        List<String> linhas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero da pergunta a ser deletada:");
        int valor = 1;
        try(FileReader fr = new FileReader(file);BufferedReader br = new BufferedReader(fr)){

            String linha;
            while((linha = br.readLine())!=null) {
                linhas.add(linha);
                System.out.println(linha);
                valor++;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        int perg = sc.nextInt();
        if(perg == 1 || perg == 2 || perg == 3 || perg == 4 || perg >valor || perg<=0){
            System.out.println("A pergunta nao pode ser deletada!");
            return;
        }else{
            try(FileWriter fw = new FileWriter(file);BufferedWriter bw = new BufferedWriter(fw)){
                int i=0;
                while(i<valor-1) {
                    if(i!=perg-1) {
                        bw.write(linhas.get(i) + "\r");

                    }
                    i++;
                }
                bw.flush();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void pesquisaPessoa(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome, email ou idade da pessoa desejada");
        String pesquisa = sc.nextLine();
        encontraPessoa(pesquisa);
    }

    public void encontraPessoa(String pesquisa){
        File dir = new File("./pessoas");
        File[] arquivos = dir.listFiles();

        if(arquivos != null){
            int tam = arquivos.length;
            for(int i= 0; i< tam; i++){
                File arquivo = arquivos[i];
                try(FileReader fr = new FileReader(arquivo); BufferedReader br = new BufferedReader(fr)){
                    String linha= br.readLine();
                    while( linha != null ) {
                        if (linha.contains(pesquisa)) {
                            try (FileReader fr2 = new FileReader(arquivo); BufferedReader br2 = new BufferedReader(fr2)) {
                                String linhaExibida = br2.readLine();

                                while (linhaExibida != null) {
                                    System.out.println(linhaExibida);
                                    linhaExibida = br2.readLine();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                                break;
                        }
                        linha = br.readLine();
                    }
                }catch(IOException e){
                    e.printStackTrace();
                }
    }
}}}
