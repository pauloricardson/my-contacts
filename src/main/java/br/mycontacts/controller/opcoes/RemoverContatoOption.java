package br.mycontacts.controller.opcoes;

import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

public class RemoverContatoOption implements MenuOption {

    private AgendaService agendaService;

    public RemoverContatoOption(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @Override
    public String nomeFuncao() {
        return "Remover Contato";
    }

    @Override
    public void executar() {

        ConsoleUI.traco();
        System.out.println(nomeFuncao());
        ConsoleUI.traco();

        long id = ConsoleUI.lerLong("ID do contato:");

        boolean validar = false;

        if (ConsoleUI.confirmar(String.format("Deseja remover o contato de ID " + id + " (s/n)?"))) {

            try {

                validar = agendaService.removerContato(id);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (validar) {
                System.out.println("Contato " + id + " removido com sucesso!");
            } else {
                System.out.println("Não foi possível remover o contato.");
            }

        } else {
            System.out.println("Operação cancelada");
        }
    }
}
