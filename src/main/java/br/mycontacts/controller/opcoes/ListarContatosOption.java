package br.mycontacts.controller.opcoes;

import br.mycontacts.models.Contato;
import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListarContatosOption implements MenuOption {

    private AgendaService agendaService;

    public ListarContatosOption(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @Override
    public String nomeFuncao() {
        return "Lista de Contatos";
    }

    @Override
    public void executar() {

        ConsoleUI.traco();
        System.out.println(nomeFuncao());
        ConsoleUI.traco();

        if (agendaService.listarContatos().isEmpty()) {
            System.out.println("Lista de contatos vazia.");
            return;
        }

        List<Contato> contatos = agendaService.listarContatos();

        Iterator<Contato> it = contatos.iterator();
        int count = 0;

        while (it.hasNext()) {
            count++;
            System.out.println("#" + count + "| " + it.next());
        }
    }
}
