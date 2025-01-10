public class Pessoa {
    String nome;
    String email;
    int idade;
    double altura;

    @Override
    public String toString() {
        return nome+"\n" + email+"\n" + idade +"\n" + altura;
    }
}
