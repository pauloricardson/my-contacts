package br.mycontacts.models;

public class ContatoComercial extends Contato {

    private String empresa;

    public ContatoComercial(String nome, String telefone, String email, String empresa) {
        super(nome, telefone, email);
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    @Override
    public String toString() {
        return super.toString() + " | EMPRESA: " + this.empresa;
    }
}
