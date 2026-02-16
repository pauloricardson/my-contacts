package br.mycontacts.controller.opcoes;

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

    }
}
