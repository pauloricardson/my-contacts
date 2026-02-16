package br.mycontacts.controller.opcoes;

import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

public class BuscarContatoOption implements MenuOption {

    private AgendaService agendaService;

    public BuscarContatoOption(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @Override
    public String nomeFuncao() {
        return "Buscar Contato";
    }

    @Override
    public void executar() {
        ConsoleUI.traco();
        System.out.println(nomeFuncao());
        ConsoleUI.traco();

        String pesquisar = ConsoleUI.lerString("Pesquisar por nome:");

        try {
            agendaService.buscarContatos(pesquisar);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
