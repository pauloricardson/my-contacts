package br.mycontacts.service;

import br.mycontacts.models.Contato;

import java.text.Normalizer;
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
    public List<Contato> buscarContatos(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        String termo = normalizar(nome.toLowerCase());
        List<Contato> resultados = new ArrayList<>();

        for (Contato c : contatos) {
            String nomeContato = normalizar(c.getNome().toLowerCase());

            if (nomeContato.contains(termo)) {
                resultados.add(c);
            }
        }

        if (resultados.isEmpty()) {
            throw new IllegalArgumentException("Nenhum contato encontrado");
        }

        return resultados;
    }


    // 4. Remover contato

    // Normalização de String para pesquisa
    public String normalizar(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return texto.replaceAll("[^\\p{ASCII}]", "");
    }

}
