package br.mycontacts.models;

import br.mycontacts.utils.ValidadorEmail;
import br.mycontacts.utils.ValidadorTelefone;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;

public class Contato {

    // 1. Atributos encapsulados usando Properties do JavaFX
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty nome = new SimpleStringProperty();
    private final StringProperty telefone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty dataCriacao = new SimpleStringProperty();

    // Construtor 1: Usado para NOVOS cadastros (O MySQL vai gerar o ID e a Data automaticamente)
    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
    }

    // Construtor 2: Usado pelo DAO para remontar os contatos vindos do Banco de Dados
    public Contato(long id, String nome, String telefone, String email, String dataCriacao) {
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        setDataCriacao(dataCriacao);
    }

    // --- MÉTODOS PROPERTY (Fundamentais para a TableView sincronizar os dados) ---
    public LongProperty idProperty() { return id; }
    public StringProperty nomeProperty() { return nome; }
    public StringProperty telefoneProperty() { return telefone; }
    public StringProperty emailProperty() { return email; }
    public StringProperty dataCriacaoProperty() { return dataCriacao; }

    // --- GETTERS TRADICIONAIS ---
    public long getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getTelefone() { return telefone.get(); }
    public String getEmail() { return email.get(); }
    public String getDataCriacao() { return dataCriacao.get(); }

    // --- SETTERS COM SUAS VALIDAÇÕES MANTIDAS ---
    public void setId(long id) {
        this.id.set(id);
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome.set(nome);
    }

    public void setTelefone(String telefone) {
        if (!ValidadorTelefone.validarTelefone(telefone)) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone.set(telefone);
    }

    public void setEmail(String email) {
        // Permite e-mail vazio ou nulo
        if (email == null || email.trim().isEmpty()) {
            this.email.set("");
            return;
        }

        // Valida apenas se o usuário digitou algo
        if (!ValidadorEmail.validarEmail(email)) {
            throw new IllegalArgumentException("E-mail inválido");
        }

        this.email.set(email);
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao.set(dataCriacao);
    }

    @Override
    public String toString() {
        return "ID: " + getId() +
                " | NOME: " + getNome() +
                " | TELEFONE: " + getTelefone() +
                " | E-MAIL: " + getEmail() +
                " | DATA: " + getDataCriacao();
    }
}