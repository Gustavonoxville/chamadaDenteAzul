package chamada2.com.br.chamadablueooth;

/**
 * Created by gustavo on 24/11/2017.
 */

public class Aluno {
    private String nome;
    private String mac;

    public Aluno(String nome, String mac) {
        this.nome = nome;
        this.mac = mac;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
