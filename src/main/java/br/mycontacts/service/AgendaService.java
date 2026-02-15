package br.mycontacts.service;

import br.mycontacts.models.Contato;

import java.util.ArrayList;
import java.util.List;

public class AgendaService {

    private ArrayList<Contato> contatos;

    // 1. Adicionar novo Contato
    public void adicionarContato(Contato contato) {
        contatos.add(contato);
    }

    // 2. Listar contatos
    public List<Contato> listarContatos() {
        return contatos;
    }

    // 3. Buscar por nome


    // 4. Remover contato

}
