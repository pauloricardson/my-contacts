package br.mycontacts.service;

import br.mycontacts.database.ContatoDAO;
import br.mycontacts.models.Contato;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class AgendaService {

    private final ContatoDAO contatoDAO = new ContatoDAO();

    public void adicionarContato(Contato contato) {
        contatoDAO.salvar(contato);
    }

    public List<Contato> listarContatos() {
        return contatoDAO.buscarTodos();
    }
//
    public List<Contato> buscarContatos(String nome) {
        if (nome == null || nome.isBlank()) {
            return listarContatos(); // Se estiver em branco, retorna todo mundo
        }

        String termo = normalizar(nome.toLowerCase());
        List<Contato> resultados = new ArrayList<>();

        // Varre a lista que veio do banco e filtra usando sua lógica de acentuação
        for (Contato c : listarContatos()) {
            String nomeContato = normalizar(c.getNome().toLowerCase());

            if (nomeContato.contains(termo)) {
                resultados.add(c);
            }
        }

        return resultados; // Retorna os achados (ou uma lista vazia se não achar nenhum)
    }

    public boolean removerContato(long id) throws br.mycontacts.exceptions.ContatoNaoEncontradoException {

        // O Service delega a responsabilidade para o DAO apagar no MySQL
        boolean deletadoComSucesso = contatoDAO.deletar(id);

        // Se o banco retornar false, significa que aquele ID não existia na tabela
        if (!deletadoComSucesso) {
            throw new br.mycontacts.exceptions.ContatoNaoEncontradoException(
                    "O contato selecionado não foi encontrado no banco de dados."
            );
        }

        return true;
    }

    public void atualizarContato(Contato contato) {
        contatoDAO.atualizar(contato);
    }

    // Normalização
    public String normalizar(String texto) {

        texto = Normalizer.normalize(
                texto,
                Normalizer.Form.NFD
        );

        return texto.replaceAll("[^\\p{ASCII}]", "");
    }
}