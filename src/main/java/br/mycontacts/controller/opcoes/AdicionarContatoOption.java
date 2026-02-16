package br.mycontacts.controller.opcoes;

import br.mycontacts.models.Contato;
import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

public class AdicionarContatoOption implements MenuOption {

    private AgendaService agendaService;

    public AdicionarContatoOption(AgendaService agendaService) {
        this.agendaService = agendaService;
    }


    @Override
    public String nomeFuncao() {
        return "Adicionar contato";
    }

    @Override
    public void executar() {
        String nome = ConsoleUI.lerString("nome:");
        String telefone = ConsoleUI.lerString("Telefone:");
        String email = ConsoleUI.lerString("E-mail:");

        try {
            Contato contato = new Contato(nome, telefone, email);
            agendaService.adicionarContato(contato);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Contato adicionado com sucesso!");
    }
}
