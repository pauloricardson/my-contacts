package br.mycontacts.controller;

import br.mycontacts.controller.opcoes.AdicionarContatoOption;
import br.mycontacts.controller.opcoes.ListarContatosOption;
import br.mycontacts.controller.opcoes.MenuOption;
import br.mycontacts.service.AgendaService;
import br.mycontacts.utils.ConsoleUI;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipal {

    private List<MenuOption> opcoes = new ArrayList<>();

    public MenuPrincipal() {
        AgendaService agendaService = new AgendaService();
        opcoes.add(new AdicionarContatoOption(agendaService));
        opcoes.add(new ListarContatosOption(agendaService));
    }

    public void inciar() {
        int escolha = 0;

        do {
            System.out.println("========== AGENDA DE CONTATOS ==========");
            for (int i = 0; i < opcoes.size(); i++) {
                System.out.println(i + 1 + ". " + opcoes.get(i).nomeFuncao());
            }
            System.out.println((opcoes.size() + 1) + ". Sair");

            escolha = ConsoleUI.lerInt("Selecione uma opção:");

            ConsoleUI.limparConsole();

            if (escolha >= 1 && escolha <= opcoes.size()) {
                opcoes.get(escolha - 1).executar();
                ConsoleUI.pausar();
                ConsoleUI.limparConsole();
            }

        } while (escolha != opcoes.size() + 1);

        ConsoleUI.limparConsole();

        System.out.println("Programa encerrado...");
    }
}
