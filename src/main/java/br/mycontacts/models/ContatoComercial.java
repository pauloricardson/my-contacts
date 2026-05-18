package br.mycontacts.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContatoComercial extends Contato {

    // Atributo específico da classe filha também usando Property
    private final StringProperty empresa = new SimpleStringProperty();

    // Construtor 1: Usado para NOVOS cadastros comerciais vindos da tela
    public ContatoComercial(String nome, String telefone, String email, String empresa) {
        super(nome, telefone, email);
        setEmpresa(empresa);
    }

    // Construtor 2: Usado pelo DAO para remontar contatos comerciais vindos do Banco
    public ContatoComercial(long id, String nome, String telefone, String email, String empresa, String dataCriacao) {
        super(id, nome, telefone, email, dataCriacao); // Repassa os dados base para a classe pai
        setEmpresa(empresa);
    }

    // --- MÉTODOS PROPERTY E TRADICIONAIS DA EMPRESA ---
    public StringProperty empresaProperty() {
        return empresa;
    }

    public String getEmpresa() {
        return empresa.get();
    }

    public void setEmpresa(String empresa) {
        if (empresa == null || empresa.isBlank()) {
            throw new IllegalArgumentException("Nome da empresa inválido");
        }
        this.empresa.set(empresa);
    }

    @Override
    public String toString() {
        return super.toString() + " | EMPRESA: " + getEmpresa();
    }
}