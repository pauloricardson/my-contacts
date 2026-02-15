package br.mycontacts.models;

public class Contato {

    protected String nome;
    protected String telefone;
    protected String email;

    public Contato(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "NOME: " + this.nome +
                " | TELEFONE: " + this.telefone +
                " | E-MAIL: " + this.email;
    }
}