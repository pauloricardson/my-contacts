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
        return "Adicionar Contato";
    }

    @Override
    public void executar() {

        ConsoleUI.traco();
        System.out.println(nomeFuncao());
        ConsoleUI.traco();

        String nome = ConsoleUI.lerString("Nome:");
        String telefone = ConsoleUI.lerString("Telefone:");
        String email = ConsoleUI.lerString("E-mail:");

        ConsoleUI.traco();

        if (ConsoleUI.confirmar("Confirmar (s/n)?")) {
            Contato contato = new Contato(nome, telefone, email);
            agendaService.adicionarContato(contato);
            System.out.println("Contato adicionado com sucesso!");
            return;
        }
        System.out.println("Opreção cancelada.");
    }
}
