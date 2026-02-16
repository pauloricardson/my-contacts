package br.mycontacts.models;

import br.mycontacts.utils.ValidadorEmail;
import br.mycontacts.utils.ValidadorTelefone;

public class Contato {

    protected String nome;
    protected String telefone;
    protected String email;
    protected long id;

    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        this.id = gerarId();
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

    public long getId() {
        return id;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        if (!ValidadorTelefone.validarTelefone(telefone)) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        if (!ValidadorEmail.validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.email = email;
    }

    public static long gerarId() {
        return System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "NOME: " + this.nome +
                " | TELEFONE: " + this.telefone +
                " | E-MAIL: " + this.email +
                " | ID: " + this.id;
    }
}